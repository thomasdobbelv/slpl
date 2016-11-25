package slpl.parse;

import slpl.Token;
import slpl.TokenType;
import slpl.ast.Add;
import slpl.ast.Ast;
import slpl.ast.Identifier;
import slpl.ast.Number;
import slpl.util.Pair;

import java.util.List;

public class ArithmeticExpressionParser {

    public static Pair<Ast, Integer> parseArithmeticExpression(int i, List<Token> tokens) throws ParseException {
        Pair<Ast, Integer> p = parseTerm(i, tokens);
        Ast a0 = p.fst;
        i = p.snd;
        if(i < tokens.size()) {
            Token t = tokens.get(i++);
            switch (t.content) {
                case "+": {
                    p = parseArithmeticExpression(i, tokens);
                    return new Pair<>(new Add(a0, p.fst), p.snd);
                }
                case "-": {

                }
            }
        }

    }

    public static Pair<Ast, Integer> parseTerm(int i, List<Token> tokens) throws ParseException {
        Pair<Ast, Integer> p = parseFactor(i, tokens);
        i = p.snd;
        if(i < tokens.size()) {
            Token t = tokens.get(i);
            switch (t.content) {
                case "*": {

                }
                case "/": {

                }
            }
        }
    }

    public static Pair<Ast, Integer> parseFactor(int i, List<Token> tokens) throws ParseException {
        Token t = tokens.get(i++);
        if(t.type == TokenType.NUMBER) {
            return new Pair<>(new Number(t.content), i);
        } else if(t.type == TokenType.IDENTIFIER) {
            return new Pair<>(new Identifier(t.content), i);
        } else if (t.content.equals("(")) {
            Pair<Ast, Integer> p = parseArithmeticExpression(i, tokens);
            i = p.snd;
            t = tokens.get(i++);
            if(!t.content.equals(")")) {
                //TODO: throw error: Expected ")"
                throw new ParseException("expected ) after token " + t.content);
            }
            return new Pair<>(p.fst, i);
        } else {
            // TODO: throw error: syntax err
            return null;
        }
    }
}
