package slpl.ast;

public class UnaryBooleanOperation extends AST {

    private String operator;
    private AST arg;

    public UnaryBooleanOperation(String operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public AST evaluate() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("(UnaryBooleanOperation %s %s)", operator, arg);
    }
}
