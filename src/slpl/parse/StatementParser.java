package slpl.parse;

import slpl.Token;
import slpl.TokenType;
import slpl.ast.AST;
import slpl.ast.Statement;
import slpl.util.Pair;

import java.util.List;

public class StatementParser {

    public static Pair<Statement, Integer> parseStatement(int start, List<Token> tokens) throws ParseException {
        Pair<AST, Integer> p1 = ArithmeticExpressionParser.parseArithmeticExpression(start, tokens);
        int next = p1.snd;
        if(tokens.get(next).getType() != TokenType.SEMICOLON) {
            throw ParseException.expectedAfter(TokenType.SEMICOLON, tokens.get(next - 1));
        }
        next++;
        if(next < tokens.size()) {
            Pair<Statement, Integer> p2 = parseStatement(next, tokens);
            return new Pair<>(new Statement(p1.fst, p2.fst), p2.snd);
        } else {
            return new Pair<>(new Statement(p1.fst, null), next);
        }
    }

}
