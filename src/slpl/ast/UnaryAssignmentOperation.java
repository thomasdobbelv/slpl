package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;
import slpl.util.Operator;

public class UnaryAssignmentOperation extends AssignmentOperation {

    private boolean isPrefixOperation;

    public UnaryAssignmentOperation(String name, Operator operator, boolean isPrefixOperation) {
        super(name, operator, null);
        this.isPrefixOperation = isPrefixOperation;
    }

    @Override
    public AST evaluate(Context context) {
        Number before = (Number) context.get(name).evaluate(context);
        double val = before.getValue() + (operator == Operator.INCR ? 1 : -1);
        Number after = new Number(val + "");
        context.set(name, after);
        return isPrefixOperation ? after : before;
    }

    @Override
    public String typeCheck(Context context) throws TypeError {
        if(context.get(name).typeCheck(context).equals(PrimitiveType.NUMBER.getTypeName())) {
            return PrimitiveType.NUMBER.getTypeName();
        } else {
            throw TypeError.expected(PrimitiveType.NUMBER.getTypeName(), context.get(name).typeCheck(context));
        }
    }

}
