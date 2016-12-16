package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class If extends AST {

    private AST condition, else_;
    private Block then;

    public If(AST condition, Block then, AST else_) {
        this.condition = condition;
        this.then = then;
        this.else_ = else_;
    }

    public If(AST condition, Block then) {
        this(condition, then, null);
    }

    @Override
    public String toString() {
        return String.format("(If Condition: %s, Then: %s, Else: %s)", condition, then, else_);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        if(((Boolean) condition.evaluate(env, mem)).value()) {
            Environment env_ = env.clone();
            then.evaluate(env_, mem);
            mem.unwind(env_.size() - env.size());
        } else if(else_ != null) {
            Environment env_ = env.clone();
            else_.evaluate(env_, mem);
            mem.unwind(env_.size() - env.size());
        }
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        if(!condition.checkType(env).equals(Boolean.type())) {
            throw new TypeError("if-statement condition is not a boolean statement");
        }
        Environment env_ = env.clone();
        then.checkType(env_);
        env_ = env.clone();
        else_.checkType(env_);
        return Void.type();
    }
}
