package parser;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class ProcDecl extends PascalDecl {
    ParamDeclList paramList;
    Block procBody;


    ProcDecl(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    public String identify() {
        return "<proc decl> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrintLn();
        Main.log.prettyPrint("procedure " + name + " ");

        if (paramList != null) {
            paramList.prettyPrint();
        }

        Main.log.prettyPrint(";");
        Main.log.prettyPrintLn();
        Main.log.prettyIndent();
        procBody.prettyPrint();
        Main.log.prettyOutdent();
        Main.log.prettyPrintLn(";");
    }

    static ProcDecl parse(Scanner scanner) {
        enterParser("proc decl");

        ProcDecl procedure = new ProcDecl(scanner.nextToken.id, scanner.curLineNum());

        scanner.skip(procedureToken);
        scanner.skip(nameToken);

        //parse params if any
        if (scanner.curToken.kind == leftParToken) {
            procedure.paramList = ParamDeclList.parse(scanner);
        }

        scanner.skip(semicolonToken);
        procedure.procBody = Block.parse(scanner);
        scanner.skip(semicolonToken);

        leaveParser("proc decl");
        return procedure;
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