package slpl.util;

import slpl.syntax.lexical.Token;
import slpl.syntax.lexical.TokenType;
import slpl.syntax.lexical.TokenTypeClass;

public enum Operator {

    OR("||", 2, 0, Fixity.LEFT),
    AND("&&", 2, 1, Fixity.LEFT),
    NOT("!", 1, 2, Fixity.RIGHT),
    EQ("==", 2, 3, Fixity.LEFT),
    NEQ("!=", 2, 3, Fixity.LEFT),
    GTE(">=", 2, 3, Fixity.LEFT),
    LTE("<=", 2, 3, Fixity.LEFT),
    GT(">", 2, 3, Fixity.LEFT),
    LT("<", 2, 3, Fixity.LEFT),
    ADD("+", 2, 4, Fixity.LEFT),
    SUB("-", 2, 4, Fixity.LEFT),
    MUL("*", 2, 5, Fixity.LEFT),
    DIV("/", 2, 5, Fixity.LEFT),
    ADDITIVE_INVERSE("-", 1, 6, Fixity.RIGHT),
    ADDEQ("+=", 2, -1, Fixity.LEFT),
    SUBEQ("-=", 2, -1, Fixity.LEFT),
    MULEQ("*=", 2, -1, Fixity.LEFT),
    DIVEQ("/=", 2, -1, Fixity.LEFT),
    ASSIGN("=", 2, -1, Fixity.LEFT),
    INCR("++", 1, -1, Fixity.LEFT),
    DECR("--", 1, -1, Fixity.LEFT);

    private final String operatorSymbol;
    private final Fixity fixity;
    private final int precedence;
    private final int arity;

    Operator(String operatorSymbol, int arity, int precedence, Fixity fixity) {
        this.operatorSymbol = operatorSymbol;
        this.arity = arity;
        this.precedence = precedence;
        this.fixity = fixity;
    }

    /**
     * @param t A {@link Token}
     * @return The {@link Operator} associated with the {@link Token} t, or null if no such Operator exists.
     */
    public static Operator fromToken(Token t) {
        String operatorSymbol = t.getContent();
        int arity = -1;
        TokenType tt = t.getType();
        if(tt.instanceOf(TokenTypeClass.UNARY_OPERATOR)) {
            arity = 1;
        } else if(tt.instanceOf(TokenTypeClass.BINARY_OPERATOR)) {
            arity = 2;
        }
        if(arity >= 0) {
            for (Operator o : values()) {
                if (o.operatorSymbol.equals(operatorSymbol) && o.arity == arity) {
                    return o;
                }
            }
        }
        return null;
    }

    public int getArity() {
        return this.arity;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public Fixity getFixity() {
        return fixity;
    }

    @Override
    public String toString() {
        return operatorSymbol;
    }

    public enum Fixity {
        LEFT, RIGHT
    }
}
