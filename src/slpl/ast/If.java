package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

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
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        String conditionType = condition.typeCheck(typeCheckerContext);
        if(!conditionType.equals(PrimitiveType.BOOLEAN.getTypeName())) {
            throw new TypeCheckException("If-statement condition is not a boolean expression");
        }
        then.typeCheck(typeCheckerContext);
        if(else_ != null) {
            else_.typeCheck(typeCheckerContext);
        }
        return PrimitiveType.VOID.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(If Condition: %s, Then: %s, Else: %s)", condition, then, else_);
    }
}
