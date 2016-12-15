package slpl.ast;

import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class FunctionApplication extends AST {

    private String name;
    private AST[] args;

    public FunctionApplication(String name, AST[] args) {
        this.name = name;
        this.args = args;
    }

    public AST[] args() {
        return args;
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
        for(int i = 0; i < args.length; ++i) {
            sb.append(args[i] + ", ");
        }
        if(sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return String.format("(FunctionApplication Name: %s, Arguments: [%s])", name, sb.toString());
    }
}
