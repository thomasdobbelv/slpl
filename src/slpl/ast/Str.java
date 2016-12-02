package slpl.ast;

public class Str extends AST {

    private final String value;

    public Str(String value) {
        this.value = value.substring(1, value.length() - 1);
    }

    @Override
    public AST evaluate() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Str %s)", value);
    }

    public String getValue() {
        return value;
    }
}
