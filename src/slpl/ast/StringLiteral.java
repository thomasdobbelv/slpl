package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

public class StringLiteral extends AST {

    private final String value;

    public StringLiteral(String value) {
        this.value = value.substring(1, value.length() - 1);
    }

    @Override
    public AST evaluate(Context _) {
        return this;
    }

    @Override
    public String typeCheck(TypeCheckerContext _) throws TypeCheckException {
        return PrimitiveType.STRING.typeName();
    }


    @Override
    public String toString() {
        return String.format("\"%s\")", value);
    }

    public String getValue() {
        return value;
    }
}
