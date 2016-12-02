package slpl.syntax;

import slpl.syntax.lexical.TokenType;
import slpl.ast.AST;
import slpl.ast.Block;
import slpl.util.TokenStream;

import java.util.LinkedList;
import java.util.List;

public class BlockParser {

    public static AST parseBlock(TokenStream ts) throws ParseException {
        ts.expect(TokenType.LCRL);
        ts.consume();
        List<AST> blockStatements = new LinkedList<>();
        while(!ts.hasNext(TokenType.RCRL)) {
            blockStatements.add(StatementParser.parseStatement(ts));
        }
        ts.consume();
        return new Block(blockStatements);
    }


}
