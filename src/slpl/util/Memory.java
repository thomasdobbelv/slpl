package slpl.util;

import slpl.ast.AST;

import java.util.ArrayList;

public class Memory {

    private int sp = 0, fp = 0;
    private ArrayList<AST> mem = new ArrayList<>();

    public int push(AST val) {
        ++sp;
        if(sp >= mem.size()) {
            mem.add(val);
        } else {
            mem.set(sp, val);
        }
        return sp - 1;
    }

    public void unwind(int amount) {
        sp -= amount;
    }

    public void set(int loc, AST val) {
        mem.set(loc, val);
    }

    public AST get(int loc) {
        return mem.get(loc);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < sp; ++i) {
            sb.append(String.format("Location %d: %s\n", i, mem.get(i)));
        }
        return sb.toString();
    }

}
