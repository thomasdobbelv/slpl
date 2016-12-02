package slpl.ast;

import slpl.util.Context;

public class If extends AST {

    private AST condition, then, else_;

    public If(AST condition, AST then, AST else_) {
        this.condition = condition;
        this.then = then;
        this.else_ = else_;
    }

    public If(AST condition, AST then) {
        this(condition, then, null); // TODO: pass some kind of empty node instead of null. VOID? define type of If else statement to be VOID
    }

    @Override
    public AST evaluate(Context context) {
        Boolean b = (Boolean) condition.evaluate(context);
        if(b.getValue()) {
            then.evaluate(context);
        } else if (else_ != null){
            else_.evaluate(context);
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("(If %s Then %s Else %s)", condition, then, else_);
    }
}
