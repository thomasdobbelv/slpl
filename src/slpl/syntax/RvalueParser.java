package slpl.syntax;

import slpl.ast.AST;
import slpl.err.ParseException;
import slpl.syntax.lexical.LambdaFunctionParser;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class RvalueParser {

    public static AST parseRvalue(TokenStream ts) throws ParseException {
        int indexBeforeLookahead = ts.getCurrentIndex();
        if (ts.hasNext(TokenType.LPAR)) {
            ts.consume();
            if (ts.hasNext(TokenType.ID)) {
                ts.consume();
                if (ts.hasNext(TokenType.COLON)) {
                    ts.setCurrentIndex(indexBeforeLookahead);
                    return LambdaFunctionParser.parseLambdaFunction(ts);
                }
            } else if (ts.hasNext(TokenType.RPAR)) {
                ts.setCurrentIndex(indexBeforeLookahead);
                return LambdaFunctionParser.parseLambdaFunction(ts);
            }
        }
        ts.setCurrentIndex(indexBeforeLookahead);
        return ExpressionParser.parseExpression(ts);
    }

}
