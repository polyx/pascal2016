package parser;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

/**
 * Created by filos on 15/10/2016.
 */
public class VarDecl extends PascalDecl{
    Type type;
    VarDecl(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    void prettyPrint() {
        Main.log.prettyPrint(name + ": ");
        type.prettyPrint();
    }

    @Override
    public String identify() {
        return null;
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

    static public VarDecl parse(Scanner scanner){
        enterParser("var-decl");
        VarDecl varDecl = new VarDecl(scanner.nextToken.id, scanner.curLineNum());
        scanner.skip(nameToken);
        scanner.skip(colonToken);
        varDecl.type = Type.parse(scanner);
        scanner.skip(semicolonToken);
        leaveParser("var-decl");
        return varDecl;
    }
}
