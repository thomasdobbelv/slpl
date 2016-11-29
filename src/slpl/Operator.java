package slpl;

import org.jetbrains.annotations.NotNull;

public enum Operator {

    ADD("+", 2, 0, Fixity.LEFT),
    SUB("-", 2, 0, Fixity.LEFT),
    MUL("*", 2, 1, Fixity.LEFT),
    DIV("/", 2, 1, Fixity.LEFT),
    ADDITIVE_INVERSE("-", 1, 2, Fixity.RIGHT);

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
        switch (t.getType().getTypeClass()) {
            case UNARY_OPERATOR:
                arity = 1;
                break;
            case BINARY_OPERATOR:
                arity = 2;
        }
        for (Operator o : values()) {
            if (o.operatorSymbol.equals(operatorSymbol) && o.arity == arity) {
                return o;
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

    public enum Fixity {
        LEFT, RIGHT
    }
}
