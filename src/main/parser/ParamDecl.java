package parser;

import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class ParamDecl extends PascalDecl {
    TypeName paramType;

    ParamDecl(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    public String identify() {
        return "<param-decl> on line " + lineNum;
    }

    @Override
    public void check(Block curScope, Library lib) {
        curScope.addDecl(this.name,this );
        paramType.check(curScope, lib);
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(name + " : ");
        paramType.prettyPrint();
    }

    public static ParamDecl parse(Scanner scanner) {
        enterParser("param decl");

        ParamDecl paramDecl = new ParamDecl(scanner.curToken.id, scanner.curLineNum());
        scanner.skip(nameToken);
        scanner.skip(colonToken);
        paramDecl.paramType = TypeName.parse(scanner);

        leaveParser("param decl");
        return paramDecl;
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