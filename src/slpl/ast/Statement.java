package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Statement extends AST {

    private AST statement;
    private boolean returns = false;

    public Statement(AST statement) {
        this.statement = statement;
        if(statement instanceof Return) {
            returns = true;
        }
    }

    public boolean returns() {
        return returns;
    }

    @Override
    public String toString() {
        return String.format("(Statement %s)", statement);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        return statement.evaluate(env, mem);
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        return statement.checkType(env);
    }
}
