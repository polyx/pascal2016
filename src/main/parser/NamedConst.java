package parser;

import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class NamedConst extends UnsignedConstant {
    String name;

    NamedConst(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        curScope.findDecl(name, this);
    }

    @Override public String identify() {
        return "<named-const> on line " + lineNum;
    }

    @Override public void prettyPrint() {
        Main.log.prettyPrint(name);
    }

    public static NamedConst parse(Scanner scanner) {
        enterParser("named-const");

        NamedConst nameConst = new NamedConst(scanner.curLineNum());
        nameConst.name = scanner.curToken.id;
        scanner.skip(nameToken);

        leaveParser("named-const");
        return nameConst;
    }
}