package slpl.ast;

public class Print extends AST {

    private AST arg;

    public Print(AST arg) {
        this.arg = arg;
    }

    @Override
    public AST evaluate() {
        AST a = arg.evaluate();
        if(a instanceof Str) {
            System.out.println(((Str) a).getValue());
        } else if(a instanceof Boolean) {
            System.out.println(((Boolean) a).getValue());
        } else if(a instanceof Number) {
            System.out.println(((Number) a).getValue());
        } else {
            throw new IllegalArgumentException(a + " is not printable");
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Print %s)", arg);
    }
}
