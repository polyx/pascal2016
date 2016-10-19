package parser;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public abstract class Statement extends PascalSyntax {
    static Statement statm;

    Statement(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Statement> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        statm.prettyPrint();
    }


    static Statement parse(Scanner scanner) {
        enterParser("statement");

        if (scanner.curToken.kind == nameToken) {
            if (scanner.nextToken.kind == leftBracketToken) {
                statm = AssignStatm.parse(scanner);

            } else if (scanner.nextToken.kind == leftParToken) {
                statm = ProcCallStatm.parse(scanner);

            } else if (scanner.nextToken.kind == assignToken) {
                statm = AssignStatm.parse(scanner);

            } else {
                statm = ProcCallStatm.parse(scanner);

            }

        } else if (scanner.curToken.kind == beginToken) {
            statm = CompoundStatm.parse(scanner);

        } else if (scanner.curToken.kind == ifToken) {
            statm = IfStatm.parse(scanner);

        } else if (scanner.curToken.kind == whileToken) {
            statm = WhileStatm.parse(scanner);

        } else {
            statm = EmptyStatm.parse(scanner);

        }

        leaveParser("statement");
        return statm;
    }
}
