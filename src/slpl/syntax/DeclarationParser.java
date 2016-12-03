package slpl.syntax;

import slpl.ast.Declaration;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class DeclarationParser {

    public static Declaration parseDeclaration(TokenStream ts) throws ParseException {
        ts.expect(TokenType.IDENTIFIER);
        String name = ts.consume().getContent();
        ts.expect(TokenType.COLON);
        ts.consume();
        ts.expect(TokenType.IDENTIFIER);
        String type = ts.consume().getContent();
        if(ts.hasNext(TokenType.ASSIGN)) {
            ts.consume();
            return new Declaration(name, type, ExpressionParser.parseExpression(ts));
        } else {
            return new Declaration(name, type);
        }
    }

}
