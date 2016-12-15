package slpl;

import slpl.ast.AST;
import slpl.err.ParseException;
import slpl.err.TypeCheckException;
import slpl.syntax.ProgramParser;

import java.io.*;

public class Interpreter {

    public static void main(String[] args) throws IOException, ParseException, TypeCheckException {
        String path = "samples/apply.slpl";
        String programText = load(path);
        AST program = ProgramParser.parseProgram(programText);
        System.out.println(program);
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

}
