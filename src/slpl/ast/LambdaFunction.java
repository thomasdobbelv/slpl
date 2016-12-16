package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;
import slpl.util.StringConcatenator;

public class LambdaFunction extends AST {

    private Declaration[] params;
    private Type type, returnType;
    private Block body;

    public LambdaFunction(Declaration[] params, Type returnType, Block body) {
        this.params = params;
        this.returnType = returnType;
        this.body = body;
        // TODO: set checkType
    }

    @Override
    public String toString() {
        return String.format("(LambdaFunction Type: %s, Parameters: %s, Body: %s)", type, StringConcatenator.concatenate(", ", params), body);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        return new Type("NYI for LamdaFunction");
    }

}
