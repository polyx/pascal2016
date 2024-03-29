package parser;

import java.util.ArrayList;

import main.CodeFile;
import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class ParamDeclList extends PascalSyntax {
    ArrayList<ParamDecl> paramDecls;
    ParamDecl pd;

    @Override
    public void genCode(CodeFile f) {
    }

    ParamDeclList(int lNum) {
        super(lNum);
        paramDecls = new ArrayList<>();
    }

    @Override
    public void check(Block curScope, Library lib) {
        int counter = 8;
        for (ParamDecl paramDecl : paramDecls) {
            paramDecl.check(curScope, lib);
            paramDecl.declOffset = counter;
            counter += 4;
        }
    }

    @Override
    public String identify() {
        return "<param-decl-paramDecls> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint("(");

        int counter = paramDecls.size();

        for (ParamDecl p : paramDecls) {
            p.prettyPrint();
            if (--counter != 0)
                Main.log.prettyPrint("; ");
        }

        Main.log.prettyPrint(")");
    }

    public static ParamDeclList parse(Scanner scanner) {
        enterParser("param decl list");

        ParamDeclList declList = new ParamDeclList(scanner.curLineNum());
        scanner.skip(leftParToken);

        declList.paramDecls.add(ParamDecl.parse(scanner));

        while (scanner.curToken.kind == semicolonToken) { // loops untill we have parsed all declarations
            scanner.skip(semicolonToken);
            declList.paramDecls.add(ParamDecl.parse(scanner));
        }
        scanner.skip(rightParToken);

        leaveParser("param decl list");
        return declList;
    }
}