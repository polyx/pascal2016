package parser;

import main.Main;
import scanner.Scanner;


public class EmptyStatm extends Statement{
    public EmptyStatm(int n) {
        super(n);
    }

    @Override
    public void check(Block curScope, Library lib) {

    }

    public static EmptyStatm parse(Scanner scanner) {
        enterParser("empty statm");
        leaveParser("empty statm");
        return new EmptyStatm(scanner.curLineNum());
    }

    @Override
    public void prettyPrint() {
        /*Main.log.prettyPrint("<empty statement>");*/
    }

    @Override
    public String identify() {
        return null;
    }


}
