package parser;

import main.CodeFile;
import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class VarDecl extends PascalDecl{
    Type typeName;
    VarDecl(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    public void genCode(CodeFile f) {

    }

    @Override
    public void check(Block curScope, Library lib) {
        typeName.check(curScope, lib);
        type = typeName.type;
        curScope.addDecl(name, this);
        curScope.nextOffset += 4;
        this.declOffset = curScope.nextOffset;
        this.declLevel = curScope.level;

    }

    @Override
    void prettyPrint() {
        Main.log.prettyPrint(name + ": ");
        typeName.prettyPrint();
        Main.log.prettyPrint(";");
    }

    @Override
    public String identify() {
        return "<var decl> " + name +
                " on line " + lineNum;
    }

    static public VarDecl parse(Scanner scanner){
        enterParser("var decl");

        VarDecl varDecl = new VarDecl(scanner.curToken.id, scanner.curLineNum());

        scanner.skip(nameToken);
        scanner.skip(colonToken);

        varDecl.typeName = Type.parse(scanner);

        scanner.skip(semicolonToken);

        leaveParser("var decl");
        return varDecl;
    }

    @Override
    void checkWhetherAssignable(PascalSyntax where) {

    }

    @Override
    void checkWhetherFunction(PascalSyntax where) {
        where.error(where.identify() + " is not a function");
    }

    @Override
    void checkWhetherProcedure(PascalSyntax where) {
        where.error(where.identify() + " is not a procedure");
    }

    @Override
    void checkWhetherValue(PascalSyntax where) {

    }
}
