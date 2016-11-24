public class Token {

    private TokenType type;
    private String content;

    public Token(TokenType type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", type.name(), content);
    }

}
