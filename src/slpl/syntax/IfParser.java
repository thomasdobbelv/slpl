package slpl.syntax;

import slpl.ast.Block;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.ast.AST;
import slpl.ast.If;
import slpl.util.TokenStream;

public class IfParser {

    public static If parseIf(TokenStream ts) throws ParseException {
        ts.expect(TokenType.IF);
        ts.consume();
        ts.expect(TokenType.LPAR);
        ts.consume();
        AST condition = ExpressionParser.parseExpression(ts);
        ts.expect(TokenType.RPAR);
        ts.consume();
        Block then = BlockParser.parseBlock(ts);
        if(ts.hasNext(TokenType.ELSE)) {
            ts.consume();
            if(ts.hasNext(TokenType.IF)) {
                return new If(condition, then, parseIf(ts));
            } else {
                return new If(condition, then, BlockParser.parseBlock(ts));
            }
        } else {
            return new If(condition, then);
        }
    }

}
