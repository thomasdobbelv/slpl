package slpl.parse;

import slpl.Lexer;
import slpl.ast.AST;
import slpl.util.TokenStream;

public class ProgramParser {

    public static AST parseProgram(String programText) throws ParseException {
        TokenStream ts = new TokenStream(Lexer.lex(programText, true));
        AST a = StatementParser.parseStatement(ts);
        if(ts.hasNext()) {
            throw ParseException.unexpected(ts.consume());
        }
        return a;
    }

    public static void main(String[] args) throws ParseException {
        String programText = "15 + true /";
        AST program = parseProgram(programText);
        System.out.println(program);
        System.out.println(program.evaluate());
    }

}
