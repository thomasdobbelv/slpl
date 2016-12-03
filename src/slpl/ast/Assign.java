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
        AST evaluated = value.evaluate(context);
        context.set(name, evaluated);
        return evaluated;
    }

    @Override
    public String toString() {
        return String.format("(Assign %s %s)", value, name);
    }
}
