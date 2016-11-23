package parser;

import main.CodeFile;
import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class FactorOperator extends Operator {
    String id;

    @Override
    public void genCode(CodeFile f) {

    }

    FactorOperator(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {

    }

    @Override
    public String identify() {
        return "<Factor Operator " + id + "> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(" " + id + " ");
    }

    static FactorOperator parse(Scanner scanner) {
        enterParser("factor opr");
        FactorOperator operator = new FactorOperator(scanner.curLineNum());

        operator.id = scanner.curToken.kind.toString();
        if (scanner.curToken.kind == multiplyToken) {
            scanner.skip(multiplyToken);
        } else if (scanner.curToken.kind == divToken) {
            scanner.skip(divToken);
        } else if (scanner.curToken.kind == modToken) {
            scanner.skip(modToken);
        } else if (scanner.curToken.kind == andToken) {
            scanner.skip(andToken);
        }

        leaveParser("factor opr");
        return operator;
    }
}