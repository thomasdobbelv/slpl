package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

import java.util.List;

public class Block extends AST {

    private List<Statement> statements;

    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return String.format("(Block Statements: %s)", statements);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        for(Statement s : statements) {
            if(s.returns()) {
                return s.evaluate(env, mem);
            } else {
                s.evaluate(env, mem);
            }
        }
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        Type ret = Void.type();
        for(Statement s : statements) {
            if(s.returns()) {
                ret = s.checkType(env);
            } else {
                s.checkType(env);
            }
        }
        return ret;
    }
}
