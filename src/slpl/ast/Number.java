package slpl.ast;

public class Number extends Ast {

    private double value;

    public Number(String number) {
        value = Double.parseDouble(number);
    }

    public double getValue() {
        return value;
    }

    @Override
    public Ast evaluate() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Number %f)", value);
    }
}
