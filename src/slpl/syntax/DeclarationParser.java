package slpl.syntax;

import slpl.ast.Declaration;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class DeclarationParser {

    public static Declaration parseDeclaration(TokenStream ts) throws ParseException {
        ts.expect(TokenType.ID);
        String name = ts.consume().content();
        ts.expect(TokenType.COLON);
        ts.consume();
        String type = TypeParser.parseType(ts);
        if(ts.hasNext(TokenType.ASSIGN)) {
            ts.consume();
            return new Declaration(name, type, RvalueParser.parseRvalue(ts));
        } else {
            return new Declaration(name, type);
        }
    }

}
