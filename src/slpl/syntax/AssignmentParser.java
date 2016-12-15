package slpl.syntax;

import slpl.ast.AssignmentOperation;
import slpl.ast.UnaryAssignmentOperation;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.Operator;
import slpl.util.TokenStream;

public class AssignmentParser {

    public static AssignmentOperation parseAssignment(TokenStream ts) throws ParseException {
        if(ts.hasNext(TokenType.INCR, TokenType.DECR)) {
            Operator operator = Operator.fromToken(ts.consume());
            ts.expectOneOf(TokenType.ID);
            return new UnaryAssignmentOperation(ts.consume().content(), operator, true);
        } else {
            ts.expectOneOf(TokenType.ID);
            String name = ts.consume().content();
            if(ts.hasNext(TokenType.INCR, TokenType.DECR)) {
                Operator operator = Operator.fromToken(ts.consume());
                return new UnaryAssignmentOperation(name, operator, false);
            } else {
                ts.expectOneOf(TokenType.ADDEQ, TokenType.SUBEQ, TokenType.MULEQ, TokenType.DIVEQ, TokenType.ASSIGN);
                Operator operator = Operator.fromToken(ts.consume());
                return new AssignmentOperation(name, operator, RvalueParser.parseRvalue(ts));
            }
        }
    }

}
