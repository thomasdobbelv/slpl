package slpl.parse;

import slpl.Operator;
import slpl.Token;
import slpl.TokenType;
import slpl.ast.Ast;
import slpl.ast.BinaryArithmeticOperation;
import slpl.ast.Identifier;
import slpl.ast.Number;
import slpl.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ArithmeticExpressionParser {

    /**
     * Shunting yard.
     * @param tokens
     * @return
     */
    public static List<Token> transformInfixToRPN(List<Token> tokens) {
        LinkedList<Token> outputQueue = new LinkedList<>();
        Stack<Token> operatorStack = new Stack<>();
        for(Token t : tokens) {
            if(t.getType() == TokenType.NUMBER || t.getType() == TokenType.IDENTIFIER || t.getContent().equals("(")) {
                outputQueue.add(t);
            } else if(t.getType() == TokenType.BINARYOPERATOR) {
                Operator o1 = Operator.fromString(t.getContent());
                while(!operatorStack.isEmpty()) {
                    Operator o2 = Operator.fromString(operatorStack.peek().getContent());
                    if(o1.getFixity() == Operator.Fixity.LEFT && o1.getPrecedence() <= o2.getPrecedence()) {
                        outputQueue.add(operatorStack.pop());
                    } else if(o1.getFixity() == Operator.Fixity.RIGHT && o1.getPrecedence() < o2.getPrecedence()) {
                        outputQueue.add(operatorStack.pop());
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static Pair<Ast, Integer> parseArithmeticExpression(int next, List<Token> tokens) throws ParseException {
        Pair<Ast, Integer> p = parseTerm(next, tokens);
        Ast a0 = p.fst;
        next = p.snd;
        if(next < tokens.size()) {
            Token t = tokens.get(next);
            if(t.getContent().equals("+") || t.getContent().equals("-")) {
                p = parseArithmeticExpression(next + 1, tokens);
                return new Pair<>(new BinaryArithmeticOperation(t.getContent(), a0, p.fst), p.snd);
            }
        }
        return p;
    }

    public static Pair<Ast, Integer> parseTerm(int next, List<Token> tokens) throws ParseException {
        Pair<Ast, Integer> p = parseFactor(next, tokens);
        Ast a0 = p.fst;
        next = p.snd;
        if(next < tokens.size()) {
            Token t = tokens.get(next);
            if(t.getContent().equals("*") || t.getContent().equals("/")) {
                p = parseTerm(next + 1, tokens);
                return new Pair<>(new BinaryArithmeticOperation(t.getContent(), a0, p.fst), p.snd);
            }
        }
        return p;
    }

    public static Pair<Ast, Integer> parseFactor(int next, List<Token> tokens) throws ParseException {
        Token t = tokens.get(next++);
        if(t.getType() == TokenType.NUMBER) {
            return new Pair<>(new Number(t.getContent()), next);
        } else if(t.getType() == TokenType.IDENTIFIER) {
            return new Pair<>(new Identifier(t.getContent()), next);
        } else if (t.getContent().equals("(")) {
            Pair<Ast, Integer> p = parseArithmeticExpression(next, tokens);
            t = tokens.get(p.snd++);
            if(!t.getContent().equals(")")) {
                throw new ParseException(String.format("expected \")\" after token \"%s\"", t.getContent()));
            }
            return p;
        } else {
            throw new ParseException(String.format("unexpected token \"%s\"", t.getContent()));
        }
    }
}
