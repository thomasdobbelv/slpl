package slpl.ast;

import slpl.util.Context;

public class UnaryLogicalOperation extends AST {

    private String operator;
    private AST arg;

    public UnaryLogicalOperation(String operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public AST evaluate(Context context) {
        Boolean b = (Boolean) arg.evaluate(context);
        switch (operator) {
            case "!":
                return new Boolean(!b.getValue());
        }
        throw new UnsupportedOperationException(operator);
    }

    @Override
    public String toString() {
        return String.format("(UnaryLogicalOperation %s %s)", operator, arg);
    }
}
