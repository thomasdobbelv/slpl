package slpl.ast;

import slpl.util.Context;

public class Print extends AST {

    private AST arg;

    public Print(AST arg) {
        this.arg = arg;
    }

    @Override
    public AST evaluate(Context context) {
        AST out = arg.evaluate(context);
        if(out instanceof Str) {
            System.out.println(((Str) out).getValue());
        } else if(out instanceof Boolean) {
            System.out.println(((Boolean) out).getValue());
        } else if(out instanceof Number) {
            System.out.println(((Number) out).getValue());
        } else {
            throw new IllegalArgumentException(out + " is not printable");
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Print %s)", arg);
    }
}
