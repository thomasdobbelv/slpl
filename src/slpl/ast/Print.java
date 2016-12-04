package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class Print extends AST {

    private AST arg;
    private boolean nl;

    public Print(AST arg, boolean nl) {
        this.arg = arg;
        this.nl = nl;
    }

    @Override
    public AST evaluate(Context context) {
        AST out = arg.evaluate(context);
        if (out instanceof Str) {
            System.out.print(((Str) out).getValue());
        } else if (out instanceof Boolean) {
            System.out.print(((Boolean) out).getValue());
        } else if (out instanceof Number) {
            System.out.print(((Number) out).getValue());
        } else if (out instanceof Null) {
            System.out.print("null");
        } else {
            throw new IllegalArgumentException(out + " is not printable");
        }
        if(nl) {
            System.out.println();
        }
        return this;
    }

    @Override
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        String argType = arg.typeCheck(typeCheckerContext);
        for(PrimitiveType pt : PrimitiveType.values()) {
            if(pt == PrimitiveType.VOID) {
                continue;
            } else if(argType.equals(pt.getTypeName())) {
                return PrimitiveType.VOID.getTypeName();
            }
        }
        throw new TypeCheckException(String.format("%s is not a printable type", argType));
    }

    @Override
    public String toString() {
        return String.format("(Print %s)", arg);
    }
}
