package parser;

import scanner.Scanner;

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


    static Statement parse(Scanner s) {
        enterParser("statement");

        if (s.curToken.kind == scanner.TokenKind.nameToken) {
            if (s.nextToken.kind == scanner.TokenKind.leftBracketToken) {
                statm = AssignStatm.parse(s);

            } else if (s.nextToken.kind == scanner.TokenKind.leftParToken) {
                statm = ProcCallStatm.parse(s);

            } else if (s.nextToken.kind == scanner.TokenKind.assignToken) {
                statm = AssignStatm.parse(s);

            } else {
                statm = ProcCallStatm.parse(s);

            }

        } else if (s.curToken.kind == scanner.TokenKind.beginToken) {
            statm = CompoundStatm.parse(s);

        } else if (s.curToken.kind == scanner.TokenKind.ifToken) {
            statm = IfStatm.parse(s);

        } else if (s.curToken.kind == scanner.TokenKind.whileToken) {
            statm = WhileStatm.parse(s);

        } else {
            statm = EmptyStatm.parse(s);

        }

        leaveParser("statement");
        return statm;
    }
}
