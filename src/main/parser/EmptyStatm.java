package parser;

import main.Main;
import scanner.Scanner;

/**
 * Created by filos on 15/10/2016.
 */
public class EmptyStatm extends Statement{
    public EmptyStatm(int n) {
        super(n);
    }

    public static EmptyStatm parse(Scanner scanner) {
        enterParser("empty-statemnt");
        leaveParser("empty-statemnt");
        return new EmptyStatm(scanner.curLineNum());
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint("<empty statement>");
    }

    @Override
    public String identify() {
        return null;
    }


}
