package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.Operator;

public class AssignmentOperation extends AST {

    protected String assignee;
    protected Operator operator;
    private AST rvalue;

    public AssignmentOperation(String assignee, Operator operator, AST rvalue) {
        this.assignee = assignee;
        this.operator = operator;
        this.rvalue = rvalue;
    }

    @Override
    public String toString() {
        return String.format("(%s Value: %s, Assignee: %s)", operator, rvalue, assignee);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        AST val = rvalue.evaluate(env, mem);
        Variable var = env.lookup(assignee);
        if (operator != Operator.ASSIGN) {
            double a = ((Number) var.evaluate(env, mem)).value(), b = ((Number) val).value();
            switch (operator) {
                case ADDEQ:
                    a += b;
                    break;
                case SUBEQ:
                    a -= b;
                    break;
                case MULEQ:
                    a *= b;
                    break;
                case DIVEQ:
                    a /= b;
                    break;
                default:
                    throw new UnsupportedOperationException(operator.toString());
            }
            val = new Number(a + "");
        }
        mem.set(var.location(), val);
        return val;
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        if (!env.contains(assignee)) {
            throw new TypeError(String.format("the name %s is not defined", assignee));
        } else {
            Type t1 = env.lookup(assignee).checkType(env), t2 = rvalue.checkType(env);
            if (!t1.equals(t2)) {
                Type[] expected = {t1}, was = {t2};
                throw TypeError.expected(expected, was);
            } else {
                return t1;
            }
        }
    }

}
