package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;
import slpl.util.Operator;

public class UnaryLogicalOperation extends AST {

    private Operator operator;
    private AST arg;

    public UnaryLogicalOperation(Operator operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public AST evaluate(Context context) {
        Boolean b = (Boolean) arg.evaluate(context);
        switch (operator) {
            case NOT:
                return new Boolean(!b.getValue());
        }
        throw new UnsupportedOperationException(operator.toString());
    }

    @Override
    public String typeCheck(Context context) throws TypeError {
        String argt = arg.typeCheck(context);
        String expected = PrimitiveType.BOOLEAN.getTypeName();
        if(!argt.equals(expected)) {
            throw TypeError.expected(expected, argt);
        }
        return expected;
    }

    @Override
    public String toString() {
        return String.format("(UnaryLogicalOperation %s %s)", operator, arg);
    }
}
