package slpl.parse;

import slpl.Token;
import slpl.TokenType;
import slpl.TokenTypeClass;

public class ParseException extends Exception {

    public ParseException(String err) {
        super(err);
    }

    public static ParseException bracketMismatch(Token t) {
        assert t.getType().getTypeClass() == TokenTypeClass.BRACKET;
        return new ParseException(String.format("Bracket mismatch \"%s\"at %d:%d", t.getContent(), t.getRow(), t.getCol()));
    }

    public static ParseException expectedAfter(TokenType expected, Token t) {
        return new ParseException(String.format("Expected %s after token \"%s\" at %d:%d", expected, t.getContent(), t.getRow(), t.getCol()));
    }

    public static ParseException unexpected(Token t) {
        return new ParseException(String.format("Unexpected token \"%s\" at %d:%d", t.getContent(), t.getRow(), t.getCol()));
    }

    public static ParseException unexpectedEndOfInputAfter(Token t) {
        return new ParseException(String.format("Unexpected end of input after token \"%s\" at %d:%d", t.getContent(), t.getRow(), t.getCol()));
    }

}
