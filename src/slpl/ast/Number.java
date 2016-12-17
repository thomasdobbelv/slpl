package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Number extends AST {

    private static final Type type = new Type("number");
    private double value;

    public Number(String number) {
        value = Double.parseDouble(number);
    }

    @Override
    public String toString() {
        return (int) value == value ? ((int) value) + "" : value + "";
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        return this;
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        return type;
    }

    public static Type type() {
        return type;
    }

    public double value() {
        return value;
    }
}
