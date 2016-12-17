package slpl.util;

import slpl.syntax.lexical.Token;
import slpl.syntax.lexical.TokenType;
import slpl.err.ParseException;

import java.util.List;

public class TokenStream {

    private final List<Token> tokens;
    private int position;

    public TokenStream(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void reset(int position) {
        this.position = position;
    }

    public int position() {
        return position;
    }

    public void replace(Token t) {
        tokens.set(position, t);
    }

    public void expect(TokenType ... tokenTypes) throws ParseException {
        if (position >= tokens.size()) {
            throw ParseException.expected(tokens.get(tokens.size() - 1), tokenTypes);
        } else if (!nextTokenTypeIn(tokenTypes)) {
            if (position > 0) {
                throw ParseException.expected(tokens.get(position - 1), tokenTypes);
            } else {
                throw ParseException.expected(0, 0, tokenTypes);
            }
        }
    }

    public void expect(String ... tokens) throws ParseException {
        if (position >= this.tokens.size()) {
            throw ParseException.expected(this.tokens.get(this.tokens.size() - 1), tokens);
        } else if (!nextTokenIn(tokens)) {
            if (position > 0) {
                throw ParseException.expected(this.tokens.get(position - 1), tokens);
            } else {
                throw ParseException.expected(0, 0, tokens);
            }
        }
    }

    private boolean nextTokenTypeIn(TokenType[] tokenTypes) {
        TokenType nextTokenType = tokens.get(position).type();
        for(TokenType tt : tokenTypes) {
            if(nextTokenType == tt) {
                return true;
            }
        }
        return false;
    }

    private boolean nextTokenIn(String[] tokens) {
        String nextToken = this.tokens.get(position).content();
        for(String token : tokens) {
            if(nextToken.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public Token consume() throws ParseException {
        if(position >= tokens.size()) {
            throw ParseException.unexpectedEOF(tokens.get(tokens.size() - 1));
        }
        return tokens.get(position++);
    }

    public Token inspect() throws ParseException {
        Token t = consume();
        --position;
        return t;
    }

    public boolean hasNext() {
        return position < tokens.size();
    }

    public boolean hasNext(TokenType ... tokenTypes) {
        return position < tokens.size() && nextTokenTypeIn(tokenTypes);
    }

    public boolean hasNext(String ... tokens) {
        return position < this.tokens.size() && nextTokenIn(tokens);
    }

}
