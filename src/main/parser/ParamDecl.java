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
    public void prettyPrint() {
        Main.log.prettyPrint(name + " : ");
        paramType.prettyPrint();
    }

    public static ParamDecl parse(Scanner s) {
        enterParser("param decl");

        ParamDecl paramDecl = new ParamDecl(s.curToken.id, s.curLineNum());
        s.skip(nameToken);
        s.skip(colonToken);
        paramDecl.paramType = TypeName.parse(s);

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