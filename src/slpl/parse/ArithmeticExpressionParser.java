package slpl.parse;

import slpl.Operator;
import slpl.Token;
import slpl.TokenType;
import slpl.ast.Ast;
import slpl.ast.BinaryArithmeticOperation;
import slpl.ast.Identifier;
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
        for (Token t : infixToRPN(tokens.subList(start, end))) {
            if (t.getType() == TokenType.BINARYOPERATOR) {
                Ast rhsOperand = s.pop(), lhsOperand = s.pop();
                s.push(new BinaryArithmeticOperation(t.getContent(), lhsOperand, rhsOperand));
            } else {
                s.push(t.getType() == TokenType.NUMBER ? new Number(t.getContent()) : new Identifier(t.getContent()));
            }
        }
        return new Pair<>(s.pop(), end);
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
        if (t.getType() == TokenType.NUMBER || t.getType() == TokenType.IDENTIFIER) {
            return start + 1;
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
    public static List<Token> infixToRPN(List<Token> tokens) throws ParseException {
        LinkedList<Token> outputQueue = new LinkedList<>();
        Stack<Token> operatorStack = new Stack<>();
        for (Token t : tokens) {
            if (t.getType() == TokenType.NUMBER || t.getType() == TokenType.IDENTIFIER) {
                outputQueue.add(t);
            } else if (t.getType() == TokenType.BINARYOPERATOR) {
                Operator o1 = Operator.fromString(t.getContent());
                // TODO: extract method
                while (!operatorStack.isEmpty() && operatorStack.peek().getType() == TokenType.BINARYOPERATOR) {
                    Operator o2 = Operator.fromString(operatorStack.peek().getContent());
                    if (o1.getFixity() == Operator.Fixity.LEFT && o1.getPrecedence() <= o2.getPrecedence()) {
                        outputQueue.add(operatorStack.pop());
                    } else if (o1.getFixity() == Operator.Fixity.RIGHT && o1.getPrecedence() < o2.getPrecedence()) {
                        outputQueue.add(operatorStack.pop());
                    } else {
                        break;
                    }
                }
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
        while(!operatorStack.isEmpty()) {
            if(operatorStack.peek().getType() == TokenType.BRACKET) {
                throw new ParseException("mismatched parentheses");
            }
            outputQueue.add(operatorStack.pop());
        }
        return outputQueue;
    }

//    public static Pair<Ast, Integer> parseArithmeticExpression(int next, List<Token> tokens) throws ParseException {
//        Pair<Ast, Integer> p = parseTerm(next, tokens);
//        Ast a0 = p.fst;
//        next = p.snd;
//        if(next < tokens.size()) {
//            Token t = tokens.get(next);
//            if(t.getContent().equals("+") || t.getContent().equals("-")) {
//                p = parseArithmeticExpression(next + 1, tokens);
//                return new Pair<>(new BinaryArithmeticOperation(t.getContent(), a0, p.fst), p.snd);
//            }
//        }
//        return p;
//    }
//
//    public static Pair<Ast, Integer> parseTerm(int next, List<Token> tokens) throws ParseException {
//        Pair<Ast, Integer> p = parseFactor(next, tokens);
//        Ast a0 = p.fst;
//        next = p.snd;
//        if(next < tokens.size()) {
//            Token t = tokens.get(next);
//            if(t.getContent().equals("*") || t.getContent().equals("/")) {
//                p = parseTerm(next + 1, tokens);
//                return new Pair<>(new BinaryArithmeticOperation(t.getContent(), a0, p.fst), p.snd);
//            }
//        }
//        return p;
//    }
//
//    public static Pair<Ast, Integer> parseFactor(int next, List<Token> tokens) throws ParseException {
//        Token t = tokens.get(next++);
//        if(t.getType() == TokenType.NUMBER) {
//            return new Pair<>(new Number(t.getContent()), next);
//        } else if(t.getType() == TokenType.IDENTIFIER) {
//            return new Pair<>(new Identifier(t.getContent()), next);
//        } else if (t.getContent().equals("(")) {
//            Pair<Ast, Integer> p = parseArithmeticExpression(next, tokens);
//            t = tokens.get(p.snd++);
//            if(!t.getContent().equals(")")) {
//                throw new ParseException(String.format("expected \")\" after token \"%s\"", t.getContent()));
//            }
//            return p;
//        } else {
//            throw new ParseException(String.format("unexpected token \"%s\"", t.getContent()));
//        }
//    }
}
