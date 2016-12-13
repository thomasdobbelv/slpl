package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.Operator;
import slpl.util.TypeCheckerContext;

public class AssignmentOperation extends AST {

    protected String assigneeName;
    protected Operator operator;
    private AST rvalue;

    public AssignmentOperation(String assigneeName, Operator operator, AST rvalue) {
        this.assigneeName = assigneeName;
        this.operator = operator;
        this.rvalue = rvalue;
    }

    @Override
    public AST evaluate(Context context) {
        AST rvalue = this.rvalue.evaluate(context);
        if (operator != Operator.ASSIGN) {
            double val1 = ((Number) context.get(assigneeName).getValue()).getValue();
            double val2 = ((Number) rvalue).getValue();
            switch (operator) {
                case ADDEQ:
                    rvalue = new Number((val1 + val2) + "");
                    break;
                case SUBEQ:
                    rvalue = new Number((val1 - val2) + "");
                    break;
                case MULEQ:
                    rvalue = new Number((val1 * val2) + "");
                    break;
                case DIVEQ:
                    rvalue = new Number((val1 / val2) + "");
                    break;
            }
        }
        context.set(assigneeName, rvalue);
        return rvalue;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        if (!typeCheckerContext.contains(assigneeName)) {
            throw TypeCheckException.undefinedName(assigneeName);
        }
        String assigneeType = typeCheckerContext.getType(assigneeName);
        String rvalueType = rvalue.typeCheck(typeCheckerContext);
        if (rvalueType.equals(PrimitiveType.NULL.getTypeName())) {
            switch (operator) {
                case ADDEQ:
                case SUBEQ:
                case MULEQ:
                case DIVEQ:
                    throw TypeCheckException.undefinedOperation(operator, rvalueType);
                case ASSIGN:
                    return assigneeType;
            }
        } else if (assigneeType.equals(rvalueType)) {
            return assigneeType;
        }
        throw TypeCheckException.rvalueTypeAssigneeTypeMismatch(rvalueType, assigneeType, assigneeName);
    }

    @Override
    public String toString() {
        return String.format("(%s Value: %s, Assignee: %s)", operator, rvalue, assigneeName);
    }
}
