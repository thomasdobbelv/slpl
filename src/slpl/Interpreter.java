package slpl;

import slpl.ast.AST;
import slpl.ast.Boolean;
import slpl.ast.Number;
import slpl.ast.Str;
import slpl.syntax.ParseException;
import slpl.syntax.ProgramParser;
import slpl.util.Context;

import java.io.*;

public class Interpreter {

    public static void main(String[] args) throws IOException, ParseException {
        String f = "samples/declarations.slpl";
        String program = load(f);
        run(program, getPredefinedContext());
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

    private static void run(String programText, Context context) throws ParseException {
        AST program = ProgramParser.parseProgram(programText);
        program.evaluate(context);
        System.out.println(context); // TODO: rm statement
    }

    // TODO: remove at some point
    private static Context getPredefinedContext() {
        Context c = new Context();
        return c;
    }

}
