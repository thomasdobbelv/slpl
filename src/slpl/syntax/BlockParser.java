package slpl.syntax;

import slpl.ast.Statement;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.ast.AST;
import slpl.ast.Block;
import slpl.util.TokenStream;

import java.util.LinkedList;
import java.util.List;

public class BlockParser {

    public static Block parseBlock(TokenStream ts) throws ParseException {
        ts.expect(TokenType.LCRL);
        ts.consume();
        List<Statement> blockStatements = new LinkedList<>();
        while(!ts.hasNext(TokenType.RCRL)) {
            blockStatements.add(StatementParser.parseStatement(ts));
        }
        ts.consume();
        return new Block(blockStatements);
    }


}
