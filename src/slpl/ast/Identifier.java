package slpl.ast;

import slpl.util.Context;

public class Identifier extends AST {

    private String value;

    public Identifier(String identifier) {
        this.value = identifier;
    }

    public String getValue() {
        return value;
    }

    @Override
    public AST evaluate(Context context) {
        if(context.get(value) instanceof Variable) {
            return context.get(value).evaluate(context);
        } else {
            return context.get(value);
        }
    }

    @Override
    public String toString() {
        return String.format("(Identifier %s)", value);
    }
}
