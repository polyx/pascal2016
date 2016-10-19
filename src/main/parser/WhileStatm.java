package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

class WhileStatm extends Statement {
    Expression expr;
    Statement body;

    WhileStatm(int lNum) {
        super(lNum);
    }

    public void prettyPrint(){
        Main.log.prettyPrint("while ");
        expr.prettyPrint();
        Main.log.prettyPrint(" do ");
        body.prettyPrint();
        Main.log.prettyPrintLn("");

    }

    @Override
    public String identify() {
        return "<while-statm> on line " + lineNum;
    }

    static WhileStatm parse(Scanner scanner) {
        enterParser("while-statm");

        WhileStatm whileStatm = new WhileStatm(scanner.curLineNum());

        scanner.skip(whileToken);
        whileStatm.expr = Expression.parse(scanner);

        scanner.skip(doToken);
        whileStatm.body = Statement.parse(scanner);

        PascalSyntax.leaveParser("while-statm");
        return whileStatm;
    }
}