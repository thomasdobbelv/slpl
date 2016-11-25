package slpl.ast;

public class Number extends Ast {

    private int intValue;
    private double floatValue;
    private boolean isInteger;

    public Number(String numberString) {
        floatValue = Double.parseDouble(numberString);
        intValue = (int) floatValue;
        isInteger = !numberString.contains(".");
    }

    public boolean isInteger() {
        return isInteger;
    }

    public int getIntValue() {
        return intValue;
    }

    public double getFloatValue() {
        return floatValue;
    }

    @Override
    public Ast evaluate() {
        return this;
    }
}
