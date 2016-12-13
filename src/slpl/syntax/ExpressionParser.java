package slpl.syntax;

import slpl.err.ParseException;
import slpl.util.Operator;
import slpl.syntax.lexical.Token;
import slpl.syntax.lexical.TokenType;
import slpl.syntax.lexical.TokenTypeClass;
import slpl.ast.*;
import slpl.ast.Boolean;
import slpl.ast.Number;
import slpl.util.TokenStream;

import java.util.*;

public class ExpressionParser {

    private static final Queue<Integer> arityQueue = new LinkedList<>();

    public static AST parseExpression(TokenStream ts) throws ParseException {
        int start = ts.getCurrentIndex();
        recognizeExpression(ts);
        int end = ts.getCurrentIndex();
        ts.setCurrentIndex(start);
        Stack<AST> s = new Stack<>();
        for (Token t : transformInfixToPostfix(ts, end)) {
            if (t.isOperator() || t.getType() == TokenType.FID) {
                int arity = t.isOperator() ? Operator.fromToken(t).getArity() : arityQueue.poll();
                AST[] arguments = new AST[arity];
                for (int i = arity - 1; i >= 0; --i) {
                    arguments[i] = s.pop();
                }
                s.push(toOperationNode(t, arguments));
            } else if (t.isValue()){
                s.push(toValueNode(t));
            }
        }
        assert ts.getCurrentIndex() == end;
        return s.pop();
    }

    public static AST toOperationNode(Token t, AST[] arguments) {
        TokenType tt = t.getType();
        if (tt.instanceOf(TokenTypeClass.UNARY_OPERATOR)) {
            if (tt.instanceOf(TokenTypeClass.ARITHMETIC_OPERATOR)) {
                return new UnaryArithmeticOperation(Operator.fromToken(t), arguments[0]);
            } else if (tt.instanceOf(TokenTypeClass.LOGICAL_OPERATOR)) {
                return new UnaryLogicalOperation(Operator.fromToken(t), arguments[0]);
            }
        } else if (tt.instanceOf(TokenTypeClass.BINARY_OPERATOR)) {
            if (tt.instanceOf(TokenTypeClass.ARITHMETIC_OPERATOR)) {
                return new BinaryArithmeticOperation(Operator.fromToken(t), arguments[0], arguments[1]);
            } else if (tt.instanceOf(TokenTypeClass.LOGICAL_OPERATOR)) {
                return new BinaryLogicalOperation(Operator.fromToken(t), arguments[0], arguments[1]);
            } else if (tt.instanceOf(TokenTypeClass.RELATIONAL_OPERATOR)) {
                return new RelationalOperation(Operator.fromToken(t), arguments[0], arguments[1]);
            }
        } else if(tt == TokenType.FID) {
            return new FunctionApplication(t.getContent(), arguments);
        }
        StringBuilder sb = new StringBuilder();
        for (AST a : arguments) {
            sb.append(a + " ");
        }
        throw new IllegalArgumentException(String.format("The operation %s is not defined for %s", t, sb.toString()));
    }

    public static AST toValueNode(Token t) {
        switch (t.getType()) {
            case TRUE:
                return new Boolean(true);
            case FALSE:
                return new Boolean(false);
            case ID:
                return new Identifier(t.getContent());
            case NUM:
                return new Number(t.getContent());
            case STRING:
                return new Str(t.getContent());
            case NULL:
                return new Null();
        }
        throw new IllegalArgumentException(t + " is not a value");
    }

    public static void recognizeExpression(TokenStream ts) throws ParseException {
        recognizeTerm(ts);
        if (ts.hasNext(TokenType.ADD, TokenType.SUB, TokenType.OR) || (ts.hasNext() && ts.inspect().getType().instanceOf(TokenTypeClass.RELATIONAL_OPERATOR))) {
            ts.consume();
            recognizeExpression(ts);
        }
    }

    private static void recognizeTerm(TokenStream ts) throws ParseException {
        recognizeFactor(ts);
        if (ts.hasNext(TokenType.MUL, TokenType.DIV, TokenType.AND)) {
            ts.consume();
            recognizeTerm(ts);
        }
    }

    private static void recognizeFactor(TokenStream ts) throws ParseException {
        if (ts.hasNext(TokenType.NOT, TokenType.SUB)) {
            if (ts.hasNext(TokenType.SUB)) {
                Token t = ts.inspect();
                ts.replaceCurrentToken(new Token(TokenType.ADDINV, t.getContent(), t.getRow(), t.getCol()));
            }
            ts.consume();
            recognizeFactor(ts);
        } else if (ts.hasNext(TokenType.LPAR)) {
            ts.consume();
            recognizeExpression(ts);
            ts.expect(TokenType.RPAR);
            ts.consume();
        } else {
            ts.expect(TokenType.NUM, TokenType.ID, TokenType.TRUE, TokenType.FALSE, TokenType.STRING, TokenType.NULL);
            int indexBeforeLookahead = ts.getCurrentIndex();
            ts.consume();
            if(ts.hasNext(TokenType.LPAR)) {
                ts.setCurrentIndex(indexBeforeLookahead);
                recognizeFunctionApplication(ts);
            }

        }
    }

    private static void recognizeFunctionApplication(TokenStream ts) throws ParseException {
        ts.expect(TokenType.ID, TokenType.FID);
        if(ts.hasNext(TokenType.ID)) {
            Token t = ts.inspect();
            ts.replaceCurrentToken(new Token(TokenType.FID, t.getContent(), t.getRow(), t.getCol()));
        }
        ts.consume();
        ts.expect(TokenType.LPAR);
        ts.consume();
        int arity = 0;
        while(!ts.hasNext(TokenType.RPAR)) {
            recognizeExpression(ts);
            ++arity;
            if(!ts.hasNext(TokenType.RPAR)) {
                ts.expect(TokenType.COMMA);
                ts.consume();
            }
        }
        ts.consume();
        arityQueue.add(arity);
    }

    /**
     * Dijkstra's Shunting Yard algorithm.
     */
    public static List<Token> transformInfixToPostfix(TokenStream ts, int end) throws ParseException {
        LinkedList<Token> outputQueue = new LinkedList<>();
        Stack<Token> operatorStack = new Stack<>();
        while (ts.getCurrentIndex() != end) {
            Token t = ts.consume();
            if (t.isValue()) {
                outputQueue.add(t);
            } else if (t.isOperator()) {
                enqueuePrecedentOperators(outputQueue, operatorStack, Operator.fromToken(t));
                operatorStack.push(t);
            } else if(t.getType() == TokenType.FID) {
                operatorStack.push(t);
            } else if (t.getType() == TokenType.LPAR) {
                operatorStack.push(t);
            } else if(t.getType() == TokenType.COMMA) {
                try {
                    while(operatorStack.peek().getType() != TokenType.LPAR) {
                        outputQueue.add(operatorStack.pop());
                    }
                } catch (EmptyStackException e) {
                    throw ParseException.bracketMismatch(t); // TODO: throw more informative error
                }
            } else if (t.getType() == TokenType.RPAR) {
                try {
                    while (operatorStack.peek().getType() != TokenType.LPAR) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.pop();
                    if(operatorStack.peek().getType() == TokenType.FID) {
                        outputQueue.add(operatorStack.pop());
                    }
                } catch (EmptyStackException e) {
                    throw ParseException.bracketMismatch(t);
                }
            }
        }
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().getType().instanceOf(TokenTypeClass.BRACKET)) {
                throw ParseException.bracketMismatch(operatorStack.peek());
            }
            outputQueue.add(operatorStack.pop());
        }
        return outputQueue;
    }

    /**
     * Add to <b>outputQueue</b> every operator on <b>operatorStack</b> that has a higher precedence than <b>o1</b>.
     *
     * @param outputQueue
     * @param operatorStack
     * @param o1
     */
    private static void enqueuePrecedentOperators(LinkedList<Token> outputQueue, Stack<Token> operatorStack, Operator o1) {
        while (!operatorStack.isEmpty() && operatorStack.peek().isOperator()) {
            Operator o2 = Operator.fromToken(operatorStack.peek());
            if (o1.getFixity() == Operator.Fixity.LEFT && o1.getPrecedence() <= o2.getPrecedence()) {
                outputQueue.add(operatorStack.pop());
            } else if (o1.getFixity() == Operator.Fixity.RIGHT && o1.getPrecedence() < o2.getPrecedence()) {
                outputQueue.add(operatorStack.pop());
            } else {
                break;
            }
        }
    }

}
