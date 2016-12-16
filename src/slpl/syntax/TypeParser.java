package slpl.syntax;

import slpl.ast.Type;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class TypeParser {

    public static Type parseType(TokenStream ts) throws ParseException {
        ts.expect(TokenType.ID);
        return new Type(ts.consume().content());
    }

}
