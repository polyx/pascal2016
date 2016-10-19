package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class NumberLiteral extends UnsignedConstant {
    int value;

    NumberLiteral(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<numeric-literal> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(Integer.toString(value));
    }

    public static NumberLiteral parse(Scanner scanner) {
        enterParser("number literal");

        NumberLiteral number = new NumberLiteral(scanner.curLineNum());
        number.value = scanner.curToken.intVal;
        scanner.skip(intValToken);

        leaveParser("number literal");
        return number;
    }
}