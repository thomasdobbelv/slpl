package slpl.ast;

import slpl.PrimitiveType;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

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
    public String typeCheck(TypeCheckerContext _) {
        return PrimitiveType.BOOLEAN.getTypeName();
    }

    @Override
    public String toString() {
        return value + "";
    }

    public boolean getValue() {
        return value;
    }
}
