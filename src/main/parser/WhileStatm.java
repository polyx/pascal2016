package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

class WhileStatm extends Statement {
    Expression expr;
    Statement body;

    @Override
    public void genCode(CodeFile f) {

    }

    WhileStatm(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        expr.check(curScope, lib);
        expr.type.checkType(lib.booleanType, "while-test", this,
                "unexpected expression type in while statement.\n" +
                        "Expected: boolean\n" +
                        "Got: " + expr.type);
        body.check(curScope, lib);
    }

    public void prettyPrint(){
        Main.log.prettyPrint("while ");
        expr.prettyPrint();
        Main.log.prettyPrintLn(" do ");
        body.prettyPrint();
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