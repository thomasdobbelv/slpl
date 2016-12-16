package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public abstract class AST {

    public abstract String toString();

    public abstract AST evaluate(Environment env, Memory mem);

    public abstract Type checkType(Environment env) throws TypeError;

}
