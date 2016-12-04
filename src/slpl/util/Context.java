package slpl.util;

import slpl.ast.AST;
import slpl.ast.Variable;

import java.util.HashMap;

public class Context {

    private HashMap<String, Variable> map = new HashMap<>();

    public void add(Variable v) {
        map.put(v.getName(), v);
    }

    public void set(String name, AST value) {
        Variable v = map.get(name);
        if(v == null) {
            throw new IllegalArgumentException(String.format("%s is not defined", name));
        }
        v.setValue(value);
    }

    public Variable get(String name) {
        Variable v = map.get(name);
        if(v == null) {
            throw new IllegalArgumentException(String.format("%s is not defined", name));
        }
        return map.get(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Context:\n");
        for(String id : map.keySet()) {
            sb.append(id + " -> " + map.get(id) + "\n");
        }
        return sb.toString();
    }

}
