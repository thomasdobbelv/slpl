package slpl.util;

import slpl.ast.Variable;
import java.util.HashMap;

public class Environment {

    private Environment enclosing;
    private HashMap<String, Variable> env = new HashMap<>();

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    public void bind(String name, Variable var) {
        env.put(name, var);
    }

    public Variable lookup(String name) {
        if(!env.containsKey(name) && enclosing != null) {
            return enclosing.lookup(name);
        } else {
            return env.get(name);
        }
    }

    public boolean contains(String name) {
        boolean contains = env.containsKey(name);
        if(!contains && enclosing != null) {
            return enclosing.contains(name);
        } else {
            return contains;
        }
    }

    public int size() {
        return env.size();
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
