package slpl.syntax;

import slpl.ast.AST;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class RvalueParser {

    public static AST parseRvalue(TokenStream ts) throws ParseException {
        return ExpressionParser.parseExpression(ts);
    }

}
