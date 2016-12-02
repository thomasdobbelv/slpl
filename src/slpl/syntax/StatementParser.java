package slpl.syntax;

import slpl.ast.Str;
import slpl.syntax.lexical.TokenType;
import slpl.ast.AST;
import slpl.ast.Print;
import slpl.ast.Statement;
import slpl.util.TokenStream;

public class StatementParser {

    // TODO: refactor
    public static Statement parseStatement(TokenStream ts) throws ParseException {
        if(ts.hasNext(TokenType.IF)) {
            return new Statement(IfParser.parseIf(ts));
        } else if(ts.hasNext(TokenType.PRINTLN)) {
            ts.consume();
            if(ts.hasNext(TokenType.STRING)) {
                AST str = new Str(ts.consume().getContent());
                ts.expect(TokenType.SEMICOLON);
                ts.consume();
                return new Statement(new Print(str)); // TODO: remove at some point. temporary
            } else {
                AST a = ExpressionParser.parseExpression(ts);
                ts.expect(TokenType.SEMICOLON);
                ts.consume();
                return new Statement(new Print(a));
            }
        } else {
            AST a = ExpressionParser.parseExpression(ts);
            ts.expect(TokenType.SEMICOLON);
            ts.consume();
            return new Statement(a);
        }
    }

}
