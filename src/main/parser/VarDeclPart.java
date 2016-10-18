package parser;

import java.util.ArrayList;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class VarDeclPart extends PascalSyntax {
    ArrayList<VarDecl> varList = new ArrayList<>();

    VarDeclPart(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<var-decl-part> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint("var ");
        int counter = 0;

        if (varList.size() > 1) {
            Main.log.prettyIndent();
            Main.log.prettyPrintLn(" ");
            System.out.println("");
        }
        for (VarDecl v : varList) {
            v.prettyPrint();
            counter++;
            if (counter == 5) {
                counter = 0;
                Main.log.prettyPrintLn(" ");
            }
        }

        if (varList.size() > 1)
            Main.log.prettyOutdent();
    }

    public static VarDeclPart parse(Scanner s) {
        enterParser("var-decl-part");

        VarDeclPart varDeclPart = new VarDeclPart(s.curLineNum());
        s.skip(varToken);

        while (s.curToken.kind == nameToken) {
            varDeclPart.varList.add(VarDecl.parse(s));
        }

        leaveParser("var-decl-part");
        return varDeclPart;
    }
}
