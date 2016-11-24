public enum TokenType {

    NUMBER("-?[0-9]+"),
    KEYWORD("let|for|while"),
    IDENTIFIER("[A-Za-z][A-Za-z0-9]*"),
    BOOLEANLITERAL("true|false"),
    BINARYOPERATOR("[*/+-=]"),
    BRACKET("[<>(){}\\[\\]]"),
    SYMBOL("[,.;]"),
    WHITESPACE("[ \\t\\f\\r\\n]+");

    public final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }
}
