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
        if (lineNum == -1){
            return "<proc decl> " + name + " in the library";
        }else {
            return "<proc decl> " + name + " on line " + lineNum;
        }
    }

    @Override
    public void check(Block curScope, Library lib) {
        procBody.outerScope = curScope;
        curScope.addDecl(name, this);
        if (paramList != null) {
            paramList.check(procBody, lib);
        }
        procBody.check(curScope, lib);
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
        where.error("Cannot assign to a procedure");
    }

    @Override
    void checkWhetherFunction(PascalSyntax where) {
        where.error(where.identify() + " is not a function");
    }

    @Override
    void checkWhetherProcedure(PascalSyntax where) {

    }

    @Override
    void checkWhetherValue(PascalSyntax where) {
        where.error(where.identify() + " is not a value");
    }
}