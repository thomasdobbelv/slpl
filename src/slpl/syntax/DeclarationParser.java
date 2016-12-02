package slpl.syntax;

import slpl.ast.AST;
import slpl.ast.Declaration;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class DeclarationParser {

    public static AST parseDeclaration(TokenStream ts) throws ParseException {
        ts.expect(TokenType.IDENTIFIER);
        String name = ts.consume().getContent();
        ts.expect(TokenType.COLON);
        ts.consume();
        // FIXME: next token should be a type
        if(ts.hasNext(TokenType.ASSIGN)) {
            ts.consume();
            return new Declaration(name, ExpressionParser.parseExpression(ts));
        } else {
            return new Declaration(name);
        }
    }

}
