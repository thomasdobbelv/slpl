package slpl.ast;

public class RelationalOperation extends AST {

    private String operator;
    private AST arg1, arg2;

    public RelationalOperation(String operator, AST arg1, AST arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public AST evaluate() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("(RelationalOperation %s %s %s)", operator, arg1, arg2);
    }
}
