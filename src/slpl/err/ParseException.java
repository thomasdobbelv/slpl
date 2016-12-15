package slpl.err;

import slpl.syntax.lexical.Token;
import slpl.syntax.lexical.TokenType;
import slpl.syntax.lexical.TokenTypeClass;

import static slpl.util.StringConcatenator.concatenate;

public class ParseException extends Exception {

    public ParseException(String err) {
        super(err);
    }

    public static ParseException bracketMismatch(Token t) {
        assert t.type().instanceOf(TokenTypeClass.BRACKET);
        return new ParseException(String.format("Bracket mismatch \"%s\"at %d:%d", t.content(), t.row(), t.col()));
    }

    public static ParseException expected(int row, int col, TokenType ... tokenTypes) {
        return new ParseException(String.format("Expected one of [%s] at %d:%d", concatenate(tokenTypes), row, col));
    }

    public static ParseException expected(int row, int col, String ... tokens) {
        return new ParseException(String.format("Expected one of \"%s\" at %d:%d", concatenate(tokens), row, col));
    }

    public static ParseException expected(Token t, TokenType ... tokenTypes) {
        return new ParseException(String.format("Expected one of [%s] after token \"%s\" at %d:%d", concatenate(tokenTypes), t.content(), t.row(), t.col()));
    }

    public static ParseException expected(Token t, String ... tokens) {
        return new ParseException(String.format("Expected one of \"%s\" after token \"%s\" at %d:%d", concatenate(tokens), t.content(), t.row(), t.col()));
    }

    public static ParseException unexpected(Token t) {
        return new ParseException(String.format("Unexpected token \"%s\" at %d:%d", t.content(), t.row(), t.col()));
    }

    public static ParseException unexpectedEOF(Token t) {
        return new ParseException(String.format("Unexpected EOF after token \"%s\" at %d:%d", t.content(), t.row(), t.col()));
    }

    public static ParseException notAStatement(Token t) {
        return new ParseException(String.format("Line %d is not a statement.", t.row()));
    }
}
