package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Return extends AST {

    private AST rvalue;

    public Return(AST rvalue) {
        this.rvalue = rvalue;
    }

    @Override
    public String toString() {
        return String.format("(Return Value: %s)", rvalue);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        return rvalue.evaluate(env, mem);
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        return rvalue.checkType(env);
    }
}
