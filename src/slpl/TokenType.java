package slpl;

public enum TokenType {

    NUMBER("[0-9]+(\\.[0-9]+)?", TokenTypeClass.VALUE),
    FOR("for", TokenTypeClass.KEYWORD),
    WHILE("while", TokenTypeClass.KEYWORD),
    TRUE("true", TokenTypeClass.KEYWORD),
    FALSE("false", TokenTypeClass.KEYWORD),
    IF("if", TokenTypeClass.KEYWORD),
    ELSE("else", TokenTypeClass.KEYWORD),
    IDENTIFIER("[A-Za-z][A-Za-z0-9]*", TokenTypeClass.VALUE),
    INCR("\\+\\+", TokenTypeClass.UNARY_OPERATOR),
    DECR("--", TokenTypeClass.UNARY_OPERATOR),
    ADDEQ("\\+=", TokenTypeClass.BINARY_OPERATOR),
    SUBEQ("-=", TokenTypeClass.BINARY_OPERATOR),
    MULEQ("\\*=", TokenTypeClass.BINARY_OPERATOR),
    DIVEQ("/=", TokenTypeClass.BINARY_OPERATOR),
    ADD("\\+", TokenTypeClass.BINARY_OPERATOR),
    SUB("-", TokenTypeClass.BINARY_OPERATOR),
    MUL("\\*", TokenTypeClass.BINARY_OPERATOR),
    DIV("/", TokenTypeClass.BINARY_OPERATOR),
    ADDINV("-", TokenTypeClass.UNARY_OPERATOR),
    EQ("==", TokenTypeClass.BINARY_OPERATOR),
    NEQ("!=", TokenTypeClass.BINARY_OPERATOR),
    GTE(">=", TokenTypeClass.BINARY_OPERATOR),
    LTE("<=", TokenTypeClass.BINARY_OPERATOR),
    GT(">", TokenTypeClass.BINARY_OPERATOR),
    LT("<", TokenTypeClass.BINARY_OPERATOR),
    NOT("!", TokenTypeClass.UNARY_OPERATOR),
    AND("&&", TokenTypeClass.BINARY_OPERATOR),
    OR("\\|\\|", TokenTypeClass.BINARY_OPERATOR),
    ASSIGN("=", TokenTypeClass.BINARY_OPERATOR),
    COMMA(",", TokenTypeClass.SYMBOL),
    DOT("\\.", TokenTypeClass.SYMBOL),
    COLON(":", TokenTypeClass.SYMBOL),
    SEMICOLON(";",TokenTypeClass.SYMBOL),
    LSQR("\\[", TokenTypeClass.BRACKET),
    RSQR("]", TokenTypeClass.BRACKET),
    LCRL("\\{", TokenTypeClass.BRACKET),
    RCRL("}", TokenTypeClass.BRACKET),
    LPAR("\\(", TokenTypeClass.BRACKET),
    RPAR("\\)", TokenTypeClass.BRACKET),
    EOL("\\r?\\n", TokenTypeClass.LAYOUT),
    TAB("\\t", TokenTypeClass.LAYOUT),
    WS("[ \\f]+", TokenTypeClass.LAYOUT);

    private final String pattern;
    private final TokenTypeClass typeClass;

    TokenType(String pattern, TokenTypeClass typeClass) {
        this.pattern = pattern;
        this.typeClass = typeClass;
    }

    public String getPattern() {
        return pattern;
    }

    public TokenTypeClass getTypeClass() {
        return typeClass;
    }

}
