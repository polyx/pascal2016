package parser;

import java.util.ArrayList;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class VarDeclPart extends PascalSyntax {
    ArrayList<VarDecl> varList;

    VarDeclPart(int lNum) {
        super(lNum);
        varList = new ArrayList<>();
    }

    @Override
    public void check(Block curScope, Library lib) {
        varList.forEach(varDecl -> varDecl.check(curScope, lib));
    }

    @Override
    public String identify() {
        return "<var-decl-part> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint("var ");
        Main.log.prettyIndent();
        Main.log.prettyPrintLn();
        int counter = varList.size();
        for (VarDecl varDecl:varList){
            varDecl.prettyPrint();
            if (--counter != 0){
                Main.log.prettyPrintLn();
            }
        }

        Main.log.prettyOutdent();
    }

    public static VarDeclPart parse(Scanner scanner) {
        enterParser("var decl part");

        VarDeclPart varDeclPart = new VarDeclPart(scanner.curLineNum());
        scanner.skip(varToken);

        while (scanner.curToken.kind == nameToken) {
            varDeclPart.varList.add(VarDecl.parse(scanner));
        }

        leaveParser("var decl part");
        return varDeclPart;
    }
}
