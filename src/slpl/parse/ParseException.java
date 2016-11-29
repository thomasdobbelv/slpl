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

    public static ParseException expectedOneOf(int row, int col, TokenType ... tokenTypes) {
        return new ParseException(String.format("Expected one of [%s] at %d:%d", toCSVString(tokenTypes), row, col));
    }

    public static ParseException expectedOneOf(int row, int col, String ... tokens) {
        return new ParseException(String.format("Expected one of \"%s\" at %d:%d", toCSVString(tokens), row, col));
    }

    public static ParseException expectedOneOf(Token t, TokenType ... tokenTypes) {
        return new ParseException(String.format("Expected one of [%s] after token \"%s\" at %d:%d", toCSVString(tokenTypes), t.getContent(), t.getRow(), t.getCol()));
    }

    public static ParseException expectedOneOf(Token t, String ... tokens) {
        return new ParseException(String.format("Expected one of \"%s\" after token \"%s\" at %d:%d", toCSVString(tokens), t.getContent(), t.getRow(), t.getCol()));
    }

    public static ParseException unexpected(Token t) {
        return new ParseException(String.format("Unexpected token \"%s\" at %d:%d", t.getContent(), t.getRow(), t.getCol()));
    }

    public static ParseException unexpectedEOF(Token t) {
        return new ParseException(String.format("Unexpected EOF after token \"%s\" at %d:%d", t.getContent(), t.getRow(), t.getCol()));
    }

    private static String toCSVString(Object[] objects) {
        StringBuilder sb = new StringBuilder();
        for(Object object : objects) {
            sb.append(object + ", ");
        }
        return sb.substring(0, sb.length() - 2);
    }
}
