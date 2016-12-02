package slpl;

import slpl.ast.AST;
import slpl.syntax.ParseException;
import slpl.syntax.ProgramParser;

import java.io.*;

public class Interpreter {

    public static void main(String[] args) throws IOException, ParseException {
        String f = "samples/if-statements.slpl";
        String program = load(f);
        run(program);
    }

    private static String load(String f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(f)));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

    private static void run(String programText) throws ParseException {
        AST program = ProgramParser.parseProgram(programText);
        program.evaluate();
    }

}
