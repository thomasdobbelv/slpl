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
        String programText = "" +
                "if(3 + a < 22 * b) {\n" +
                "    1 + 2;\n" +
                "} else if(c || !d && 5 >= e) {\n" +
                "    3 + 4;\n" +
                "    5 + 6;\n" +
                "} else {\n" +
                "    a;\n" +
                "}";
        AST program = parseProgram(programText);
        System.out.println(program);
//        System.out.println(program.evaluate());
    }

}
