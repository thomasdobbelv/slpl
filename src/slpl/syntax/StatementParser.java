package slpl.syntax;

import slpl.ast.Return;
import slpl.err.ParseException;
import slpl.syntax.lexical.Token;
import slpl.syntax.lexical.TokenType;
import slpl.ast.Statement;
import slpl.syntax.lexical.TokenTypeClass;
import slpl.util.TokenStream;

public class StatementParser {

    // TODO: refactor
    public static Statement parseStatement(TokenStream ts) throws ParseException {
        if(ts.hasNext(TokenType.IF)) {
            return new Statement(IfParser.parseIf(ts));
        } else if(ts.hasNext(TokenType.WHILE)) {
            return new Statement(WhileParser.parseWhile(ts));
        } else if(ts.hasNext(TokenType.FOR)) {
            return new Statement(ForParser.parseFor(ts));
        }
        Statement statement;
        if(ts.hasNext(TokenType.PRINT, TokenType.PRINTLN)) {
            statement = new Statement(PrintParser.parsePrint(ts));
        } else if(ts.hasNext(TokenType.ID)) {
            int indexBeforeLookahead = ts.getCurrentIndex();
            Token t = ts.consume();
            if(ts.hasNext(TokenType.COLON)) {
                ts.setCurrentIndex(indexBeforeLookahead);
                statement = new Statement(DeclarationParser.parseDeclaration(ts));
            } else if (ts.inspect().type().instanceOf(TokenTypeClass.ASSIGNMENT_OPERATOR)){
                ts.setCurrentIndex(indexBeforeLookahead);
                statement = new Statement(AssignmentParser.parseAssignment(ts));
            } else if (ts.hasNext(TokenType.LPAR)) {
                ts.setCurrentIndex(indexBeforeLookahead);
                ts.replaceCurrentToken(new Token(TokenType.FID, t.content(), t.row(), t.col()));
                statement = new Statement(FunctionApplicationParser.parseFunctionApplication(ts));
            } else {
                throw ParseException.unexpected(ts.consume());
            }
        } else if(ts.hasNext(TokenType.INCR, TokenType.DECR)) {
            statement = new Statement(AssignmentParser.parseAssignment(ts));
        } else if(ts.hasNext(TokenType.RETURN)) {
            ts.consume();
            statement = new Statement(new Return(RvalueParser.parseRvalue(ts)));
        } else {
            throw ParseException.notAStatement(ts.consume());
        }
        ts.expectOneOf(TokenType.SEMICOLON);
        ts.consume();
        return statement;
    }

}
