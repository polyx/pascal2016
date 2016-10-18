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
    }

    @Override
    public String identify() {
        return "<while-statm> on line " + lineNum;
    }

    static WhileStatm parse(Scanner s) {
        enterParser("while-statm");
        WhileStatm ws = new WhileStatm(s.curLineNum());
        s.skip(whileToken);
        ws.expr = Expression.parse(s);
        s.skip(doToken);
        ws.body = Statement.parse(s);
        PascalSyntax.leaveParser("while-statm");
        return ws;
    }
}