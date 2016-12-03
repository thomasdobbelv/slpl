package slpl.syntax;

import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.ast.Module;
import slpl.util.TokenStream;

public class ModuleParser {

    public static Module parseModule(TokenStream ts) throws ParseException {
        ts.expect(TokenType.MODULE);
        ts.consume();
        ts.expect(TokenType.IDENTIFIER);
        String name = ts.consume().getContent();
        return new Module(name, BlockParser.parseBlock(ts));
    }

}
