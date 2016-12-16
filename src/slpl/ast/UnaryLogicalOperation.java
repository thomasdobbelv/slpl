package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.Operator;

public class UnaryLogicalOperation extends AST {

    private Operator operator;
    private AST arg;

    public UnaryLogicalOperation(Operator operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", operator, arg);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        switch (operator) {
            case NOT:
                return new Boolean(!((Boolean) arg.evaluate(env, mem)).value());
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        Type t = arg.checkType(env);
        if (t.equals(Boolean.type())) {
            return Boolean.type();
        }
        Type[] expected = {Boolean.type()}, was = {t};
        throw TypeError.expected(expected, was);
    }
}
