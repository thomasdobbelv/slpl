package slpl.syntax;

import slpl.ast.Print;
import slpl.ast.StringLiteral;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class PrintParser {

    public static Print parsePrint(TokenStream ts) throws ParseException {
        ts.expectOneOf(TokenType.PRINT, TokenType.PRINTLN);
        boolean nl = ts.consume().type() == TokenType.PRINTLN;
        if(ts.hasNext(TokenType.STRING)) {
            return new Print(new StringLiteral(ts.consume().content()), nl);
        } else {
            return new Print(ExpressionParser.parseExpression(ts), nl);
        }
    }

}
