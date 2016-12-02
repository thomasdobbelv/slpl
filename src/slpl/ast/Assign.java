package slpl.ast;

import slpl.util.Context;

public class Assign extends AST {

    private AST value;
    private String name;

    public Assign(AST value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public AST evaluate(Context context) {
        value = value.evaluate(context);
        context.set(name, value);
        return value;
    }

    @Override
    public String toString() {
        return String.format("(Assign %s %s)", value, name);
    }
}
