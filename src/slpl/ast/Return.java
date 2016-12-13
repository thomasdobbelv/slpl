package slpl.ast;

import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class Return extends AST {

    private AST rvalue;

    public Return(AST rvalue) {
        this.rvalue = rvalue;
    }

    @Override
    public AST evaluate(Context context) {
        return null;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        return null;
    }

    @Override
    public String toString() {
        return String.format("(Return Value: %s)", rvalue);
    }
}
