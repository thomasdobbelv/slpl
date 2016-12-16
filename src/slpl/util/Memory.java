package slpl.util;

import slpl.ast.AST;

import java.util.ArrayList;

public class Memory {

    private ArrayList<AST> memory = new ArrayList<>();

    public void add(AST value) {
        memory.add(value);
    }

    public void set(int address, AST value) {
        memory.set(address, value);
    }

    public AST get(int address) {
        return memory.get(address);
    }

}
