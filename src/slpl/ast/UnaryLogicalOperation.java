package slpl.ast;

public class UnaryLogicalOperation extends AST {

    private String operator;
    private AST arg;

    public UnaryLogicalOperation(String operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public AST evaluate() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("(UnaryLogicalOperation %s %s)", operator, arg);
    }
}
