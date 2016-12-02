package slpl.syntax;

import slpl.syntax.lexical.TokenType;
import slpl.ast.AST;
import slpl.ast.Module;
import slpl.util.TokenStream;

public class ModuleParser {

    public static AST parseModule(TokenStream ts) throws ParseException {
        ts.expect(TokenType.MODULE);
        ts.consume();
        ts.expect(TokenType.IDENTIFIER);
        String name = ts.consume().getContent();
        return new Module(name, BlockParser.parseBlock(ts));
    }

}