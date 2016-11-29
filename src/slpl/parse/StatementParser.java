package slpl.parse;

import slpl.TokenType;
import slpl.ast.AST;
import slpl.ast.Statement;
import slpl.util.TokenStream;

public class StatementParser {

    public static AST parseStatement(TokenStream ts) throws ParseException {
        AST a1 = ArithmeticExpressionParser.parseArithmeticExpression(ts);
        ts.expectOneOf(TokenType.SEMICOLON);
        ts.consume();
        if(ts.hasNext()) {
            AST a2 = parseStatement(ts);
            return new Statement(a1, a2);
        } else {
            return new Statement(a1, null);
        }
    }

}
