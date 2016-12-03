package slpl.err;

public class TypeError extends Exception {

    public TypeError(String err) {
        super(err);
    }

    // TODO: get information regarding location of typeCheck error

    public static TypeError expected(String expected, String type) throws TypeError {
        throw new TypeError(String.format("Expected type %s but was %s", expected, type));
    }
}
