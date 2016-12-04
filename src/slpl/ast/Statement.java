package slpl.ast;

import slpl.util.Context;

public class Statement extends AST {

    private AST statement;

    public Statement(AST statement) {
        this.statement = statement;
    }

    @Override
    public AST evaluate(Context context) {
        statement.evaluate(context);
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Statement %s)", statement);
    }
}
