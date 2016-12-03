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

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ExpressionParser {

    public static AST parseExpression(TokenStream ts) throws ParseException {
        int start = ts.getCurrentIndex();
        recognizeExpression(ts);
        int end = ts.getCurrentIndex();
        ts.setCurrentIndex(start);
        Stack<AST> s = new Stack<>();
        for (Token t : transformInfixToPostfix(ts, end)) {
            if (t.isOperator()) {
                int arity = Operator.fromToken(t).getArity();
                AST[] operands = new AST[arity];
                for (int i = arity - 1; i >= 0; --i) {
                    operands[i] = s.pop();
                }
                s.push(toOperationNode(t, operands));
            } else {
                s.push(toValueNode(t));
            }
        }
        assert ts.getCurrentIndex() == end;
        return s.pop();
    }

    public static AST toOperationNode(Token t, AST[] operands) {
        TokenType tt = t.getType();
        Operator operator = Operator.fromToken(t);
        if (tt.instanceOf(TokenTypeClass.UNARY_OPERATOR)) {
            if (tt.instanceOf(TokenTypeClass.ARITHMETIC_OPERATOR)) {
                return new UnaryArithmeticOperation(operator, operands[0]);
            } else if (tt.instanceOf(TokenTypeClass.LOGICAL_OPERATOR)) {
                return new UnaryLogicalOperation(operator, operands[0]);
            }
        } else if (tt.instanceOf(TokenTypeClass.BINARY_OPERATOR)) {
            if (tt.instanceOf(TokenTypeClass.ARITHMETIC_OPERATOR)) {
                return new BinaryArithmeticOperation(operator, operands[0], operands[1]);
            } else if (tt.instanceOf(TokenTypeClass.LOGICAL_OPERATOR)) {
                return new BinaryLogicalOperation(operator, operands[0], operands[1]);
            } else if (tt.instanceOf(TokenTypeClass.RELATIONAL_OPERATOR)) {
                return new RelationalOperation(operator, operands[0], operands[1]);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (AST a : operands) {
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
            case IDENTIFIER:
                return new Identifier(t.getContent());
            case NUMBER:
                return new Number(t.getContent());
            case STRING:
                return new Str(t.getContent());
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
            ts.expect(TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.TRUE, TokenType.FALSE);
            ts.consume();
        }
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
            } else if (t.getType() == TokenType.LPAR) {
                operatorStack.push(t);
            } else if (t.getType() == TokenType.RPAR) {
                try {
                    while (!operatorStack.peek().getContent().equals("(")) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.pop();
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
