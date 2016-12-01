package slpl.ast;

public class Boolean extends AST {

    private boolean value;

    public Boolean(boolean value) {
        this.value = value;
    }

    @Override
    public AST evaluate() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Boolean %s)", value);
    }

    public boolean getValue() {
        return value;
    }
}
