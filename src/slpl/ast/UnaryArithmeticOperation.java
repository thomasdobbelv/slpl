package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.Operator;
import slpl.util.TypeCheckerContext;

public class UnaryArithmeticOperation extends AST {

    private Operator operator;
    private AST arg;

    public UnaryArithmeticOperation(Operator operator, AST arg) {
        this.operator = operator;
        this.arg = arg;
    }

    @Override
    public AST evaluate(Context context) {
        Number num = (Number) arg.evaluate(context);
        switch (operator) {
            case ADDITIVE_INVERSE:
                return new Number((-num.getValue()) + "");
        }
        throw new UnsupportedOperationException(operator.toString());
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        String argType = arg.typeCheck(typeCheckerContext);
        String numberType = PrimitiveType.NUMBER.getTypeName();
        if(argType.equals(numberType)) {
            return numberType;
        }
        throw TypeCheckException.undefinedOperation(operator, argType);
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", operator, arg);
    }

}
