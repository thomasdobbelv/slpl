package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class Declaration extends AST {

    private String name;
    private String type;
    private AST rvalue;

    public Declaration(String name, String type, AST rvalue) {
        this.name = name;
        this.type = type;
        this.rvalue = rvalue;
    }

    public Declaration(String name, String type) {
        this.name = name;
        this.type = type;
        rvalue = new Null();
    }

    public String getType() {
        return type;
    }

    @Override
    public AST evaluate(Context context) {
        context.add(new Variable(name, type, rvalue.evaluate(context)));
        return this;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        if(type.equals(PrimitiveType.NULL.getTypeName())) {
            throw TypeCheckException.illegalVariableType(name, type);
        }
        typeCheckerContext.setType(name, type);
        String rvalueType = rvalue.typeCheck(typeCheckerContext);
        if(rvalueType.equals(PrimitiveType.NULL.getTypeName()) || type.equals(rvalueType)) {
            return type;
        }
        throw TypeCheckException.rvalueTypeAssigneeTypeMismatch(rvalueType, type, name);
    }

    @Override
    public String toString() {
        return String.format("(Declaration Name: %s, Type: %s, Value: %s)", name, type, rvalue);
    }
}
