package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Boolean extends AST {

    private static final Type type = new Type("boolean");
    private boolean value;

    public Boolean(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        return this;
    }

    public Type checkType(Environment env) throws TypeError {
        return type;
    }

    public static Type type() {
        return type;
    }

    public boolean value() {
        return value;
    }

}
