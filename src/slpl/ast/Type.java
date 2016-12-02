package slpl.ast;

import slpl.syntax.lexical.Token;
import slpl.util.Context;

import java.util.HashMap;

// TODO: find other solution to typing
public class Type extends AST {

    public static final HashMap<String, Type> types = new HashMap<>();
    static {
        types.put("int", new Type("int"));
        types.put("boolean", new Type("boolean"));
        types.put("string", new Type("string"));
    }

    private final String name;
    private Type superType; // NYI

    public Type(String name) {
        this.name = name;
    }

    @Override
    public AST evaluate(Context context) {
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Type %s)", name);
    }

    public static Type fromToken(Token t) {
        Type type = types.get(t.getContent());
        if(type == null) {
            throw new IllegalArgumentException(String.format("The type %s is not defined", type));
        }
        return type;
    }
}
