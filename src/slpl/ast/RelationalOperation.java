package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.Operator;

public class RelationalOperation extends AST {

    private Operator operator;
    private AST arg1, arg2;

    public RelationalOperation(Operator operator, AST arg1, AST arg2) {
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
            case EQ:
                return new Boolean(((Number) arg1.evaluate(env, mem)).value() == ((Number) arg2.evaluate(env, mem)).value());
            case NEQ:
                return new Boolean(((Number) arg1.evaluate(env, mem)).value() != ((Number) arg2.evaluate(env, mem)).value());
            case GTE:
                return new Boolean(((Number) arg1.evaluate(env, mem)).value() >= ((Number) arg2.evaluate(env, mem)).value());
            case LTE:
                return new Boolean(((Number) arg1.evaluate(env, mem)).value() <= ((Number) arg2.evaluate(env, mem)).value());
            case GT:
                return new Boolean(((Number) arg1.evaluate(env, mem)).value() > ((Number) arg2.evaluate(env, mem)).value());
            case LT:
                return new Boolean(((Number) arg1.evaluate(env, mem)).value() < ((Number) arg2.evaluate(env, mem)).value());
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        Type t1 = arg1.checkType(env), t2 = arg2.checkType(env);
        if (t1.equals(t2) && t2.equals(Number.type())) {
            return Boolean.type();
        } else {
            Type[] expected = {Number.type(), Number.type()}, was = {t1, t2};
            throw TypeError.expected(expected, was);
        }
    }
}
