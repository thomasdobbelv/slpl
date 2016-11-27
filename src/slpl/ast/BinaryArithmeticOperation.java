package slpl.ast;

public class BinaryArithmeticOperation extends Ast {

    private String operator;
    private Ast arg1, arg2;

    public BinaryArithmeticOperation(String operator, Ast arg1, Ast arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public Ast evaluate() {
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
