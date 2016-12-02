package slpl.syntax;

import slpl.syntax.lexical.Lexer;
import slpl.ast.AST;
import slpl.util.TokenStream;

public class ProgramParser {

    public static AST parseProgram(String programText) throws ParseException {
        TokenStream ts = new TokenStream(Lexer.lex(programText, true));
        AST module = ModuleParser.parseModule(ts);
        if(ts.hasNext()) {
            throw ParseException.unexpected(ts.consume());
        }
        return module;
    }

}
