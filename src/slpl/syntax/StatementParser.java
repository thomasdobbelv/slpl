package slpl.syntax;

import slpl.ast.Str;
import slpl.syntax.lexical.TokenType;
import slpl.ast.Print;
import slpl.ast.Statement;
import slpl.util.TokenStream;

public class StatementParser {

    // TODO: refactor
    public static Statement parseStatement(TokenStream ts) throws ParseException {
        Statement statement;
        if(ts.hasNext(TokenType.IF)) {
            return new Statement(IfParser.parseIf(ts));
        } else if(ts.hasNext(TokenType.PRINTLN)) {
            ts.consume();
            if(ts.hasNext(TokenType.STRING)) {
                statement = new Statement(new Print(new Str(ts.consume().getContent())));
            } else {
                statement = new Statement(new Print(ExpressionParser.parseExpression(ts)));
            }
        } else if(ts.hasNext(TokenType.IDENTIFIER)) {
            statement = new Statement(DeclarationParser.parseDeclaration(ts));
        } else {
            statement = new Statement(ExpressionParser.parseExpression(ts));
        }
        ts.expect(TokenType.SEMICOLON);
        ts.consume();
        return statement;
    }

}
