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
    public String identify() {
        return "<var-decl-part> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint("var ");

        Main.log.prettyIndent();
        Main.log.prettyPrintLn();

        for (VarDecl v : varList) {
            v.prettyPrint();
            Main.log.prettyPrintLn();
        }

        Main.log.prettyOutdent();
    }

    public static VarDeclPart parse(Scanner s) {
        enterParser("var decl part");

        VarDeclPart varDeclPart = new VarDeclPart(s.curLineNum());
        s.skip(varToken);

        while (s.curToken.kind == nameToken) {
            varDeclPart.varList.add(VarDecl.parse(s));
        }

        leaveParser("var decl part");
        return varDeclPart;
    }
}
