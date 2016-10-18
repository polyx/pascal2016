package parser;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;


public class Program extends PascalDecl {
    Block pb;
    String pname;

    Program(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    public String identify() {
        return "<Program on line >" + lineNum;
    }


    public void prettyPrint() {
        Main.log.prettyPrintLn("program " + pname + ";");
        pb.prettyPrint();
        Main.log.prettyPrint(".");
    }

    public static Program parse(Scanner s) {
        enterParser("program");

        s.skip(programToken);
        Program p = new Program(s.nextToken.id, s.curLineNum());
        p.pname = s.curToken.id;
        s.skip(nameToken);
        s.skip(semicolonToken);
        p.pb = Block.parse(s);
        s.skip(dotToken);

        leaveParser("program");
        return p;
    }

    @Override
    void checkWhetherAssignable(PascalSyntax where) {

    }

    @Override
    void checkWhetherFunction(PascalSyntax where) {

    }

    @Override
    void checkWhetherProcedure(PascalSyntax where) {

    }

    @Override
    void checkWhetherValue(PascalSyntax where) {

    }
}
