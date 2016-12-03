package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;

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
    public String typeCheck(Context context) throws TypeError {
        if(condition.typeCheck(context).equals(PrimitiveType.BOOLEAN.getTypeName())) {
            then.typeCheck(context);
            if(else_ != null) {
                else_.typeCheck(context);
            }
        }
        return PrimitiveType.VOID.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(If %s Then %s Else %s)", condition, then, else_);
    }
}
