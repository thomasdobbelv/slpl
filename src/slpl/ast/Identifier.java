package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Identifier extends AST {

    private String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        return env.lookup(name).evaluate(env, mem);
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        if(!env.contains(name)) {
            throw TypeError.nameOutOfScope(name);
        } else {
            return env.lookup(name).checkType(env);
        }
    }
}
