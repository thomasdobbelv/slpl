package slpl.ast;

import slpl.util.Context;

public class Variable extends AST {

    private String name;
    private String type;
    private AST referencedValue;

    public Variable(String name, String type, AST referencedValue) {
        this.name = name;
        this.type = type;
        this.referencedValue = referencedValue;
    }

    @Override
    public AST evaluate(Context context) {
        return referencedValue.evaluate(context);
    }

    @Override
    public String toString() {
        return String.format("(Variable %s %s %s)", name, type, referencedValue);
    }

    public AST getReferencedValue() {
        return referencedValue;
    }

    public void setReferencedValue(AST referencedValue) {
        this.referencedValue = referencedValue;
    }

    public String getName() {
        return name;
    }

}
