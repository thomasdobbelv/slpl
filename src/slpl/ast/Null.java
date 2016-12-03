package slpl.ast;

import slpl.util.Context;

public class Null extends AST {

    @Override
    public AST evaluate(Context _) {
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Null)");
    }
}
