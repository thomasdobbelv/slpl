package slpl.util;

import slpl.ast.AST;
import slpl.ast.Variable;

import java.util.HashMap;

public class Context {

    private HashMap<String, Variable> map = new HashMap<>();

    public void add(Variable variable) {
        map.put(variable.getName(), variable);
    }

    public void set(String name, AST value) {
        map.get(name).setReferencedValue(value);
    }

    public Variable get(String name) {
        return map.get(name);
    }
}
