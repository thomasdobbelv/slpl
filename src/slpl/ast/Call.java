package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.StringConcatenator;

public class Call extends AST {

    private String callee;
    private AST[] args;

    public Call(String callee, AST[] args) {
        this.callee = callee;
        this.args = args;
    }

    @Override
    public String toString() {
        return String.format("(Call Name: %s, Arguments: [%s])", callee, StringConcatenator.concatenate(", ", args));
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        return new Type("NYI for Call");
    }

    public AST[] args() {
        return args;
    }

}
