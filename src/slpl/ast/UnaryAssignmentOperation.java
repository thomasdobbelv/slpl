package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.Operator;
import slpl.util.TypeCheckerContext;

public class UnaryAssignmentOperation extends AssignmentOperation {

    private boolean isPrefixOperation;

    public UnaryAssignmentOperation(String name, Operator operator, boolean isPrefixOperation) {
        super(name, operator, null);
        this.isPrefixOperation = isPrefixOperation;
    }

    @Override
    public AST evaluate(Context context) {
        Number before = (Number) context.get(assigneeName).evaluate(context);
        double val = before.getValue() + (operator == Operator.INCR ? 1 : -1);
        Number after = new Number(val + "");
        context.set(assigneeName, after);
        return isPrefixOperation ? after : before;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        if(!typeCheckerContext.contains(assigneeName)) {
            throw TypeCheckException.undefinedName(assigneeName);
        }
        String assigneeType = typeCheckerContext.getType(assigneeName);
        String numberType = PrimitiveType.NUMBER.typeName();
        if(assigneeType.equals(numberType)) {
            return numberType;
        }
        throw TypeCheckException.undefinedOperation(operator, assigneeType);
    }

    @Override
    public String toString() {
        if(isPrefixOperation) {
            return String.format("(%s %s)", operator, assigneeName);
        } else {
            return String.format("(%s %s)", assigneeName, operator);
        }
    }

}
