package parser;


import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class TermOperator extends Operator {
    String name;

    TermOperator(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Term Operator> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        if (name.equals("or"))
            Main.log.prettyPrint(" " + name + " ");
        else
            Main.log.prettyPrint(name);
    }

    static TermOperator parse(Scanner s) {
        enterParser("term opr");
        TermOperator operator = new TermOperator(s.curLineNum());

        operator.name = s.curToken.kind.toString();
        switch (s.curToken.kind) {
            case addToken:
                s.skip(addToken);
                break;
            case subtractToken:
                s.skip(subtractToken);
                break;
            case orToken:
                s.skip(orToken);
                break;
        }

        leaveParser("term opr");
        return operator;
    }
}