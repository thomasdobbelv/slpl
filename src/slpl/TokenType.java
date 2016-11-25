package slpl;

public enum TokenType {

    NUMBER("-?[0-9]+(\\.[0-9]+)?"),
    KEYWORD("let|for|while|true|false"),
    IDENTIFIER("[A-Za-z][A-Za-z0-9]*"),
    BINARYOPERATOR("[*/+=-]"),
    SYMBOL("[,.;]"),
    BRACKET("[<>(){}\\[\\]]"),
    WHITESPACE("[ \\t\\f\\r\\n]+");

    public final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }
}
