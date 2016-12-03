package slpl.syntax;

import slpl.ast.AST;
import slpl.ast.AssignmentOperation;
import slpl.ast.Declaration;
import slpl.ast.For;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class ForParser {

    public static For parseFor(TokenStream ts) throws ParseException {
        ts.expect(TokenType.FOR);
        ts.consume();
        ts.expect(TokenType.LPAR);
        ts.consume();
        Declaration init = null;
        AST condition = null;
        AssignmentOperation update = null;
        if(!ts.hasNext(TokenType.SEMICOLON)) {
            init = DeclarationParser.parseDeclaration(ts);
        }
        ts.expect(TokenType.SEMICOLON);
        ts.consume();
        if(!ts.hasNext(TokenType.SEMICOLON)) {
            condition = ExpressionParser.parseExpression(ts);
        }
        ts.expect(TokenType.SEMICOLON);
        ts.consume();
        if(!ts.hasNext(TokenType.RPAR)) {
            update = AssignmentParser.parseAssignment(ts);
        }
        ts.expect(TokenType.RPAR);
        ts.consume();
        return new For(init, condition, update, BlockParser.parseBlock(ts));
    }

}
