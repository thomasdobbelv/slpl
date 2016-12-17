package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.StringConcatenator;

public class FunctionCall extends AST {

    private String name;
    private AST[] args;

    public FunctionCall(String name, AST[] args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public String toString() {
        return String.format("(FunctionCall Name: %s, Arguments: [%s])", name, StringConcatenator.concatenate(", ", args));
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        Function callee = (Function) mem.get(env.lookup(name).location());
        Environment env_ = new Environment(callee.environment());
        Declaration[] params = callee.parameters();
        for(int i = 0; i < args.length; ++i) {
            params[i].assign(args[i].evaluate(env, mem));
            params[i].evaluate(env_, mem);
        }
        AST ret = callee.body().evaluate(env_, mem);
        mem.unwind(env_);
        return ret;
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        // TODO: check that name actually refers to a function
        // TODO: check that the arguments passed in the call match the parameter list of the callee (we need memory for this, since we have to inspect the callee node)
        if(!env.contains(name)) {
            throw TypeError.nameOutOfScope(name);
        }
        return env.lookup(name).checkType(env);
    }

    public AST[] args() {
        return args;
    }

}
