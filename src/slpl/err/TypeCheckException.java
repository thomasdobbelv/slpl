package slpl.err;

import slpl.util.Operator;

public class TypeCheckException extends Exception {

    public TypeCheckException(String err) {
        super(err);
    }

    public static TypeCheckException rvalueTypeAssigneeTypeMismatch(String rvalueType, String assigneeType, String assigneeName) throws TypeCheckException {
        throw new TypeCheckException(String.format("Cannot assign a value of type %s to %s: %s", rvalueType, assigneeName, assigneeType));
    }

    public static TypeCheckException variableTypeStoredTypeMismatch(String variableType, String storedType, String variableName) throws TypeCheckException {
        throw new TypeCheckException(String.format("Cannot store a value of type %s in %s: %s", storedType, variableName, variableType));
    }

    public static TypeCheckException undefinedOperation(Operator operator, String ... argumentTypes) throws TypeCheckException {
        throw new TypeCheckException(String.format("The operation %s is not defined for argument(s) of type %s", operator, toCSVString(argumentTypes)));
    }

    public static TypeCheckException undefinedName(String name) throws TypeCheckException {
        throw new TypeCheckException(String.format("The name %s is not defined", name));
    }

    public static TypeCheckException illegalVariableType(String name, String type) throws TypeCheckException {
        throw new TypeCheckException(String.format("%s cannot have type %s", name, type));
    }

    private static String toCSVString(Object[] objects) {
        StringBuilder sb = new StringBuilder();
        for(Object object : objects) {
            sb.append(object + ", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

}
