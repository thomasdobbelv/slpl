package slpl.syntax;

import slpl.ast.Print;
import slpl.ast.Str;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class PrintParser {

    public static Print parsePrint(TokenStream ts) throws ParseException {
        ts.expect(TokenType.PRINTLN);
        ts.consume();
        if(ts.hasNext(TokenType.STRING)) {
            return new Print(new Str(ts.consume().getContent()));
        } else {
            return new Print(ExpressionParser.parseExpression(ts));
        }
    }

}
