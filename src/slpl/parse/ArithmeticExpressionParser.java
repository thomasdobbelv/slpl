package slpl.parse;

import slpl.Token;
import slpl.TokenType;
import slpl.ast.Ast;
import slpl.ast.BinaryArithmeticOperation;
import slpl.ast.Identifier;
import slpl.ast.Number;
import slpl.util.Pair;

import java.util.List;

public class ArithmeticExpressionParser {

    public static Pair<Ast, Integer> parseArithmeticExpression(int next, List<Token> tokens) throws ParseException {
        Pair<Ast, Integer> p = parseTerm(next, tokens);
        Ast a0 = p.fst;
        next = p.snd;
        if(next < tokens.size()) {
            Token t = tokens.get(next);
            if(t.content.equals("+") || t.content.equals("-")) {
                p = parseArithmeticExpression(next + 1, tokens);
                return new Pair<>(new BinaryArithmeticOperation(t.content, a0, p.fst), p.snd);
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
            if(t.content.equals("*") || t.content.equals("/")) {
                p = parseTerm(next + 1, tokens);
                return new Pair<>(new BinaryArithmeticOperation(t.content, a0, p.fst), p.snd);
            }
        }
        return p;
    }

    public static Pair<Ast, Integer> parseFactor(int next, List<Token> tokens) throws ParseException {
        Token t = tokens.get(next++);
        if(t.type == TokenType.NUMBER) {
            return new Pair<>(new Number(t.content), next);
        } else if(t.type == TokenType.IDENTIFIER) {
            return new Pair<>(new Identifier(t.content), next);
        } else if (t.content.equals("(")) {
            Pair<Ast, Integer> p = parseArithmeticExpression(next, tokens);
            t = tokens.get(p.snd++);
            if(!t.content.equals(")")) {
                throw new ParseException(String.format("expected \")\" after token \"%s\"", t.content));
            }
            return p;
        } else {
            throw new ParseException(String.format("unexpected token \"%s\"", t.content));
        }
    }
}
