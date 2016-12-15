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
        return null;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        String[] argTypes = new String[args.length];
        for(int i = 0; i < argTypes.length; ++i) {
            argTypes[i] = args[i].typeCheck(typeCheckerContext);
        }
        // TODO: find function in context, check that the paramTypes of function match argTypes. if not throw type error
        // else return type of the function (return type) (function definition has been typechecked and we should assume return type is correct)
        return null;
    }

    @Override
    public String toString() {
        return String.format("(FunctionApplication Name: %s, Arguments: [%s])", name, StringConcatenator.concatenate(", ", args));
    }
}
