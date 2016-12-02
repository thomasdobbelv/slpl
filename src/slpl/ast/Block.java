package slpl.ast;

import java.util.List;

public class Block extends AST {

    private List<AST> blockStatements;

    public Block(List<AST> blockStatements) {
        this.blockStatements = blockStatements;
    }

    @Override
    public AST evaluate() {
        for(AST blockStatement : blockStatements) {
            blockStatement.evaluate();
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("(Block %s)", blockStatements);
    }
}
