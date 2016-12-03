package slpl.ast;

import slpl.PrimitiveType;
import slpl.util.Context;

public class Boolean extends AST {

    private boolean value;

    public Boolean(boolean value) {
        this.value = value;
    }

    @Override
    public AST evaluate(Context _) {
        return this;
    }

    @Override
    public String typeCheck(Context context) {
        return PrimitiveType.BOOLEAN.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(Boolean %s)", value);
    }

    public boolean getValue() {
        return value;
    }
}
