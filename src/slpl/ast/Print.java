package slpl.ast;

import slpl.err.TypeError;
import slpl.util.Environment;
import slpl.util.Memory;

public class Print extends AST {

    private AST arg;
    private boolean nl;

    public Print(AST arg, boolean nl) {
        this.arg = arg;
        this.nl = nl;
    }

    @Override
    public String toString() {
        return String.format("(Print%s %s)", nl ? "ln" : "", arg);
    }

    @Override
    public AST evaluate(Environment env, Memory mem) {
        AST out = arg.evaluate(env, mem);
        if(out instanceof Str) {
            System.out.print(((Str) out).value());
        } else if(out instanceof Number) {
            double val = ((Number) out).value();
            if(val == (int) val) {
                System.out.print((int) val);
            } else {
                System.out.print(val);
            }
        } else if(out instanceof Boolean) {
            System.out.print(((Boolean) out).value());
        } else if(out instanceof Null) {
            System.out.print(Null.type());
        } else {
            throw new UnsupportedOperationException();
        }
        if(nl) {
            System.out.println();
        }
        return new Void();
    }

    @Override
    public Type checkType(Environment env) throws TypeError {
        Type t = arg.checkType(env);
        if(t.equals(Str.type()) || t.equals(Number.type()) || t.equals(Boolean.type()) || t.equals(Null.type())) {
            return Void.type();
        } else {
            throw new TypeError(String.format("%s is not a printable checkType", t));
        }
    }
}
