package slpl.ast;

import slpl.util.Context;

public class Module extends AST {

    private String name;
    private AST body;

    public Module(String name, AST body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public AST evaluate(Context context) {
        // TODO: consider what it means to evaluate a module
        body.evaluate(context);
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Module %s %s)", name, body);
    }
}
