package parser;

import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class ConstDecl extends PascalDecl {
    Constant constant;

    ConstDecl(String id, int lNum) {
        super(id, lNum);
    }

    @Override public String identify() {
        return "<ConstDecl> on line " + lineNum;
    }

    @Override
    public void check(Block curScope, Library lib) {
        constant.check(curScope, lib);
    }

    @Override void prettyPrint() {
        Main.log.prettyPrint(name + " = ");
        constant.prettyPrint();
        Main.log.prettyPrint("; ");
    }

    static ConstDecl parse(Scanner scanner) {
        enterParser("const decl");

        ConstDecl constDecl = new ConstDecl(scanner.curToken.id, scanner.curLineNum());
        scanner.skip(nameToken);
        scanner.skip(equalToken);
        constDecl.constant = Constant.parse(scanner);
        scanner.skip(semicolonToken);

        leaveParser("const decl");
        return constDecl;
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