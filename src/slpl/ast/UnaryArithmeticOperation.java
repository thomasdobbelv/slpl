package slpl.ast;

public class UnaryArithmeticOperation extends AST {

    private String operator;
    private AST arg;

    public UnaryArithmeticOperation(String operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public AST evaluate() {
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
