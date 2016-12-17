package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class For extends AST {

    private Declaration init;
    private AST condition;
    private AssignmentOperation update;
    private Block body;

    public For(Declaration init, AST condition, AssignmentOperation update, Block body) {
        this.init = init;
        this.condition = condition == null ? new Boolean(true) : condition;
        this.update = update;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("(For Initializer: %s, Condition: %s, Update: %s, Body: %s)", init, condition, update, body);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        Environment env_ = new Environment(env);
        if(init != null) {
            init.evaluate(env_, mem);
        }
        if(update == null) {
            while(((Boolean) condition.evaluate(env_, mem)).value()) {
                body.evaluate(env_, mem);
            }
        } else {
            while(((Boolean) condition.evaluate(env_, mem)).value()) {
                body.evaluate(env_, mem);
                update.evaluate(env_, mem);
            }
        }
        mem.unwind(env_);
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        Environment env_ = new Environment(env);
        if(init != null) {
            init.checkType(env_);
        }
        if(condition != null && !condition.checkType(env_).equals(Boolean.type())) {
            throw new TypeError("for-loop condition is not a boolean statement");
        }
        if(update != null) {
            update.checkType(env_);
        }
        body.checkType(env_);
        return Void.type();
    }
}
