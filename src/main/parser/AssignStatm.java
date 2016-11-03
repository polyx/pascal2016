package parser;

import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;


public class AssignStatm extends Statement {
    Variable var;
    Expression expr;

    AssignStatm(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Assign-Statement> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        var.prettyPrint();
        Main.log.prettyPrint(" := ");
        expr.prettyPrint();
        Main.log.prettyPrint("");
//        Main.log.prettyPrintLn();
//        Main.log.prettyPrintLn();
    }

    static AssignStatm parse(Scanner s) {
        enterParser("assign statm");

        AssignStatm as = new AssignStatm(s.curLineNum());

        as.var = Variable.parse(s);
        s.skip(assignToken);
        as.expr = Expression.parse(s);

        leaveParser("assign statm");
        return as;
    }
}