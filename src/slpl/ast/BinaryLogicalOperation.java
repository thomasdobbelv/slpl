package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.Operator;
import slpl.util.TypeCheckerContext;

public class BinaryLogicalOperation extends AST {

    private Operator operator;
    private AST arg1, arg2;

    public BinaryLogicalOperation(Operator operator, AST arg1, AST arg2) {
        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public AST evaluate(Context context) {
        Boolean b1 = (Boolean) arg1.evaluate(context), b2 = (Boolean) arg2.evaluate(context);
        switch (operator) {
            case OR:
                return new Boolean(b1.getValue() || b2.getValue());
            case AND:
                return new Boolean(b1.getValue() && b2.getValue());
        }
        throw new UnsupportedOperationException(operator.toString());
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        String arg1Type = arg1.typeCheck(typeCheckerContext), arg2Type = arg2.typeCheck(typeCheckerContext);
        String booleanType = PrimitiveType.BOOLEAN.getTypeName();
        if(arg1Type.equals(booleanType) && arg2Type.equals(booleanType)) {
            return booleanType;
        }
        throw TypeCheckException.undefinedOperation(operator, arg1Type, arg2Type);
    }

    @Override
    public String toString() {
        return String.format("(%s %s, %s)", operator, arg1, arg2);
    }
}
