package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Context;
import slpl.util.Operator;

public class AssignmentOperation extends AST {

    protected String name;
    protected Operator operator;
    private AST rvalue;

    public AssignmentOperation(String name, Operator operator, AST rvalue) {
        this.name = name;
        this.operator = operator;
        this.rvalue = rvalue;
    }

    @Override
    public AST evaluate(Context context) {
        AST rvalue = this.rvalue.evaluate(context);
        if(operator != Operator.ASSIGN) {
            double val1 = ((Number) context.get(name).getReferencedValue()).getValue();
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
        context.set(name, rvalue);
        return rvalue;
    }

    @Override
    public String typeCheck(Context context) throws TypeError {
        String rvaluet = rvalue.typeCheck(context);
        if(context.get(name).typeCheck(context).equals(rvaluet)) {
            return rvaluet;
        } else {
            throw TypeError.expected(context.get(name).typeCheck(context), rvaluet);
        }
    }

    @Override
    public String toString() {
        return String.format("(AssignmentOperation %s %s)", rvalue, name);
    }
}
