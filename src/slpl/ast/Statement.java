package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Statement extends AST {

    private AST statement;

    public Statement(AST statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return String.format("(Statement %s)", statement);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        statement.evaluate(env, mem);
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        statement.checkType(env);
        return Void.type();
    }
}
