package slpl.util;

import slpl.ast.AST;

import java.util.HashMap;

public class Context {

    private HashMap<String, AST> map = new HashMap<>();

    public void set(String name, AST value) {
        map.put(name, value);
    }

    public AST get(String name) {
        AST value = map.get(name);
        if(value == null) {
            throw new IllegalArgumentException(String.format("%s is not defined", name));
        }
        return value;
    }

    public boolean contains(String name) {
        return map.containsKey(name);
    }

}
