package slpl.ast;

public class UnaryArithmeticOperation extends Ast {

    private String operator;
    private Ast arg;

    public UnaryArithmeticOperation(String operator, Ast arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public Ast evaluate() {
        if(operator.equals("-")) {
            Number num = (Number) arg.evaluate();
            return new Number((-num.getValue()) + "");
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("(UnaryArithmeticOperation %s %s)", operator, arg);
    }

}
