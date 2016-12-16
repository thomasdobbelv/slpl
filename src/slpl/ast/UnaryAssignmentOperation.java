package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.Operator;

public class UnaryAssignmentOperation extends AssignmentOperation {

    private boolean isPrefixOperation;

    public UnaryAssignmentOperation(String name, Operator operator, boolean isPrefixOperation) {
        super(name, operator, null);
        this.isPrefixOperation = isPrefixOperation;
    }

    @Override
    public String toString() {
        if (isPrefixOperation) {
            return String.format("(%s %s)", operator, assignee);
        } else {
            return String.format("(%s %s)", assignee, operator);
        }
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        Variable var = env.lookup(assignee);
        Number before = ((Number) var.evaluate(env, mem));
        double val = before.value();
        switch (operator) {
            case INCR:
                ++val;
                break;
            case DECR:
                --val;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        Number after = new Number(val + "");
        mem.set(var.location(), after);
        if(isPrefixOperation) {
            return after;
        } else {
            return before;
        }
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        if (!env.contains(assignee)) {
            throw new TypeError(String.format("the name %s is not defined", assignee));
        } else {
            Type t = env.lookup(assignee).checkType(env);
            if (!t.equals(Number.type())) {
                Type[] expected = {Number.type()}, was = {t};
                throw TypeError.expected(expected, was);
            } else {
                return t;
            }
        }
    }

}
