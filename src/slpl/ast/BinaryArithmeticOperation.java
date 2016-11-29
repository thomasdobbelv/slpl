package slpl.ast;

public class BinaryArithmeticOperation extends AST {

    private String operator;
    private AST arg1, arg2;

    public BinaryArithmeticOperation(String operator, AST arg1, AST arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public AST evaluate() {
        Number num1 = (Number) arg1.evaluate(), num2 = (Number) arg2.evaluate();
        double val1 = num1.getValue(), val2 = num2.getValue();
        switch (operator) {
            case "+": return new Number((val1 + val2) + "");
            case "-": return new Number((val1 - val2) + "");
            case "*": return new Number((val1 * val2) + "");
            case "/": return new Number((val1 / val2) + "");
        }
        throw new UnsupportedOperationException(operator);
    }

    @Override
    public String toString() {
        return String.format("(BinaryArithmeticOperation %s %s %s)", operator, arg1, arg2);
    }
}
