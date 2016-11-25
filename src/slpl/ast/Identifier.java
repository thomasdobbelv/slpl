package slpl.ast;

public class Identifier extends Ast {

    private String identifier;

    public Identifier(String identifierString) {
        this.identifier = identifierString;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Ast evaluate() {
        return this;
    }
}
