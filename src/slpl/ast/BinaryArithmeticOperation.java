package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;
import slpl.util.Operator;

public class BinaryArithmeticOperation extends AST {

    private Operator operator;
    private AST arg1, arg2;

    public BinaryArithmeticOperation(Operator operator, AST arg1, AST arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public AST evaluate(Context context) {
        Number num1 = (Number) arg1.evaluate(context), num2 = (Number) arg2.evaluate(context);
        switch (operator) {
            case ADD:
                return new Number((num1.getValue() + num2.getValue()) + "");
            case SUB:
                return new Number((num1.getValue() - num2.getValue()) + "");
            case MUL:
                return new Number((num1.getValue() * num2.getValue()) + "");
            case DIV:
                return new Number((num1.getValue() / num2.getValue()) + "");
        }
        throw new UnsupportedOperationException(operator.toString());
    }

    @Override
    public String typeCheck(Context context) throws TypeError {
        String arg1t = arg1.typeCheck(context), arg2t = arg2.typeCheck(context);
        String expected = PrimitiveType.NUMBER.getTypeName();
        if(!(arg1t.equals(expected) && arg2t.equals(expected))) {
            throw TypeError.expected(expected, arg1t);
        }
        return expected;
    }

    @Override
    public String toString() {
        return String.format("(BinaryArithmeticOperation %s %s %s)", operator, arg1, arg2);
    }
}
