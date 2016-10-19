package parser;


import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Block extends PascalSyntax {
    ConstDeclPart constDeclPart;
    VarDeclPart varDeclPart;
    StatmList statmList;

    ArrayList<FuncDecl> funcDecls;
    ArrayList<ProcDecl> procDecls;


    Block(int lNum) {
        super(lNum);
        funcDecls = new ArrayList<>();
        procDecls = new ArrayList<>();
    }

    @Override
    public String identify() {
        return "<block> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        if (constDeclPart != null) {
            constDeclPart.prettyPrint();
            Main.log.prettyPrintLn("");
        }

        if (varDeclPart != null) {
            varDeclPart.prettyPrint();
            Main.log.prettyPrintLn("");
        }

        // neither const or var decls parts exist so just put an empty line
        if (constDeclPart == null && varDeclPart == null)
            Main.log.prettyPrintLn("");

        //print func decls
        if (procDecls.size() > 0) {
            procDecls.forEach(ProcDecl::prettyPrint);
        }

        //print proc decls
        if (funcDecls.size() > 0) {
            funcDecls.forEach(FuncDecl::prettyPrint);
        }


        //Main.log.prettyPrintLn();
        Main.log.prettyPrintLn("begin");
        Main.log.prettyIndent();
        statmList.prettyPrint();
        Main.log.prettyOutdent();
        Main.log.prettyPrint("end");
    }

    public static Block parse(Scanner scanner) {
        enterParser("block");

        Block block = new Block(scanner.curLineNum());

       if (scanner.curToken.kind == constToken) {
            block.constDeclPart = ConstDeclPart.parse(scanner);
        }
        if (scanner.curToken.kind == varToken) {
            block.varDeclPart = VarDeclPart.parse(scanner);
        }

        while (scanner.curToken.kind == functionToken || scanner.curToken.kind == procedureToken) {
            if (scanner.curToken.kind == functionToken) {
                FuncDecl fd = FuncDecl.parse(scanner);
                block.funcDecls.add(fd);

            } else if (scanner.curToken.kind == procedureToken) {
                ProcDecl pd = ProcDecl.parse(scanner);
                block.procDecls.add(pd);

            }
        }

        scanner.skip(beginToken);
        block.statmList = StatmList.parse(scanner);
        scanner.skip(endToken);

        leaveParser("block");
        return block;
    }
}
