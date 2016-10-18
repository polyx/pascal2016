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

    HashMap<String, FuncDecl> funcDecls;
    HashMap<String, ProcDecl> procDecls;


    Block(int lNum) {
        super(lNum);
        funcDecls = new HashMap<>();
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
        if (procDecls.size() > 0){
            procDecls.values().forEach(ProcDecl::prettyPrint);
        }

        //print proc decls
        if (funcDecls.size() > 0){
            funcDecls.values().forEach(FuncDecl::prettyPrint);
        }


        Main.log.prettyPrintLn("begin");
        Main.log.prettyIndent();
        statmList.prettyPrint();
        Main.log.prettyOutdent();
        Main.log.prettyPrint("end");
    }

    public static Block parse(Scanner scanner) {
        enterParser("block");

        Block block = new Block(scanner.curLineNum());

        if (scanner.nextToken.kind == constToken) block.constDeclPart = ConstDeclPart.parse(scanner);
        if (scanner.nextToken.kind == varToken) block.varDeclPart = VarDeclPart.parse(scanner);

        while (scanner.nextToken.kind == functionToken || scanner.nextToken.kind == procedureToken) {
            if (scanner.nextToken.kind == functionToken) {
                FuncDecl fd = FuncDecl.parse(scanner);
                block.funcDecls.put(fd.name, fd);

            } else if (scanner.nextToken.kind == procedureToken) {
                ProcDecl pd = ProcDecl.parse(scanner);
                block.procDecls.put(pd.name, pd);

            }
        }

        scanner.skip(beginToken);
        block.statmList = StatmList.parse(scanner);
        scanner.skip(endToken);

        leaveParser("block");
        return block;
    }
}
