package slpl.ast;

import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

import java.util.List;

public class LambdaFunction extends AST {

    private List<Declaration> parameters;
    private String type, returnType;
    private Block body;

    public LambdaFunction(List<Declaration> parameters, String returnType, Block body) {
        this.parameters = parameters;
        this.returnType = returnType;
        this.type = deriveType();
        this.body = body;
    }

    private String deriveType() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if(!parameters.isEmpty()) {
            for(Declaration parameter : parameters) {
                sb.append(parameter.getType() + ",");
            }
            sb.setLength(sb.length() - 1);
        }
        sb.append(")->" + returnType);
        return sb.toString();
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
        return String.format("(LambdaFunction %s %s)", type, body);
    }
}
