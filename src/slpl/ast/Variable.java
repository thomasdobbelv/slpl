package slpl.ast;

import slpl.util.Context;

public class Variable extends AST {

    private String name;
    private Type type;
    private AST value;

    public Variable(String name, Type type, AST value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public AST evaluate(Context context) {
        return value.evaluate(context);
    }

    @Override
    public String toString() {
        return String.format("(Variable %s %s %s)", name, type, value);
    }
}
