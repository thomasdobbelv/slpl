package slpl.parse;

import slpl.Operator;
import slpl.Token;
import slpl.TokenType;
import slpl.ast.*;
import slpl.ast.Number;
import slpl.util.Pair;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ArithmeticExpressionParser {

    public static Pair<Ast, Integer> parseArithmeticExpression(int start, List<Token> tokens) throws ParseException {
        int end = recognizeArithmeticExpression(start, tokens);
        Stack<Ast> s = new Stack<>();
        for (Token t : transformInfixToPostfix(tokens.subList(start, end))) {
            if (isOperator(t)) {
                int arity = arity(t);
                Ast[] operands = new Ast[arity];
                for (int i = arity - 1; i >= 0; --i) {
                    operands[i] = s.pop();
                }
                s.push(asOperationAst(t, operands));
            } else {
                s.push(asValueAst(t));
            }
        }
        return new Pair<>(s.pop(), end);
    }

    // TODO: consider making an Ast subclass for each operation, instead of just BinaryArithmeticOperation for any binary operation
    private static Ast asOperationAst(Token t, Ast[] operands) {
        switch (t.getType()) {
            case UNARYOPERATOR:
                return new UnaryArithmeticOperation(t.getContent(), operands[0]);
            case BINARYOPERATOR:
                return new BinaryArithmeticOperation(t.getContent(), operands[0], operands[1]);
        }
        return null;
    }

    private static Ast asValueAst(Token t) {
        switch (t.getType()) {
            case NUMBER:
                return new Number(t.getContent());
            case IDENTIFIER:
                return new Identifier(t.getContent());
        }
        return null;
    }

    public static int recognizeArithmeticExpression(int start, List<Token> tokens) throws ParseException {
        int end = recognizeTerm(start, tokens);
        if (end < tokens.size() && (tokens.get(end).getContent().equals("+") || tokens.get(end).getContent().equals("-"))) {
            end = recognizeArithmeticExpression(end + 1, tokens);
        }
        return end;
    }

    public static int recognizeTerm(int start, List<Token> tokens) throws ParseException {
        int end = recognizeFactor(start, tokens);
        if (end < tokens.size() && (tokens.get(end).getContent().equals("*") || tokens.get(end).getContent().equals("/"))) {
            end = recognizeTerm(end + 1, tokens);
        }
        return end;
    }

    public static int recognizeFactor(int start, List<Token> tokens) throws ParseException {
        Token t = tokens.get(start);
        if (isValue(t)) {
            return start + 1;
        } else if (t.getContent().equals("-")) {
            // t must be the unary additive inverse operator
            tokens.set(start, new Token(TokenType.UNARYOPERATOR, "-"));
            return recognizeFactor(start + 1, tokens);
        } else if (t.getContent().equals("(")) {
            int end = recognizeArithmeticExpression(start + 1, tokens);
            if (!tokens.get(end).getContent().equals(")")) {
                throw new ParseException(String.format("expected \")\" after token \"%s\"", t.getContent()));
            }
            return end + 1;
        } else {
            throw new ParseException(String.format("unexpected token \"%s\"", t.getContent()));
        }
    }

    /**
     * Dijkstra's Shunting Yard algorithm.
     *
     * @param tokens
     * @return
     */
    public static List<Token> transformInfixToPostfix(List<Token> tokens) throws ParseException {
        LinkedList<Token> outputQueue = new LinkedList<>();
        Stack<Token> operatorStack = new Stack<>();
        for (Token t : tokens) {
            if (isValue(t)) {
                outputQueue.add(t);
            } else if (isOperator(t)) {
                Operator o1 = Operator.fromString(t.getContent(), arity(t));
                enqueuePrecedentOperators(outputQueue, operatorStack, o1);
                operatorStack.push(t);
            } else if (t.getContent().equals("(")) {
                operatorStack.push(t);
            } else if (t.getContent().equals(")")) {
                try {
                    while (!operatorStack.peek().getContent().equals("(")) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.pop();
                } catch (EmptyStackException e) {
                    throw new ParseException("mismatched parentheses");
                }
            }
        }
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().getType() == TokenType.BRACKET) {
                throw new ParseException("mismatched parentheses");
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
        while (!operatorStack.isEmpty() && isOperator(operatorStack.peek())) {
            Token t = operatorStack.peek();
            Operator o2 = Operator.fromString(t.getContent(), arity(t));
            if (o1.getFixity() == Operator.Fixity.LEFT && o1.getPrecedence() <= o2.getPrecedence()) {
                outputQueue.add(operatorStack.pop());
            } else if (o1.getFixity() == Operator.Fixity.RIGHT && o1.getPrecedence() < o2.getPrecedence()) {
                outputQueue.add(operatorStack.pop());
            } else {
                break;
            }
        }
    }

    private static boolean isOperator(Token t) {
        return t.getType() == TokenType.UNARYOPERATOR || t.getType() == TokenType.BINARYOPERATOR;
    }

    /**
     * @param t A Token.
     * @return The arity of the operator that <b>t</b> represents, or -1 if <b>t</b> is not an operator.
     */
    private static int arity(Token t) {
        switch (t.getType()) {
            case UNARYOPERATOR:
                return 1;
            case BINARYOPERATOR:
                return 2;
        }
        return -1;
    }

    private static boolean isValue(Token t) {
        return t.getType() == TokenType.NUMBER || t.getType() == TokenType.IDENTIFIER;
    }
}
