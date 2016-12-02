package slpl.ast;

import slpl.util.Context;

public class BinaryLogicalOperation extends AST {

    private String operator;
    private AST arg1, arg2;

    public BinaryLogicalOperation(String operator, AST arg1, AST arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public AST evaluate(Context context) {
        Boolean b1 = (Boolean) arg1.evaluate(context), b2 = (Boolean) arg2.evaluate(context);
        switch (operator) {
            case "||":
                return new Boolean(b1.getValue() || b2.getValue());
            case "&&":
                return new Boolean(b1.getValue() && b2.getValue());
        }
        throw new UnsupportedOperationException(operator);
    }

    @Override
    public String toString() {
        return String.format("(BinaryLogicalOperation %s %s %s)", operator, arg1, arg2);
    }
}
