package parser;


import main.CodeFile;
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
    HashMap<String, PascalDecl> decls;
    Block outerScope;

    @Override
    public void genCode(CodeFile f) {

    }

    Block(int lNum) {
        super(lNum);
        funcDecls = new ArrayList<>();
        procDecls = new ArrayList<>();
        decls = new HashMap<>();
//        outerScope = Main.library;
    }

    void addDecl(String id, PascalDecl d) {
        id = id.toLowerCase();
        if (decls.containsKey(id)) {
            d.error(id + " declared twice in same block!");
        }
        decls.put(id, d);
    }

    PascalDecl findDecl(String id, PascalSyntax where) {
        PascalDecl d = decls.get(id);
        if (d != null) {
            Main.log.noteBinding(id, where, d);
            return d;
        } else if (outerScope != null) {
            return outerScope.findDecl(id, where);
        } else {
            where.error("Name " + id + " is unknown!");
            return null; // Required by the Java compiler.
        }
    }

    @Override
    public void check(Block outerScope, Library lib) {
        this.outerScope = outerScope;
        if (constDeclPart != null) {
            constDeclPart.check(this, lib);
        }
        if (varDeclPart != null) {
            varDeclPart.check(this, lib);
        }
        if (funcDecls.size() != 0) {
//            funcDecls.forEach(funcDecl -> funcDecl.check(this, lib));
            for (FuncDecl fd : funcDecls) {
                fd.check(this, lib);
            }
        }
        if (procDecls.size() != 0) {
            procDecls.forEach(funcDecl -> funcDecl.check(this, lib));
        }
        if (statmList != null) {
            statmList.check(this, lib);
        }
    }

    @Override
    public String identify() {
        return "<block> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        if (constDeclPart != null) {
            constDeclPart.prettyPrint();
        }

        if (varDeclPart != null) {
            varDeclPart.prettyPrint();
        }

        //print func decls
        if (procDecls.size() > 0) {
            procDecls.forEach(ProcDecl::prettyPrint);
            Main.log.prettyPrintLn("");
        }

        //print proc decls
        if (funcDecls.size() > 0) {
            funcDecls.forEach(FuncDecl::prettyPrint);
            Main.log.prettyPrintLn("");
        }
        if (varDeclPart == null && constDeclPart == null) {
            Main.log.prettyPrintLn("begin");
        } else {
            Main.log.prettyPrintLn();
            Main.log.prettyPrintLn("begin");
        }
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
                FuncDecl func = FuncDecl.parse(scanner);
                block.funcDecls.add(func);
            } else if (scanner.curToken.kind == procedureToken) {
                ProcDecl proc = ProcDecl.parse(scanner);
                block.procDecls.add(proc);

            }
        }

        scanner.skip(beginToken);
        block.statmList = StatmList.parse(scanner);
        scanner.skip(endToken);

        leaveParser("block");
        return block;
    }
}
