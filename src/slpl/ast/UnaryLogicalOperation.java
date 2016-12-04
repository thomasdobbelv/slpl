package slpl.ast;

import slpl.util.Context;
import slpl.util.Operator;

public class UnaryLogicalOperation extends AST {

    private Operator operator;
    private AST arg;

    public UnaryLogicalOperation(Operator operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public AST evaluate(Context context) {
        Boolean b = (Boolean) arg.evaluate(context);
        switch (operator) {
            case NOT:
                return new Boolean(!b.getValue());
        }
        throw new UnsupportedOperationException(operator.toString());
    }

    @Override
    public String toString() {
        return String.format("(UnaryLogicalOperation %s %s)", operator, arg);
    }
}
