package slpl;

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
     *
     * @param operatorSymbol
     * @return The {@link Operator} associated with <b>operatorSymbol</b>, or null if no such operator exists.
     */
    public static Operator fromString(String operatorSymbol, int arity) {
        for(Operator o : values()) {
            if(o.operatorSymbol.equals(operatorSymbol) && o.arity == arity) {
                return o;
            }
        }
        return null;
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
