package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;

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
    public String typeCheck(Context context) throws TypeError {
        body.typeCheck(context);
        return PrimitiveType.VOID.getTypeName(); // FIXME ?
    }

    @Override
    public String toString() {
        return String.format("(Module %s %s)", name, body);
    }
}
