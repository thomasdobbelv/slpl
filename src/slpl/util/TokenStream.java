package slpl.util;

import slpl.Token;
import slpl.TokenType;
import slpl.parse.ParseException;

import java.util.List;

public class TokenStream {

    private final List<Token> tokens;
    private int next;

    public TokenStream(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getNext() {
        return next;
    }

    public void expect(String expected) throws ParseException {
        if(next >= tokens.size()) {
            throw ParseException.expectedAfter(expected, tokens.get(tokens.size() - 1));
        } else if(!tokens.get(next).getContent().equals(expected)) {
            throw ParseException.expectedAfter(expected, tokens.get(next - 1));
        }
    }

    public void expect(TokenType expected) throws ParseException {
        if(next >= tokens.size()) {
            throw ParseException.expectedAfter(expected, tokens.get(tokens.size() - 1));
        } else if(tokens.get(next).getType() != expected) {
            throw ParseException.expectedAfter(expected, tokens.get(next - 1));
        }
    }

}
