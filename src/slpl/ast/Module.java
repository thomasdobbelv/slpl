package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class Module extends AST {

    private String name;
    private AST body;

    public Module(String name, AST body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public AST evaluate(Context context) {
        // TODO: consider what it means to evaluate a module. does it mean building a context (set of predef. functions, variables)?
        body.evaluate(context);
        return this;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        body.typeCheck(typeCheckerContext);
        return PrimitiveType.VOID.typeName();
    }

    @Override
    public String toString() {
        return String.format("(Module Name: %s, Body: %s)", name, body);
    }
}
