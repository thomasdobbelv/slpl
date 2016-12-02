package slpl.ast;

public class Statement extends AST {

    private AST statement;

    public Statement(AST statement) {
        this.statement = statement;
    }

    @Override
    public AST evaluate() {
        statement.evaluate();
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Statement %s)", statement);
    }
}
