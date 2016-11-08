package parser;

import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class CompoundStatm extends Statement {
    StatmList sl;

    CompoundStatm(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        sl.check(curScope, lib);
    }

    @Override public String identify() {
        return "<CompoundStatm> on line " + lineNum;
    }

    @Override public void prettyPrint() {
            Main.log.prettyIndent();
                Main.log.prettyPrintLn("begin");
                Main.log.prettyIndent();
                    sl.prettyPrint();
                Main.log.prettyOutdent();
            Main.log.prettyPrint("end");
        Main.log.prettyOutdent();
    }

    static CompoundStatm parse(Scanner scanner) {
        enterParser("compound statm");
        CompoundStatm compoundStatm = new CompoundStatm(scanner.curLineNum());

        scanner.skip(beginToken);
        compoundStatm.sl = StatmList.parse(scanner);
        scanner.skip(endToken);

        leaveParser("compound statm");
        return compoundStatm;
    }
}