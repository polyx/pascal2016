package parser;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class VarDecl extends PascalDecl{
    Type type;
    VarDecl(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        type.check(curScope, lib);
        curScope.addDecl(name, this);
    }

    @Override
    void prettyPrint() {
        Main.log.prettyPrint(name + ": ");
        type.prettyPrint();
        Main.log.prettyPrint(";");
    }

    @Override
    public String identify() {
        return "<var-decl> on line " + lineNum;
    }

    static public VarDecl parse(Scanner scanner){
        enterParser("var decl");

        VarDecl varDecl = new VarDecl(scanner.curToken.id, scanner.curLineNum());

        scanner.skip(nameToken);
        scanner.skip(colonToken);

        varDecl.type = Type.parse(scanner);

        scanner.skip(semicolonToken);

        leaveParser("var decl");
        return varDecl;
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
