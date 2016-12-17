package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.StringConcatenator;

public class Function extends AST {

    private final String name;
    private final Declaration[] params;
    private final Type type;
    private final Block body;
    private Environment env;

    public Function(String name, Declaration[] params, Type type, Block body) {
        this.name = name;
        this.params = params;
        this.type = type;
        this.body = body;
    }

    public Block body() {
        return body;
    }

    public Declaration[] parameters() {
        return params;
    }

    public Environment environment() {
        return env;
    }

    @Override
    public String toString() {
        return String.format("(Function Name: %s, Parameters: (%s), Type: %s, Body: %s)", name, StringConcatenator.concatenate(", ", params), type, body);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        Variable var = new Variable(name, type, mem.push(this));
        env.bind(name, var);
        this.env = env.clone();
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        // TODO: check if function returns proper value (according to return type) for all inputs (all possible branches)
        env.bind(name, new Variable(name, type));
        this.env = env.clone();
        for(Declaration param : params) {
            this.env.bind(param.name(), new Variable(param.name(), param.type()));
        }
        body.checkType(this.env);
        this.env = null;
        return Void.type();
    }

}
