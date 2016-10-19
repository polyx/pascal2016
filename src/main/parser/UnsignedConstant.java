package parser;


import scanner.Scanner;

public abstract class UnsignedConstant extends Factor {

    UnsignedConstant(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Constant> on line " + lineNum;
    }

    public static UnsignedConstant parse(Scanner s) {
        enterParser("unsigned constant");
        UnsignedConstant unsignConst = null;
        switch (s.curToken.kind) {
            case nameToken:
                unsignConst = NamedConst.parse(s);
                break;
            case intValToken:
                unsignConst = NumberLiteral.parse(s);
                break;
            case charValToken:
                unsignConst = CharLiteral.parse(s);
                break;
            default:
                s.testError("unsigned constant");
        }

        leaveParser("unsigned constant");
        return unsignConst;
    }
}
