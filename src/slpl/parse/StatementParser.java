package slpl.parse;

import slpl.TokenType;
import slpl.ast.AST;
import slpl.ast.Statement;
import slpl.util.TokenStream;

public class StatementParser {

    public static AST parseStatement(TokenStream ts) throws ParseException {
        if(!ts.hasNext()) {
            return null; // VOID ??
        } else if(ts.hasNext(TokenType.IF)) {
            AST a1 = IfParser.parseIf(ts);
            AST a2 = parseStatement(ts);
            return new Statement(a1, a2);
        } else {
            AST a1 = ExpressionParser.parseExpression(ts);
            ts.expect(TokenType.SEMICOLON);
            ts.consume();
            AST a2 = parseStatement(ts);
            return new Statement(a1, a2);
        }
    }

}
