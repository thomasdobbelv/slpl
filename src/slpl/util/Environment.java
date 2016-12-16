package slpl.util;

import slpl.ast.Variable;
import java.util.HashMap;

public class Environment {

    private HashMap<String, Variable> env = new HashMap<>();

    public void bind(String name, Variable var) {
        env.put(name, var);
    }

    public Variable lookup(String name) {
        return env.get(name);
    }

    public boolean contains(String name) {
        return env.containsKey(name);
    }

    public int size() {
        return env.size();
    }

    @Override
    public Environment clone() {
        Environment clone = new Environment();
        for(String name : env.keySet()) {
            clone.env.put(name, env.get(name));
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String name : env.keySet()) {
            sb.append(String.format("%s -> %s\n", name, env.get(name)));
        }
        return sb.toString();
    }

}
