package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class Constant extends PascalSyntax {
    PrefixOperator prefix;
    UnsignedConstant constant;

    Constant(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Constant> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        constant.prettyPrint();
    }

    public static Constant parse(Scanner s) {
        enterParser("constant");
        Constant c = new Constant(s.curLineNum());
        c.prefix = PrefixOperator.parse(s);
        c.constant = UnsignedConstant.parse(s);
        leaveParser("constant");
        return c;
    }
}