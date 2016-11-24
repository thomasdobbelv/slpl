public enum TokenType {

    NUMBER("-?[0-9]+"), BINARYOPERATOR("[+|-|*|/]"), WHITESPACE("[ \t\f\r\n]+");

    public final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

}
