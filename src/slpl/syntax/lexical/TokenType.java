package slpl.syntax.lexical;

import java.util.HashSet;
import static slpl.syntax.lexical.TokenTypeClass.*;

public enum TokenType {

    FOR("for", KEYWORD),
    WHILE("while", KEYWORD),
    TRUE("true", KEYWORD, VALUE),
    FALSE("false", KEYWORD, VALUE),
    IF("if", KEYWORD),
    ELSE("else", KEYWORD),
    MODULE("module", KEYWORD),
    FUNCTION("function", KEYWORD),
    PRINTLN("println", KEYWORD),
    PRINT("print", KEYWORD),
    RETURN("return", KEYWORD),
    NULL("null", KEYWORD, VALUE),
    ID("[A-Za-z][A-Za-z0-9]*", VALUE),
    FID(ID.pattern),
    NUM("[0-9]+(\\.[0-9]+)?", VALUE),
    STRING("\"[^\"\\\\]*(\\\\.[^\"\\\\]*)*\"", VALUE),
    ARROW("->", SYMBOL),
    INCR("\\+\\+", OPERATOR, UNARY_OPERATOR, ASSIGNMENT_OPERATOR),
    DECR("--", OPERATOR, UNARY_OPERATOR, ASSIGNMENT_OPERATOR),
    ADDEQ("\\+=", OPERATOR, BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    SUBEQ("-=", OPERATOR, BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    MULEQ("\\*=", OPERATOR, BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    DIVEQ("/=", OPERATOR, BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    ADD("\\+", OPERATOR, BINARY_OPERATOR, ARITHMETIC_OPERATOR),
    SUB("-", OPERATOR, BINARY_OPERATOR, ARITHMETIC_OPERATOR),
    MUL("\\*", OPERATOR, BINARY_OPERATOR, ARITHMETIC_OPERATOR),
    DIV("/", OPERATOR, BINARY_OPERATOR, ARITHMETIC_OPERATOR),
    ADDINV("-", OPERATOR, UNARY_OPERATOR, ARITHMETIC_OPERATOR),
    EQ("==", OPERATOR, BINARY_OPERATOR, RELATIONAL_OPERATOR),
    NEQ("!=", OPERATOR, BINARY_OPERATOR, RELATIONAL_OPERATOR),
    GTE(">=", OPERATOR, BINARY_OPERATOR, RELATIONAL_OPERATOR),
    LTE("<=", OPERATOR, BINARY_OPERATOR, RELATIONAL_OPERATOR),
    GT(">", OPERATOR, BINARY_OPERATOR, RELATIONAL_OPERATOR),
    LT("<", OPERATOR, BINARY_OPERATOR, RELATIONAL_OPERATOR),
    NOT("!", OPERATOR, UNARY_OPERATOR, LOGICAL_OPERATOR),
    AND("&&", OPERATOR, BINARY_OPERATOR, LOGICAL_OPERATOR),
    OR("\\|\\|", OPERATOR, BINARY_OPERATOR, LOGICAL_OPERATOR),
    ASSIGN("=", OPERATOR, BINARY_OPERATOR, ASSIGNMENT_OPERATOR),
    COMMA(",", SYMBOL),
    DOT("\\.", SYMBOL),
    COLON(":", SYMBOL),
    SEMICOLON(";", SYMBOL),
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

    public String pattern() {
        return pattern;
    }

    public boolean instanceOf(TokenTypeClass ttc) {
        return typeClasses.contains(ttc);
    }

}
