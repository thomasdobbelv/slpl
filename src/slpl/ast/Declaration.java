package slpl.ast;

import slpl.util.Context;

public class Declaration extends AST {

    private String name;
    private String type;
    private AST definition;

    public Declaration(String name, String type, AST definition) {
        this.name = name;
        this.type = type;
        this.definition = definition;
    }

    public Declaration(String name, String type) {
        this.name = name;
        this.type = type;
        definition = new Null();
    }

    @Override
    public AST evaluate(Context context) {
        context.add(new Variable(name, type, definition.evaluate(context)));
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Declaration %s %s)", name, definition);
    }
}
