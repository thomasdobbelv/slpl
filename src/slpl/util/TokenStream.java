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

    public void replaceCurrentToken(Token t) {
        tokens.set(index, t);
    }

    public void expect(TokenType ... tokenTypes) throws ParseException {
        if (index >= tokens.size()) {
            throw ParseException.expected(tokens.get(tokens.size() - 1), tokenTypes);
        } else if (!nextTokenTypeIn(tokenTypes)) {
            if (index > 0) {
                throw ParseException.expected(tokens.get(index - 1), tokenTypes);
            } else {
                throw ParseException.expected(0, 0, tokenTypes);
            }
        }
    }

    public void expect(String ... tokens) throws ParseException {
        if (index >= this.tokens.size()) {
            throw ParseException.expected(this.tokens.get(this.tokens.size() - 1), tokens);
        } else if (!nextTokenIn(tokens)) {
            if (index > 0) {
                throw ParseException.expected(this.tokens.get(index - 1), tokens);
            } else {
                throw ParseException.expected(0, 0, tokens);
            }
        }
    }

    private boolean nextTokenTypeIn(TokenType[] tokenTypes) {
        TokenType nextTokenType = tokens.get(index).getType();
        for(TokenType tt : tokenTypes) {
            if(nextTokenType == tt) {
                return true;
            }
        }
        return false;
    }

    private boolean nextTokenIn(String[] tokens) {
        String nextToken = this.tokens.get(index).getContent();
        for(String token : tokens) {
            if(nextToken.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public Token consume() throws ParseException {
        if(index >= tokens.size()) {
            throw ParseException.unexpectedEOF(tokens.get(tokens.size() - 1));
        }
        return tokens.get(index++);
    }

    public Token inspect() throws ParseException {
        Token t = consume();
        --index;
        return t;
    }

    public boolean hasNext() {
        return index < tokens.size();
    }

    public boolean hasNext(TokenType ... tokenTypes) {
        return index < tokens.size() && nextTokenTypeIn(tokenTypes);
    }

    public boolean hasNext(String ... tokens) {
        return index < this.tokens.size() && nextTokenIn(tokens);
    }

}
