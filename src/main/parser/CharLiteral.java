package parser;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class CharLiteral extends UnsignedConstant {
    String charVal;

    CharLiteral(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<char-literal> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint("'" + charVal + "'");
    }

    public static CharLiteral parse(Scanner s) {
        enterParser("char literal");

        CharLiteral charLiteral = new CharLiteral(s.curLineNum());
        charLiteral.charVal = String.valueOf(s.curToken.charVal);
        s.skip(charValToken);
        leaveParser("char literal");
        return charLiteral;
    }
}