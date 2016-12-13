package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class Variable extends AST {

    private String name;
    private String type;
    private AST rvalue;

    public Variable(String name, String type, AST rvalue) {
        this.name = name;
        this.type = type;
        this.rvalue = rvalue;
    }

    @Override
    public AST evaluate(Context context) {
        return rvalue.evaluate(context);
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        String rvalueType = rvalue.typeCheck(typeCheckerContext);
        if(rvalueType.equals(PrimitiveType.NULL.getTypeName()) || rvalueType.equals(type)) {
            return type;
        }
        throw TypeCheckException.variableTypeStoredTypeMismatch(type, rvalueType, name);
    }

    @Override
    public String toString() {
        return String.format("(Variable Name: %s, Type: %s, Value: %s)", name, type, rvalue);
    }

    public AST getValue() {
        return rvalue;
    }

    public void setValue(AST rvalue) {
        this.rvalue = rvalue;
    }

    public String getName() {
        return name;
    }

}
