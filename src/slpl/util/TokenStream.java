package slpl.util;

import slpl.Token;
import slpl.TokenType;
import slpl.parse.ParseException;

import java.util.List;

public class TokenStream {

    private final List<Token> tokens;
    private int index;

    public TokenStream(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setCurrentIndex(int i) {
        this.index = i;
    }

    public int getCurrentIndex() {
        return index;
    }

    public void expect(String expected) throws ParseException {
        if (index >= tokens.size()) {
            throw ParseException.unexpectedEOF(tokens.get(tokens.size() - 1));
        } else if (!tokens.get(index).getContent().equals(expected)) {
            if (index > 0) {
                throw ParseException.expected(expected, tokens.get(index - 1));
            } else {
                throw ParseException.expected(expected, 0, 0);
            }
        }
    }

    public void expect(TokenType expected) throws ParseException {
        if (index >= tokens.size()) {
            throw ParseException.unexpectedEOF(tokens.get(tokens.size() - 1));
        } else if (tokens.get(index).getType() != expected) {
            if (index > 0) {
                throw ParseException.expected(expected, tokens.get(index - 1));
            } else {
                throw ParseException.expected(expected, 0, 0);
            }
        }
    }

    public Token consume() {
        return tokens.get(index++);
    }

    public boolean hasNext() {
        return index < tokens.size();
    }

}
