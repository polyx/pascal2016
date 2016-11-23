package parser;

import main.CodeFile;
import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;


public class AssignStatm extends Statement {
    Variable var;
    Expression expr;

    @Override
    public void genCode(CodeFile f) {

    }

    AssignStatm(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        var.check(curScope, lib);
        var.pascDecl.checkWhetherAssignable(this);
        expr.check(curScope, lib);
        var.type.checkType(expr.type, ":=", this, "type missmatch left and right side, type of " +
                var.name + " (" + var.type.identify() + ") != " + expr.type.identify());
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