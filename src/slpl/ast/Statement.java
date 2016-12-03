package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
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
    public String typeCheck(Context context) throws TypeError {
        statement.typeCheck(context);
        return PrimitiveType.VOID.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(Statement %s)", statement);
    }
}
