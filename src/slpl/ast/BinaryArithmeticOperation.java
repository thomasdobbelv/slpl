package slpl.ast;

public class BinaryArithmeticOperation extends Ast {

    private String operator;
    private Ast arg0, arg1;

    public BinaryArithmeticOperation(String operator, Ast arg0, Ast arg1) {
        this.operator = operator;
        this.arg0 = arg0;
        this.arg1 = arg1;
    }

    @Override
    public Ast evaluate() {
        Number num0 = (Number) arg0.evaluate(), num1 = (Number) arg1.evaluate();
        double val0 = num0.getValue(), val1 = num1.getValue();
        switch (operator) {
            case "+": return new Number((val0 + val1) + "");
            case "-": return new Number((val0 - val1) + "");
            case "*": return new Number((val0 * val1) + "");
            case "/": return new Number((val0 / val1) + "");
        }
        throw new UnsupportedOperationException(operator);
    }

    @Override
    public String toString() {
        return String.format("(BinaryArithmeticOperation %s %s %s)", operator, arg0, arg1);
    }
}
