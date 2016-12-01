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
//        String programText = "1 + 5 * (-3 * ((18/9 + 1) * 6)/3);";
        String programText = "!a || b && c >= 3 + 5;";
//        String programText = "!a || b && c;";
        AST program = parseProgram(programText);
        System.out.println(program);
        System.out.println(program.evaluate());
    }

}
