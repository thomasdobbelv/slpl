package slpl.ast;

import slpl.util.Context;

public abstract class AST {

    public abstract AST evaluate(Context context);

    public abstract String toString();

}
