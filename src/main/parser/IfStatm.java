package parser;

import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class IfStatm extends Statement {
    Expression condition;
    Statement statement;
    Statement elseStatm;

    IfStatm(int lNum) {
        super(lNum);
    }

    @Override public String identify() {
        return "<if-statement> on line " + lineNum;
    }

    @Override public void prettyPrint() {
        Main.log.prettyPrintLn(" ");
        Main.log.prettyPrint("if ");
        condition.prettyPrint();
        Main.log.prettyPrintLn(" then ");
        statement.prettyPrint();
        if(elseStatm != null) {
            Main.log.prettyPrintLn(" ");
            Main.log.prettyOutdent();
            Main.log.prettyPrintLn("else");
            elseStatm.prettyPrint();
        }
        Main.log.prettyOutdent();
    }

    static IfStatm parse(Scanner s) {
        enterParser("if-statement");
        IfStatm ifStatm = new IfStatm(s.curLineNum());
        s.skip(ifToken);
        ifStatm.condition = Expression.parse(s);
        s.skip(thenToken);
        ifStatm.statement = Statement.parse(s);

        if(s.curToken.kind == elseToken) {
            s.skip(elseToken);
            ifStatm.elseStatm = Statement.parse(s);
        }

        leaveParser("if-statement");
        return ifStatm;
    }
}