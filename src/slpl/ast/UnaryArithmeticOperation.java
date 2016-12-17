package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.Operator;

public class UnaryArithmeticOperation extends AST {

    private Operator operator;
    private AST arg;

    public UnaryArithmeticOperation(Operator operator, AST arg) {
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
            case ADDITIVE_INVERSE:
                return new Number((-1 * ((Number) arg.evaluate(env, mem)).value()) + "");
            default:
                throw new UnsupportedOperationException(operator.toString());
        }
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        Type t = arg.checkType(env);
        if (t.equals(Number.type())) {
            return Number.type();
        } else {
            Type[] expected = {Number.type()}, was = {t};
            throw TypeError.expected(expected, was);
        }
    }

}
