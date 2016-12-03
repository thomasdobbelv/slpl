package slpl.syntax;

import slpl.ast.Assign;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class AssignmentParser {

    public static Assign parseAssignment(TokenStream ts) throws ParseException {
        ts.expect(TokenType.IDENTIFIER);
        String name = ts.consume().getContent();
        ts.expect(TokenType.ASSIGN);
        ts.consume();
        return new Assign(ExpressionParser.parseExpression(ts), name);
    }

}
