package slpl.ast;

import slpl.util.Context;

public class For extends AST {

    private Declaration init;
    private AST condition;
    private Assign update;
    private Block body;

    public For(Declaration init, AST condition, Assign update, Block body) {
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    @Override
    public AST evaluate(Context context) {
        if(init != null) {
            init.evaluate(context);
        }
        if(condition != null && update != null) {
            while(((Boolean) condition.evaluate(context)).getValue()) {
                body.evaluate(context);
                update.evaluate(context);
            }
        } else if(condition != null) {
            while(((Boolean) condition.evaluate(context)).getValue()) {
                body.evaluate(context);
            }
        } else if(update != null) {
            while(true) {
                body.evaluate(context); // TODO: add break statement
                update.evaluate(context);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("(For %s; %s; %s %s)", init, condition, update, body);
    }
}
