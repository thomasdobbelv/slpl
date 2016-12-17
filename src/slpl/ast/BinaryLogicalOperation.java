package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.Operator;

public class BinaryLogicalOperation extends AST {

    private Operator operator;
    private AST arg1, arg2;

    public BinaryLogicalOperation(Operator operator, AST arg1, AST arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public String toString() {
        return String.format("(%s %s, %s)", operator, arg1, arg2);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        switch (operator) {
            case AND:
                return new Boolean(((Boolean) arg1.evaluate(env, mem)).value() && ((Boolean) arg2.evaluate(env, mem)).value());
            case OR:
                return new Boolean(((Boolean) arg1.evaluate(env, mem)).value() || ((Boolean) arg2.evaluate(env, mem)).value());
            default:
                throw new UnsupportedOperationException(operator.toString());
        }
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        Type t1 = arg1.checkType(env), t2 = arg2.checkType(env);
        if (t1.equals(t2) && t2.equals(Boolean.type())) {
            return Boolean.type();
        } else {
            Type[] expected = {Boolean.type(), Boolean.type()}, was = {t1, t2};
            throw TypeError.expected(expected, was);
        }
    }
}
