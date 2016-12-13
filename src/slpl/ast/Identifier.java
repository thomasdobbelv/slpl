package slpl.ast;

import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class Identifier extends AST {

    private String id;

    public Identifier(String id) {
        this.id = id;
    }

    @Override
    public AST evaluate(Context context) {
        return context.get(id).evaluate(context);
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        if(typeCheckerContext.contains(id)) {
            return typeCheckerContext.getType(id);
        }
        throw TypeCheckException.undefinedName(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
