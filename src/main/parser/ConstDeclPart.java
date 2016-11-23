package parser;

import java.util.ArrayList;

import main.CodeFile;
import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class ConstDeclPart extends PascalSyntax {
    ArrayList<ConstDecl> constList = new ArrayList<>();

    @Override
    public void genCode(CodeFile f) {

    }

    ConstDeclPart(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        for (ConstDecl constDecl : constList){
            constDecl.check(curScope, lib);
        }
    }

    @Override public String identify() {
        return "<ConstDeclPart> on line " + lineNum;
    }

    @Override public void prettyPrint() {
        Main.log.prettyPrint("const ");
        Main.log.prettyPrintLn();
        Main.log.prettyIndent();
        constList.forEach(constDecl -> {constDecl.prettyPrint(); Main.log.prettyPrintLn();});
        Main.log.prettyOutdent();
    }

    public static ConstDeclPart parse(Scanner scanner) {
        enterParser("const decl part");

        scanner.skip(constToken);
        ConstDeclPart constDeclPart = new ConstDeclPart(scanner.curLineNum());
        while(scanner.curToken.kind == nameToken) {
            constDeclPart.constList.add(ConstDecl.parse(scanner));
        }

        leaveParser("const decl part");
        return constDeclPart;
    }
}