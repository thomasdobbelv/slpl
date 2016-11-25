package slpl.ast;

public class Add extends Ast {

    private Ast arg0, arg1;

    public Add(Ast arg0, Ast arg1) {
        this.arg0 = arg0;
        this.arg1 = arg1;
    }

    @Override
    public Ast evaluate() {
        // Suppose typecheck has been performed. Then a0 and a1 are numbers fo sho.
        Number num0 = (Number) arg0.evaluate(), num1 = (Number) arg1.evaluate();
        double sum = num0.getFloatValue() + num1.getFloatValue();
        String numberString = (num0.isInteger() ? (int) sum : sum) + "";
        return new Number(numberString);
    }
}
