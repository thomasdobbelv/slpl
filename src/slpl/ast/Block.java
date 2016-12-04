package slpl.ast;

import slpl.PrimitiveType;
import slpl.err.TypeCheckException;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

import java.util.List;

public class Block extends AST {

    private List<Statement> blockStatements;

    public Block(List<Statement> blockStatements) {
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
    public String typeCheck(TypeCheckerContext typeCheckerContext) throws TypeCheckException {
        for(Statement blockStatement : blockStatements) {
            blockStatement.typeCheck(typeCheckerContext);
        }
        return PrimitiveType.VOID.getTypeName();
    }

    @Override
    public String toString() {
        return String.format("(Block %s)", blockStatements);
    }
}
