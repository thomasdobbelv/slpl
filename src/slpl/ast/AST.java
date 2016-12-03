package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Context;

public abstract class AST {

    public abstract AST evaluate(Context context);

    public abstract String typeCheck(Context context) throws TypeError;

    public abstract String toString();

}
