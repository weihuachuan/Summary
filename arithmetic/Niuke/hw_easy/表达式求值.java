package Niuke.hw_easy;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Scanner;

public class 表达式求值 {
    public static void main(String [] args) throws ScriptException {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        input = input.replace("[", "(");
        input = input.replace("{", "(");
        input = input.replace("}", ")");
        input = input.replace("]", ")");
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        System.out.println(scriptEngine.eval(input));
    }
}
