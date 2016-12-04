package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

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
    public AST evaluate(Context context) {
        if (init != null) {
            init.evaluate(context);
        }
        if (update != null) {
            while (((Boolean) condition.evaluate(context)).getValue()) {
                body.evaluate(context);
                update.evaluate(context);
            }
        } else {
            while (((Boolean) condition.evaluate(context)).getValue()) {
                body.evaluate(context);
            }
        }
        return this;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        if (init != null) {
            init.typeCheck(typeCheckerContext);
        }
        String conditionType = condition.typeCheck(typeCheckerContext);
        if (!conditionType.equals(PrimitiveType.BOOLEAN.getTypeName())) {
            throw new TypeCheckException("For-loop condition is not a boolean expression");
        }
        if(update != null) {
            update.typeCheck(typeCheckerContext);
        }
        body.typeCheck(typeCheckerContext);
        return PrimitiveType.VOID.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(For %s; %s; %s %s)", init, condition, update, body);
    }
}
