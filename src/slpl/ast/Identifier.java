package slpl.ast;

public class Identifier extends AST {

    private String value;

    public Identifier(String identifier) {
        this.value = identifier;
    }

    public String getValue() {
        return value;
    }

    @Override
    public AST evaluate() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Identifier %s)", value);
    }
}
