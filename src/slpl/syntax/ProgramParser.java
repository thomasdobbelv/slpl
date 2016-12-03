package slpl.syntax;

import slpl.ast.Module;
import slpl.err.ParseException;
import slpl.syntax.lexical.Lexer;
import slpl.util.TokenStream;

public class ProgramParser {

    public static Module parseProgram(String programText) throws ParseException {
        TokenStream ts = new TokenStream(Lexer.lex(programText, true));
        Module module = ModuleParser.parseModule(ts);
        if(ts.hasNext()) {
            throw ParseException.unexpected(ts.consume());
        }
        return module;
    }

}
