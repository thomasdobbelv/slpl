package slpl.ast;

import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.StringConcatenator;
import slpl.util.TypeCheckerContext;

import java.util.LinkedList;
import java.util.List;

public class LambdaFunction extends AST {

    private List<Declaration> params;
    private String type, returnType;
    private Block body;

    public LambdaFunction(List<Declaration> params, String returnType, Block body) {
        this.params = params;
        this.returnType = returnType;
        this.type = deriveType();
        this.body = body;
    }

    private String deriveType() {
        LinkedList<String> paramTypes = new LinkedList<>();
        for(Declaration param : params) {
            paramTypes.add(param.getType());
        }
        return String.format("(%s) -> %s", StringConcatenator.concatenate(", ", paramTypes.toArray()), returnType);
    }

    @Override
    public AST evaluate(Context context) {
        return this;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        // FIXME: NYI
        System.err.println("WARN: LambdaFunction typeCheck not yet implemented");
        return type;
    }

    @Override
    public String toString() {
        return String.format("(LambdaFunction Type: %s, Parameters: %s, Body: %s)", type, StringConcatenator.concatenate(", ", params.toArray()), body);
    }
}
