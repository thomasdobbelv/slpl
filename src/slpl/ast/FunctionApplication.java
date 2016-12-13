package slpl.ast;

import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class FunctionApplication extends AST {

    private String name;
    private AST[] arguments;

    public FunctionApplication(String name, AST[] arguments) {
        this.name = name;
        this.arguments = arguments;
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
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < arguments.length; ++i) {
            sb.append(arguments[i] + ", ");
        }
        if(sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return String.format("(FunctionApplication Name: %s, Arguments: [%s])", name, sb.toString());
    }
}
