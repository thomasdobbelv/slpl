package slpl.syntax;

import slpl.ast.AST;
import slpl.ast.Block;
import slpl.ast.While;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class WhileParser {

    public static While parseWhile(TokenStream ts) throws ParseException {
        ts.expect(TokenType.WHILE);
        ts.consume();
        ts.expect(TokenType.LPAR);
        ts.consume();
        AST condition = ExpressionParser.parseExpression(ts);
        ts.expect(TokenType.RPAR);
        ts.consume();
        Block body = BlockParser.parseBlock(ts);
        return new While(condition, body);
    }

}
