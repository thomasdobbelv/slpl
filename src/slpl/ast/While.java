package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class While extends AST {

    private AST condition;
    private Block body;

    public While(AST condition, Block body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("(While Condition: %s, Body: %s)", condition, body);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        Environment env_ = env.clone();
        while(((Boolean) condition.evaluate(env_, mem)).value()) {
            body.evaluate(env_, mem);
        }
        mem.unwind(env_.size() - env.size());
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        Environment env_ = env.clone();
        Type t = condition.checkType(env_);
        if(!t.equals(Boolean.type())) {
            throw new TypeError("while-loop condition is not a boolean statement");
        }
        body.checkType(env_);
        return Void.type();
    }
}
