package slpl.parse;

import slpl.Lexer;
import slpl.Token;
import slpl.ast.Ast;
import slpl.ast.Statement;
import slpl.util.Pair;

import java.util.List;

public class ProgramParser {

    public static Ast parseProgram(String programText) throws ParseException {
        List<Token> tokens = Lexer.lex(programText);
        Pair<Statement, Integer> p = StatementParser.parseStatement(0, tokens);
        if(p.snd < tokens.size()) {
            throw new ParseException(String.format("unexpected token \"%s\"", tokens.get(p.snd)));
        }
        return p.fst;
    }

    public static void main(String[] args) throws ParseException {
//        String programText = "1 + 1; 2 / 3;(5 * (28/2 - 7) - 5)/10 - 3;";
        String programText = "1 + 1; 2 / 3;(5 * (28/2 - 7) - 5)/10 - 3";
        Ast program = parseProgram(programText);
        System.out.println(program);
        System.out.println(program.evaluate());
    }

}
