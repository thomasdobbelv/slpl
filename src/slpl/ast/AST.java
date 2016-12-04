package slpl.ast;

import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public abstract class AST {

    public abstract AST evaluate(Context context);

    public abstract String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException;

    public abstract String toString();

}
