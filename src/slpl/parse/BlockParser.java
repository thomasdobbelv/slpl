package slpl.parse;

import slpl.TokenType;
import slpl.ast.AST;
import slpl.util.TokenStream;

public class BlockParser {

    public static AST parseBlock(TokenStream ts) throws ParseException {
        ts.expect(TokenType.LCRL);
        ts.consume();
        AST statement = StatementParser.parseStatement(ts);
        ts.expect(TokenType.RCRL);
        ts.consume();
        return statement;
    }

}
