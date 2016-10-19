package parser;

import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class CompoundStatm extends Statement {
    StatmList sl;

    CompoundStatm(int lNum) {
        super(lNum);
    }

    @Override public String identify() {
        return "<CompoundStatm> on line " + lineNum;
    }

    @Override public void prettyPrint() {
        Main.log.prettyPrintLn();
            Main.log.prettyIndent();
                Main.log.prettyPrintLn("begin");
                Main.log.prettyIndent();
                sl.prettyPrint();
                Main.log.prettyOutdent();
            Main.log.prettyPrint("end");
        Main.log.prettyOutdent();

    }

    static CompoundStatm parse(Scanner s) {
        enterParser("compound statm");
        CompoundStatm cp = new CompoundStatm(s.curLineNum());

        s.skip(beginToken);
        cp.sl = StatmList.parse(s);
        s.skip(endToken);

        leaveParser("compound statm");
        return cp;
    }
}