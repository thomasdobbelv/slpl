package slpl.ast;

import slpl.util.Context;

public class Declaration extends AST {

    String name;
    AST definition;

    public Declaration(String name, AST definition) {
        this.name = name;
        this.definition = definition;
    }

    public Declaration(String name) {
        definition = new Null();
        this.name = name;
    }

    @Override
    public AST evaluate(Context context) {
        definition = definition.evaluate(context);
        context.set(name, definition);
        return definition; // FIXME ?
    }

    @Override
    public String toString() {
        return String.format("(Declaration %s %s)", name, definition);
    }
}
