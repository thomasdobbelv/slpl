package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Variable extends AST {

    private static final int NOT_ASSIGNED = -1;

    private String name;
    private Type type;
    private int loc;

    public Variable(String name, Type type) {
        this(name, type, NOT_ASSIGNED);
    }

    public Variable(String name, Type type, int loc) {
        this.name = name;
        this.type = type;
        this.loc = loc;
    }

    @Override
    public String toString() {
        return String.format("(Variable Name: %s, Type: %s Location: %s)", name, type, loc == NOT_ASSIGNED ? "NA" : loc);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        return mem.get(loc).evaluate(env, mem);
    }

    public int location() {
        return loc;
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        return type;
    }

}
