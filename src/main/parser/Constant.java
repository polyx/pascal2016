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
        if (prefix != null){
            prefix.prettyPrint();
        }
        constant.prettyPrint();
    }

    public static Constant parse(Scanner scanner) {
        enterParser("constant");
        Constant constant = new Constant(scanner.curLineNum());
        if (scanner.curToken.kind == TokenKind.subtractToken || scanner.curToken.kind == TokenKind.addToken) {
            constant.prefix = PrefixOperator.parse(scanner);
        }
        constant.constant = UnsignedConstant.parse(scanner);
        leaveParser("constant");
        return constant;
    }
}