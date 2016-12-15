package slpl.syntax;

import slpl.ast.AST;
import slpl.ast.Block;
import slpl.ast.While;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class WhileParser {

    public static While parseWhile(TokenStream ts) throws ParseException {
        ts.expectOneOf(TokenType.WHILE);
        ts.consume();
        ts.expectOneOf(TokenType.LPAR);
        ts.consume();
        AST condition = ExpressionParser.parseExpression(ts);
        ts.expectOneOf(TokenType.RPAR);
        ts.consume();
        Block body = BlockParser.parseBlock(ts);
        return new While(condition, body);
    }

}
