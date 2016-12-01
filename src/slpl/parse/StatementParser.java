package slpl.parse;

import slpl.TokenType;
import slpl.ast.AST;
import slpl.ast.Print;
import slpl.ast.Statement;
import slpl.util.TokenStream;

public class StatementParser {

    public static Statement parseStatement(TokenStream ts) throws ParseException {
        if(ts.hasNext(TokenType.IF)) {
            return new Statement(IfParser.parseIf(ts));
        } else if(ts.hasNext(TokenType.PRINTLN)) {
            ts.consume();
            ts.expect(TokenType.STRING);
            String toPrint = ts.consume().getContent();
            ts.expect(TokenType.SEMICOLON);
            ts.consume();
            return new Statement(new Print(toPrint.substring(1, toPrint.length() - 1))); // TODO: remove at some point. temporary
        } else {
            AST a = ExpressionParser.parseExpression(ts);
            ts.expect(TokenType.SEMICOLON);
            ts.consume();
            return new Statement(a);
        }
    }

}
