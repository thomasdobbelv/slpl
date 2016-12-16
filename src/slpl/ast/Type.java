package slpl.ast;

public class Type {

    private String name;

    public Type(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Type) {
            return name.equals(((Type) o).name);
        } else {
            return false;
        }
    }
}
