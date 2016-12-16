package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Null extends AST {

    private static final Type type = new Type("null");

    @Override
    public String toString() {
        return type.name();
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
}
