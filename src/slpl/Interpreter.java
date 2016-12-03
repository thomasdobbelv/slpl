package slpl;

import slpl.ast.AST;
import slpl.syntax.ParseException;
import slpl.syntax.ProgramParser;
import slpl.util.Context;

import java.io.*;

public class Interpreter {

    public static void main(String[] args) throws IOException, ParseException {
        String path = "samples/prog.slpl";
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

    private static void run(String programText) throws ParseException {
        AST program = ProgramParser.parseProgram(programText);
        Context context = new Context();
        program.evaluate(context);
        System.out.println(context);
    }

}
