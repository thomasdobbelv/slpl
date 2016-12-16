package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Str extends AST {

    private static final Type type = new Type("string");
    private final String value;

    public Str(String value) {
        this.value = value.substring(1, value.length() - 1);
    }

    @Override
    public String toString() {
        return String.format("\"%s\")", value);
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

    public String value() {
        return value;
    }

}
