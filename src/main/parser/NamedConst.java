package parser;

import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class NamedConst extends UnsignedConstant {
    String name;

    NamedConst(int lNum) {
        super(lNum);
    }

    @Override public String identify() {
        return "<named-const> on line " + lineNum;
    }

    @Override public void prettyPrint() {
        Main.log.prettyPrint(name);
    }

    public static NamedConst parse(Scanner s) {
        enterParser("named-const");

        NamedConst nameConst = new NamedConst(s.curLineNum());
        nameConst.name = s.curToken.id;
        s.skip(nameToken);

        leaveParser("named-const");
        return nameConst;
    }
}