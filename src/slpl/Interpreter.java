package slpl;

import slpl.ast.AST;
import slpl.err.ParseException;
import slpl.err.TypeError;
import slpl.syntax.ProgramParser;
import slpl.util.Context;

import java.io.*;

public class Interpreter {

    public static void main(String[] args) throws IOException, ParseException, TypeError {
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

    private static void run(String programText) throws ParseException, TypeError {
        AST program = ProgramParser.parseProgram(programText);
        program.typeCheck(new Context());
        program.evaluate(new Context());
    }

}
