package slpl;

public class Token {

    private final TokenType type;
    private final String content;
    private final int row, col;

    public Token(TokenType type, String content, int row, int col) {
        this.type = type;
        this.content = content;
        this.row = row;
        this.col = col;
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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
