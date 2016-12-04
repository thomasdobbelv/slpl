package slpl.ast;

import slpl.util.Context;

public class Identifier extends AST {

    private String id;

    public Identifier(String id) {
        this.id = id;
    }

    @Override
    public AST evaluate(Context context) {
        return context.get(id).evaluate(context);
    }

    @Override
    public String toString() {
        return String.format("(Identifier %s)", id);
    }
}
