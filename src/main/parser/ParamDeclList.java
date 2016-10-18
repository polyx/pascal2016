package parser;

import java.util.ArrayList;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class ParamDeclList extends PascalSyntax {
    ArrayList<ParamDecl> paramDecls = new ArrayList<>();
    ParamDecl pd;

    ParamDeclList(int lNum) {
        super(lNum);
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

    public static ParamDeclList parse(Scanner s) {
        enterParser("param-decl-paramDecls");

        ParamDeclList declList = new ParamDeclList(s.curLineNum());
        s.skip(leftParToken);

        declList.paramDecls.add(ParamDecl.parse(s));

        while (s.curToken.kind == semicolonToken) { // loops untill we have parsed all declarations
            s.skip(semicolonToken);
            declList.paramDecls.add(ParamDecl.parse(s));
        }
        s.skip(rightParToken);

        leaveParser("param-decl-paramDecls");
        return declList;
    }
}