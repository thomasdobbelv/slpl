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

    public void expectOneOf(String ... tokens) throws ParseException {
        if (index >= this.tokens.size()) {
            throw ParseException.unexpectedEOF(this.tokens.get(this.tokens.size() - 1));
        } else if (!nextTokenIn(tokens)) {
            if (index > 0) {
                throw ParseException.expectedOneOf(this.tokens.get(index - 1), tokens);
            } else {
                throw ParseException.expectedOneOf(0, 0, tokens);
            }
        }
    }

    public void expectOneOf(TokenType ... tokenTypes) throws ParseException {
        if (index >= tokens.size()) {
            throw ParseException.unexpectedEOF(tokens.get(tokens.size() - 1));
        } else if (!nextTokenTypeIn(tokenTypes)) {
            if (index > 0) {
                throw ParseException.expectedOneOf(tokens.get(index - 1), tokenTypes);
            } else {
                throw ParseException.expectedOneOf(0, 0, tokenTypes);
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

    public Token consume() {
        return tokens.get(index++);
    }

    public boolean hasNext() {
        return index < tokens.size();
    }

}
