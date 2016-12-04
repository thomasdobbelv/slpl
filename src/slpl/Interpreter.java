package slpl;

import slpl.ast.AST;
import slpl.err.ParseException;
import slpl.err.TypeCheckException;
import slpl.syntax.ProgramParser;
import slpl.util.Context;
import slpl.util.TypeCheckerContext;

import java.io.*;

public class Interpreter {

    public static void main(String[] args) throws IOException, ParseException, TypeCheckException {
        String path = "samples/type-errors.slpl";
        String program = load(path);
        run(program);
    }

    private static String load(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

    private static void run(String programText) throws ParseException, TypeCheckException {
        AST program = ProgramParser.parseProgram(programText);
        program.typeCheck(new TypeCheckerContext());
        program.evaluate(new Context());
    }

}
