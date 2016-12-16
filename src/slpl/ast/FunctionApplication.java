package slpl.ast;

import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.StringConcatenator;
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
        System.out.println(this);
        for(int i = 0; i < args.length; ++i) {
            args[i] = args[i].evaluate(context);
        }
        LambdaFunction f = (LambdaFunction) context.get(name).getValue();
        Declaration[] params = f.params();
        for(int i = 0; i < params.length; ++i) {
            context.set(params[i].name(), args[i]);
        }
        return f.body().evaluate(context);
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        return null;
    }

    @Override
    public String toString() {
        return String.format("(FunctionApplication Name: %s, Arguments: [%s])", name, StringConcatenator.concatenate(", ", args));
    }
}
