package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;
import slpl.util.Operator;

public class UnaryArithmeticOperation extends AST {

    private Operator operator;
    private AST arg;

    public UnaryArithmeticOperation(Operator operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public AST evaluate(Context context) {
        Number num = (Number) arg.evaluate(context);
        switch (operator) {
            case ADDITIVE_INVERSE:
                return new Number((-num.getValue()) + "");
        }
        throw new UnsupportedOperationException(operator.toString());
    }

    @Override
    public String typeCheck(Context context) throws TypeError {
        String type = arg.typeCheck(context);
        String expected = PrimitiveType.NUMBER.getTypeName();
        if(!type.equals(expected)) {
            throw TypeError.expected(expected, type);
        }
        return expected;
    }

    @Override
    public String toString() {
        return String.format("(UnaryArithmeticOperation %s %s)", operator, arg);
    }

}
