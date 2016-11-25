package slpl.parse;

import slpl.Lexer;
import slpl.Token;
import slpl.ast.Ast;
import slpl.util.Pair;

import java.util.List;

public class ProgramParser {

    public static Ast parseProgram(String programText) {
        List<Token> tokens = Lexer.lex(programText);
        Pair<Ast, Integer> p = ArithmeticExpressionParser.parseArithmeticExpression(0, tokens);
        if(p.snd + 1 < tokens.size()) {
            // TODO: throw error: something something
        }
        return p.fst;
    }

}
