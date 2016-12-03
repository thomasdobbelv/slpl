package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;
import slpl.util.Operator;

public class BinaryLogicalOperation extends AST {

    private Operator operator;
    private AST arg1, arg2;

    public BinaryLogicalOperation(Operator operator, AST arg1, AST arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public AST evaluate(Context context) {
        Boolean b1 = (Boolean) arg1.evaluate(context), b2 = (Boolean) arg2.evaluate(context);
        switch (operator) {
            case OR:
                return new Boolean(b1.getValue() || b2.getValue());
            case AND:
                return new Boolean(b1.getValue() && b2.getValue());
        }
        throw new UnsupportedOperationException(operator.toString());
    }

    @Override
    public String typeCheck(Context context) throws TypeError {
        String arg1t = arg1.typeCheck(context), arg2t = arg2.typeCheck(context);
        String expected = PrimitiveType.BOOLEAN.getTypeName();
        if(!(arg1t.equals(expected) && arg2t.equals(expected))) {
            throw TypeError.expected(expected, arg1t);
        }
        return expected;
    }

    @Override
    public String toString() {
        return String.format("(BinaryLogicalOperation %s %s %s)", operator, arg1, arg2);
    }
}
