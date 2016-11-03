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
        Main.log.prettyPrint("if ");
        condition.prettyPrint();
        Main.log.prettyPrintLn(" then ");
        Main.log.prettyIndent();
        statement.prettyPrint();
        Main.log.prettyOutdent();
        if(elseStatm != null) {
            Main.log.prettyPrintLn("");
            Main.log.prettyPrintLn("else");
            Main.log.prettyIndent();
            elseStatm.prettyPrint();
            Main.log.prettyOutdent();
        }
    }

    static IfStatm parse(Scanner scanner) {
        enterParser("if-statm");
        IfStatm ifStatm = new IfStatm(scanner.curLineNum());
        scanner.skip(ifToken);
        ifStatm.condition = Expression.parse(scanner);
        scanner.skip(thenToken);
        ifStatm.statement = Statement.parse(scanner);

        if(scanner.curToken.kind == elseToken) {
            scanner.skip(elseToken);
            ifStatm.elseStatm = Statement.parse(scanner);
        }

        leaveParser("if-statm");
        return ifStatm;
    }
}