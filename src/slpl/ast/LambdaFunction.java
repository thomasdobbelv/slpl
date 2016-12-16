package slpl.ast;

import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.StringConcatenator;
import slpl.util.TypeCheckerContext;

import java.util.LinkedList;
import java.util.List;

public class LambdaFunction extends AST {

    private Declaration[] params;
    private String type, returnType;
    private Block body;

    public LambdaFunction(Declaration[] params, String returnType, Block body) {
        this.params = params;
        this.returnType = returnType;
        this.type = extractType();
        this.body = body;
    }

    private String extractType() {
        LinkedList<String> paramTypes = new LinkedList<>();
        for(Declaration param : params) {
            paramTypes.add(param.type());
        }
        return String.format("(%s) -> %s", StringConcatenator.concatenate(", ", paramTypes.toArray()), returnType);
    }

    public Declaration[] params() {
        return params;
    }

    public Block body() {
        return body;
    }

    @Override
    public AST evaluate(Context context) {
        for(Declaration param : params) {
            context.add(new Variable(param.name(), param.type(), new Null()));
        }
        return this;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        // FIXME: implement
        System.err.println("WARN: LambdaFunction typechecker NYI");
        return type;
    }

    @Override
    public String toString() {
        return String.format("(LambdaFunction Type: %s, Parameters: %s, Body: %s)", type, StringConcatenator.concatenate(", ", params), body);
    }
}
