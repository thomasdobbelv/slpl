package slpl.err;

import slpl.util.Operator;
import slpl.util.StringConcatenator;

public class TypeCheckException extends Exception {

    public TypeCheckException(String err) {
        super(err);
    }

    public static TypeCheckException rvalueTypeAssigneeTypeMismatch(String rvalueType, String assigneeType, String assigneeName) {
        return new TypeCheckException(String.format("Cannot assign a value of type %s to %s: %s", rvalueType, assigneeName, assigneeType));
    }

    public static TypeCheckException variableTypeStoredTypeMismatch(String variableType, String storedType, String variableName) {
        return new TypeCheckException(String.format("Cannot store a value of type %s in %s: %s", storedType, variableName, variableType));
    }

    public static TypeCheckException undefinedOperation(Operator operator, String ... argumentTypes) {
        return new TypeCheckException(String.format("The operation %s is not defined for argument(s) of type %s", operator, StringConcatenator.concatenate(", ", argumentTypes)));
    }

    public static TypeCheckException undefinedName(String name) {
        return new TypeCheckException(String.format("The name %s is not defined", name));
    }

    public static TypeCheckException illegalVariableType(String name, String type) {
        return new TypeCheckException(String.format("%s cannot have type %s", name, type));
    }

    public static TypeCheckException argumentTypeParameterTypeMismatch() {
        return new TypeCheckException("A type error occurred somewhere. Have fun debugging.");
    }
}
