package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class Constant extends PascalSyntax {
    PrefixOperator prefix;
    UnsignedConstant constant;
    types.Type type;

    int constVal;

    Constant(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        constant.check(curScope, lib);

        type = constant.type;
        constVal = constant.constVal;
        if (prefix != null) {
            String oprName = prefix.name;
            constant.type.checkType(lib.intType, "Prefix "+oprName, this,
                    "Prefix + or - may only be applied to Integers.");
            if (prefix.name.equals(subtractToken.toString())) {
                constVal = -constVal;
            }
        }
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