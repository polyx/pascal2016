package parser;

import java.util.ArrayList;
import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class ConstDeclPart extends PascalSyntax {
    ArrayList<ConstDecl> constList = new ArrayList<>();

    ConstDeclPart(int lNum) {
        super(lNum);
    }

    @Override public String identify() {
        return "<ConstDeclPart> on line " + lineNum;
    }

    @Override public void prettyPrint() {
        Main.log.prettyPrint("const ");
        constList.forEach(ConstDecl::prettyPrint);
    }

    public static ConstDeclPart parse(Scanner s) {
        enterParser("const-decl-part");

        s.skip(constToken);
        ConstDeclPart constDeclPart = new ConstDeclPart(s.curLineNum());
        while(s.curToken.kind == nameToken) {
            constDeclPart.constList.add(ConstDecl.parse(s));
        }

        leaveParser("const-decl-part");
        return constDeclPart;
    }
}