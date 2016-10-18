package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class PrefixOperator extends Operator {
    String name;

    PrefixOperator(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Prefix Operator> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(" " + name + " ");
    }

    static PrefixOperator parse(Scanner s) {
        enterParser("prefix-operator");
        PrefixOperator operator = new PrefixOperator(s.curLineNum());

        operator.name = s.curToken.kind.toString();
        switch (s.curToken.kind) {
            case subtractToken:
                s.skip(subtractToken);
                break;
            case addToken:
                s.skip(addToken);
                break;
        }

        leaveParser("prefix-operator");
        return operator;
    }
}