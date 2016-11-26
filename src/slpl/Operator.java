package slpl;

public enum Operator {

    ADD("+", 0, Fixity.LEFT),
    SUB("-", 0, Fixity.LEFT),
    MUL("*", 1, Fixity.LEFT),
    DIV("/", 1, Fixity.LEFT);

    private final Fixity fixity;
    private final int precedence;
    private final String operatorSymbol;

    Operator(String operatorSymbol, int precedence, Fixity fixity) {
        this.operatorSymbol = operatorSymbol;
        this.precedence = precedence;
        this.fixity = fixity;
    }

    /**
     *
     * @param operatorSymbol
     * @return The {@link Operator} associated with <b>operatorSymbol</b>, or null if no such operator exists.
     */
    public static Operator fromString(String operatorSymbol) {
        for(Operator o : values()) {
            if(o.operatorSymbol.equals(operatorSymbol)) {
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
