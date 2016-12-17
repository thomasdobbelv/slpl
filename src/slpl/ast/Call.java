package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.StringConcatenator;

public class Call extends AST {

    private String name;
    private AST[] args;

    public Call(String name, AST[] args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public String toString() {
        return String.format("(Call Name: %s, Arguments: [%s])", name, StringConcatenator.concatenate(", ", args));
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        if(!env.contains(name)) {
            throw TypeError.nameOutOfScope(name);
        }
        Variable var = env.lookup(name);
        return null;
    }

    public AST[] args() {
        return args;
    }

}
