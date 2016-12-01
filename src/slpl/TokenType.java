package slpl;

import java.util.HashSet;
import static slpl.TokenTypeClass.*;

public enum TokenType {

    NUMBER("[0-9]+(\\.[0-9]+)?", VALUE),
    FOR("for", KEYWORD),
    WHILE("while", KEYWORD),
    TRUE("true", KEYWORD),
    FALSE("false", KEYWORD),
    IF("if", KEYWORD),
    ELSE("else", KEYWORD),
    IDENTIFIER("[A-Za-z][A-Za-z0-9]*", VALUE),
    INCR("\\+\\+", UNARY_OPERATOR, ARITHMETIC_OPERATOR),
    DECR("--", UNARY_OPERATOR, ARITHMETIC_OPERATOR),
    ADDEQ("\\+=", BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    SUBEQ("-=", BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    MULEQ("\\*=", BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    DIVEQ("/=", BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    ADD("\\+", BINARY_OPERATOR, ARITHMETIC_OPERATOR),
    SUB("-", BINARY_OPERATOR, ARITHMETIC_OPERATOR),
    MUL("\\*", BINARY_OPERATOR, ARITHMETIC_OPERATOR),
    DIV("/", BINARY_OPERATOR, ARITHMETIC_OPERATOR),
    ADDINV("-", UNARY_OPERATOR, ARITHMETIC_OPERATOR),
    EQ("==", BINARY_OPERATOR, RELATIONAL_OPERATOR),
    NEQ("!=", BINARY_OPERATOR, RELATIONAL_OPERATOR),
    GTE(">=", BINARY_OPERATOR, RELATIONAL_OPERATOR),
    LTE("<=", BINARY_OPERATOR, RELATIONAL_OPERATOR),
    GT(">", BINARY_OPERATOR, RELATIONAL_OPERATOR),
    LT("<", BINARY_OPERATOR, RELATIONAL_OPERATOR),
    NOT("!", UNARY_OPERATOR, LOGICAL_OPERATOR),
    AND("&&", BINARY_OPERATOR, LOGICAL_OPERATOR),
    OR("\\|\\|", BINARY_OPERATOR, LOGICAL_OPERATOR),
    ASSIGN("=", BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    COMMA(",", SYMBOL),
    DOT("\\.", SYMBOL),
    COLON(":", SYMBOL),
    SEMICOLON(";",SYMBOL),
    LSQR("\\[", BRACKET),
    RSQR("]", BRACKET),
    LCRL("\\{", BRACKET),
    RCRL("}", BRACKET),
    LPAR("\\(", BRACKET),
    RPAR("\\)", BRACKET),
    EOL("\\r?\\n", LAYOUT),
    TAB("\\t", LAYOUT),
    WS("[ \\f]+", LAYOUT);

    private final String pattern;
    private final HashSet<TokenTypeClass> typeClasses = new HashSet<>();

    TokenType(String pattern, TokenTypeClass ... tokenTypeClasses) {
        this.pattern = pattern;
        for(TokenTypeClass ttc : tokenTypeClasses) {
            typeClasses.add(ttc);
        }
    }

    public String getPattern() {
        return pattern;
    }

    public boolean instanceOf(TokenTypeClass ttc) {
        return typeClasses.contains(ttc);
    }

}
