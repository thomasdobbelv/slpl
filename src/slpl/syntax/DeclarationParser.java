package slpl.syntax;

import slpl.ast.Declaration;
import slpl.ast.Type;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class DeclarationParser {

    // TODO: refactor. not happy with type system
    public static Declaration parseDeclaration(TokenStream ts) throws ParseException {
        ts.expect(TokenType.IDENTIFIER);
        String name = ts.consume().getContent();
        ts.expect(TokenType.COLON);
        ts.consume();
        ts.expect(TokenType.TYPE);
        Type type = Type.fromToken(ts.consume());
        if(ts.hasNext(TokenType.ASSIGN)) {
            ts.consume();
            return new Declaration(name, type, ExpressionParser.parseExpression(ts));
        } else {
            return new Declaration(name, type);
        }
    }

}
