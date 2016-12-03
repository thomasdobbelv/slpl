package slpl.ast;

import slpl.err.TypeError;
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
    public String typeCheck(Context context) throws TypeError {
        return context.get(id).typeCheck(context);
    }

    @Override
    public String toString() {
        return String.format("(Identifier %s)", id);
    }
}
