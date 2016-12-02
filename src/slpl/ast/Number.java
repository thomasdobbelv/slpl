package slpl.ast;

import slpl.util.Context;

public class Number extends AST {

    private double value;

    public Number(String number) {
        value = Double.parseDouble(number);
    }

    public double getValue() {
        return value;
    }

    @Override
    public AST evaluate(Context _) {
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Number %f)", value);
    }
}
