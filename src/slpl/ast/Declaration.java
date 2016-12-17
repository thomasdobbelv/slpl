package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Declaration extends AST {

    private String name;
    private Type type;
    private AST rvalue;

    public Declaration(String name, Type type, AST rvalue) {
        this.name = name;
        this.type = type;
        this.rvalue = rvalue;
    }

    public Declaration(String name, Type type) {
        this.name = name;
        this.type = type;
        rvalue = new Null();
    }

    @Override
    public String toString() {
        return String.format("(Declaration Name: %s, Type: %s, Value: %s)", name, type, rvalue);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        AST val = rvalue.evaluate(env, mem);
        Variable var = new Variable(name, type, mem.push(val));
        env.bind(name, var);
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        Type t = rvalue.checkType(env);
        if(env.contains(name)) {
            throw TypeError.nameOutOfScope(name);
        } else if(!t.equals(type)) {
            Type[] expected = {type}, was = {t};
            throw TypeError.expected(expected, was);
        } else {
            env.bind(name, new Variable(name, type));
            return Void.type();
        }
    }
}
