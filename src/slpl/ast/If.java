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
        Environment env_ = new Environment(env);
        if(((Boolean) condition.evaluate(env, mem)).value()) {
            then.evaluate(env_, mem);
        } else if(else_ != null) {
            else_.evaluate(env_, mem);
        }
        mem.unwind(env_);
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        if(!condition.checkType(env).equals(Boolean.type())) {
            throw new TypeError("if-statement condition is not a boolean statement");
        }
        Environment env_ = new Environment(env);
        then.checkType(env_);
        env_ = new Environment(env);
        else_.checkType(env_);
        return Void.type();
    }
}
