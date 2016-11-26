package parser;

import main.CodeFile;
import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class CharLiteral extends UnsignedConstant {
    String charVal;

    @Override
    public void genCode(CodeFile f) {
        char c = charVal.charAt(0);
        f.genInstr("", "movl", "$" + (int)c + ",%eax", charVal);
    }

    CharLiteral(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        constVal = charVal.charAt(0);
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