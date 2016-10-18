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
        Main.log.prettyPrint("procedure " + name + "");

        if (paramList != null) {
            paramList.prettyPrint();
        }

        Main.log.prettyPrint(";");
        Main.log.prettyPrintLn(" ");
        procBody.prettyPrint();
        Main.log.prettyPrint(";");
    }

    static ProcDecl parse(Scanner s) {
        enterParser("proc-decl");

        ProcDecl procedure = new ProcDecl(s.curToken.id, s.curLineNum());

        s.skip(procedureToken);
        s.skip(nameToken);

        //parse params if any
        if (s.curToken.kind == leftParToken) {
            procedure.paramList = ParamDeclList.parse(s);
        }

        s.skip(semicolonToken);
        procedure.procBody = Block.parse(s);
        s.skip(semicolonToken);

        leaveParser("proc-decl");
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