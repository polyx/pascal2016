package parser;


import scanner.Scanner;

import static scanner.TokenKind.*;

public abstract class UnsignedConstant extends Factor {

    UnsignedConstant(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Constant> on line " + lineNum;
    }

    public static UnsignedConstant parse(Scanner scanner) {
        enterParser("unsigned constant");
        UnsignedConstant unsignConst = null;
        if (scanner.curToken.kind == nameToken) {
            unsignConst = NamedConst.parse(scanner);
        } else if (scanner.curToken.kind == intValToken) {
            unsignConst = NumberLiteral.parse(scanner);
        } else if (scanner.curToken.kind == charValToken) {
            unsignConst = CharLiteral.parse(scanner);
        } else {
            scanner.testError("unsigned constant");
        }

        leaveParser("unsigned constant");
        return unsignConst;
    }
}
