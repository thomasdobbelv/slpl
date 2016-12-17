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
        throw new UnsupportedOperationException();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        return new Type("NYI for return");
    }
}
