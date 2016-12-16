package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

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
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        statement.typeCheck(typeCheckerContext);
        return PrimitiveType.VOID.typeName();
    }

    @Override
    public String toString() {
        return String.format("(Statement %s)", statement);
    }
}
