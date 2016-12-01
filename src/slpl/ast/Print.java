package slpl.ast;

public class Print extends AST {

    private String toPrint;

    public Print(String toPrint) {
        this.toPrint = toPrint;
    }

    @Override
    public AST evaluate() {
        System.out.println(toPrint);
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Print %s)", toPrint);
    }
}
