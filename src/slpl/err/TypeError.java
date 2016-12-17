package slpl.err;

import slpl.ast.Type;
import slpl.util.StringConcatenator;

public class TypeError extends Exception {

    public TypeError(String err) {
        super(err);
    }

    public static TypeError expected(Type[] expected, Type[] was) {
        return new TypeError(String.format("expected %s, but was %s", StringConcatenator.concatenate(", ", expected), StringConcatenator.concatenate(", ", was)));
    }

    public static TypeError nameOutOfScope(String name) {
        return new TypeError(String.format("the name %s is not in scope", name));
    }
}
