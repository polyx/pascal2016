package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class PrefixOperator extends Operator {
    String name;

    @Override
    public void genCode(CodeFile f) {
        if (name.equals("-")){
            f.genInstr("","negl","%eax","- (prefix)");
        }
    }

    PrefixOperator(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {

    }

    @Override
    public String identify() {
        return "<Prefix Operator> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(name);
    }

    static PrefixOperator parse(Scanner scanner) {
        enterParser("prefix-operator");
        PrefixOperator operator = new PrefixOperator(scanner.curLineNum());

        operator.name = scanner.curToken.kind.toString();
        if (scanner.curToken.kind == TokenKind.subtractToken) {
            scanner.skip(subtractToken);
        } else if (scanner.curToken.kind == TokenKind.addToken) {
            scanner.skip(addToken);
        }

        leaveParser("prefix-operator");
        return operator;
    }
}