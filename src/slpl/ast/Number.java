package slpl.ast;

import slpl.PrimitiveType;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

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
    public String typeCheck(TypeCheckerContext _) {
        return PrimitiveType.NUMBER.getTypeName();
    }

    @Override
    public String toString() {
        return value - (int) value == 0 ? ((int) value) + "" : value + "";
    }
}
