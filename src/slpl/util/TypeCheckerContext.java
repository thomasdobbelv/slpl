package slpl.util;

import java.util.HashMap;

public class TypeCheckerContext {

    private HashMap<String, String> map = new HashMap<>();

    public boolean contains(String name) {
        return map.containsKey(name);
    }

    public void setType(String name, String type) {
        map.put(name, type);
    }

    public String getType(String name) {
        return map.get(name);
    }

}
