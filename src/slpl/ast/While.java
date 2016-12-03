package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;

public class While extends AST {

    private AST condition;
    private Block body;

    public While(AST condition, Block body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public AST evaluate(Context context) {
        while(((Boolean) condition.evaluate(context)).getValue()) {
            body.evaluate(context);
        }
        return this;
    }

    @Override
    public String typeCheck(Context context) throws TypeError {
        body.typeCheck(context);
        return PrimitiveType.VOID.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(While %s %s)", condition, body);
    }
}
