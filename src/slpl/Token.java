package slpl;

public class Token {

    private final TokenType type;
    private final String content;

    public Token(TokenType type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", type.name(), content);
    }

    public TokenType getType() {
        return this.type;
    }

    public String getContent() {
        return this.content;
    }

}
