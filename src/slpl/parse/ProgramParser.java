package slpl.parse;

import slpl.Lexer;
import slpl.Token;
import slpl.ast.AST;
import slpl.ast.Statement;
import slpl.util.Pair;

import java.util.List;

public class ProgramParser {

    public static AST parseProgram(String programText) throws ParseException {
        List<Token> tokens = Lexer.lex(programText, true);
        Pair<Statement, Integer> p = StatementParser.parseStatement(0, tokens);
        if(p.snd < tokens.size()) {
            throw new ParseException(String.format("unexpected token \"%s\"", tokens.get(p.snd)));
        }
        return p.fst;
    }

    public static void main(String[] args) throws ParseException {
        String programText = "(5 + (3 * -2 + 6) * 4 + 18/9)/(3 * 0.5;";
        AST program = parseProgram(programText);
        System.out.println(program);
        System.out.println(program.evaluate());
    }

}
