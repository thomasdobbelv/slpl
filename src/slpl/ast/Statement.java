package slpl.ast;

public class Statement extends Ast {

    private Ast statement;
    private Statement nextStatement;

    public Statement(Ast statement, Statement nextStatement) {
        this.statement = statement;
        this.nextStatement = nextStatement;
    }

    @Override
    public Ast evaluate() {
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
