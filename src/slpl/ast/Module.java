package slpl.ast;

public class Module extends AST {

    private String name;
    private AST body;

    public Module(String name, AST body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public AST evaluate() {
        return body.evaluate();
    }

    @Override
    public String toString() {
        return String.format("(Module %s %s)", name, body);
    }
}
