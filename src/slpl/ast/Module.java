package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Module extends AST {

    private String name;
    private AST body;

    public Module(String name, AST body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("(Module Name: %s, Body: %s)", name, body);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        body.evaluate(env, mem);
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        body.checkType(env);
        return Void.type();
    }
}
