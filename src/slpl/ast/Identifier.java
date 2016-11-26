package slpl.ast;

public class Identifier extends Ast {

    private String value;

    public Identifier(String identifier) {
        this.value = identifier;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Ast evaluate() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Identifier %s)", value);
    }
}
