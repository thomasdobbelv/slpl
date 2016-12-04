package slpl.ast;

import slpl.PrimitiveType;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class Null extends AST {

    @Override
    public AST evaluate(Context _) {
        return this;
    }

    @Override
    public String typeCheck(TypeCheckerContext _) {
        return PrimitiveType.NULL.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(Null)");
    }
}
