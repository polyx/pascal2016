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

    public static CharLiteral parse(Scanner scanner) {
        enterParser("char literal");

        CharLiteral charLiteral = new CharLiteral(scanner.curLineNum());
        charLiteral.charVal = String.valueOf(scanner.curToken.charVal);
        scanner.skip(charValToken);

        leaveParser("char literal");
        return charLiteral;
    }
}