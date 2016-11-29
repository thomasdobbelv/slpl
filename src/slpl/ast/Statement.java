package slpl.ast;

public class Statement extends AST {

    private AST statement;
    private Statement nextStatement;

    public Statement(AST statement, Statement nextStatement) {
        this.statement = statement;
        this.nextStatement = nextStatement;
    }

    @Override
    public AST evaluate() {
        // FIXME: NYI
        if(nextStatement == null) {
            return statement.evaluate();
        }
        statement.evaluate();
        return nextStatement.evaluate();
    }

    @Override
    public String toString() {
        return String.format("(Statement %s %s)", statement, nextStatement);
    }
}
