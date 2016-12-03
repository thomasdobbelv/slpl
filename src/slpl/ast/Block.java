package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeError;
import slpl.util.Context;

import java.util.List;

public class Block extends AST {

    private List<AST> blockStatements;

    public Block(List<AST> blockStatements) {
        this.blockStatements = blockStatements;
    }

    @Override
    public AST evaluate(Context context) {
        for(AST blockStatement : blockStatements) {
            blockStatement.evaluate(context);
        }
        return this;
    }

    @Override
    public String typeCheck(Context context) throws TypeError {
        for(AST blockStatement : blockStatements) {
            blockStatement.typeCheck(context);
        }
        return PrimitiveType.VOID.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(Block %s)", blockStatements);
    }
}
