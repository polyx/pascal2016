package parser;


import main.CodeFile;
import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class TermOperator extends Operator {
    String name;

    @Override
    public void genCode(CodeFile f) {

    }

    TermOperator(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        //pass nothing to check
    }

    @Override
    public String identify() {
        return "<Term Operator> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(" " + name + " ");
    }

    static TermOperator parse(Scanner scanner) {
        enterParser("term opr");
        TermOperator operator = new TermOperator(scanner.curLineNum());

        operator.name = scanner.curToken.kind.toString();

        if (scanner.curToken.kind == addToken) {
            scanner.skip(addToken);
        } else if (scanner.curToken.kind == subtractToken) {
            scanner.skip(subtractToken);
        } else if (scanner.curToken.kind == orToken) {
            scanner.skip(orToken);
        }

        leaveParser("term opr");
        return operator;
    }
}