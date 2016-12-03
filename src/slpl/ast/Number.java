package slpl.ast;

import slpl.PrimitiveType;
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
    public String typeCheck(Context context) {
        return PrimitiveType.NUMBER.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(Number %f)", value);
    }
}
