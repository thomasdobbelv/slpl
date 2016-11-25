package slpl.parse;

import slpl.Lexer;
import slpl.Token;
import slpl.ast.Ast;
import slpl.util.Pair;

import java.util.List;

public class ProgramParser {

    public static Ast parseProgram(String programText) throws ParseException {
        List<Token> tokens = Lexer.lex(programText);
        Pair<Ast, Integer> p = ArithmeticExpressionParser.parseArithmeticExpression(0, tokens);
        if(p.snd < tokens.size()) {
            throw new ParseException("Unexpected token " + tokens.get(p.snd));
        }
        return p.fst;
    }

}
