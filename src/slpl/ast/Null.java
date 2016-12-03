package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;

public class Null extends AST {

    @Override
    public AST evaluate(Context _) {
        return this;
    }

    @Override
    public String typeCheck(Context context) throws TypeError {
        return PrimitiveType.NULL.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(Null)");
    }
}
