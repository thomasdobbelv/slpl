package slpl.ast;

import slpl.util.Context;

public class BinaryArithmeticOperation extends AST {

    private String operator;
    private AST arg1, arg2;

    public BinaryArithmeticOperation(String operator, AST arg1, AST arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public AST evaluate(Context context) {
        Number num1 = (Number) arg1.evaluate(context), num2 = (Number) arg2.evaluate(context);
        switch (operator) {
            case "+":
                return new Number((num1.getValue() + num2.getValue()) + "");
            case "-":
                return new Number((num1.getValue() - num2.getValue()) + "");
            case "*":
                return new Number((num1.getValue() * num2.getValue()) + "");
            case "/":
                return new Number((num1.getValue() / num2.getValue()) + "");
        }
        throw new UnsupportedOperationException(operator);
    }

    @Override
    public String toString() {
        return String.format("(BinaryArithmeticOperation %s %s %s)", operator, arg1, arg2);
    }
}
