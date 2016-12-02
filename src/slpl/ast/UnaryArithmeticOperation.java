package slpl.ast;

import slpl.util.Context;

public class UnaryArithmeticOperation extends AST {

    private String operator;
    private AST arg;

    public UnaryArithmeticOperation(String operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public AST evaluate(Context context) {
        Number num = (Number) arg.evaluate(context);
        switch (operator) {
            case "-":
                return new Number((-num.getValue()) + "");
        }
        throw new UnsupportedOperationException(operator);
    }

    @Override
    public String toString() {
        return String.format("(UnaryArithmeticOperation %s %s)", operator, arg);
    }

}
