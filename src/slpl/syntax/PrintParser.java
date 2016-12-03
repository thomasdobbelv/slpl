package slpl.syntax;

import slpl.ast.Print;
import slpl.ast.Str;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class PrintParser {

    public static Print parsePrint(TokenStream ts) throws ParseException {
        ts.expect(TokenType.PRINT, TokenType.PRINTLN);
        boolean nl = ts.consume().getType() == TokenType.PRINTLN;
        if(ts.hasNext(TokenType.STRING)) {
            return new Print(new Str(ts.consume().getContent()), nl);
        } else {
            return new Print(ExpressionParser.parseExpression(ts), nl);
        }
    }

}
