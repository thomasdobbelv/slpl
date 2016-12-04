package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

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
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        String conditionType = condition.typeCheck(typeCheckerContext);
        if(!conditionType.equals(PrimitiveType.BOOLEAN.getTypeName())) {
            throw new TypeCheckException("While-loop condition is not a boolean expression");
        }
        body.typeCheck(typeCheckerContext);
        return PrimitiveType.VOID.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(While %s %s)", condition, body);
    }
}
