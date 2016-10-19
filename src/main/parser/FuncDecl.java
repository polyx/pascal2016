package parser;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class FuncDecl extends ProcDecl {
    TypeName funcTypeName;
    Block funcBody;

    FuncDecl(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    public String identify() {
        return "<func-decl> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint("function " + name);

        if (paramList != null)
            paramList.prettyPrint();

        Main.log.prettyPrint(": ");
        funcTypeName.prettyPrint();
        Main.log.prettyPrintLn(";");
        funcBody.prettyPrint();
        Main.log.prettyPrintLn(";");
    }

    public static FuncDecl parse(Scanner s) {
        enterParser("func decl");

        FuncDecl fd = new FuncDecl(s.nextToken.id, s.curLineNum());

        s.skip(functionToken);
        s.skip(nameToken);

        if (s.curToken.kind == leftParToken) {
            fd.paramList = ParamDeclList.parse(s);
        }

        s.skip(colonToken);
        fd.funcTypeName = TypeName.parse(s);
        s.skip(semicolonToken);
        fd.funcBody = Block.parse(s);
        s.skip(semicolonToken);

        leaveParser("func decl");
        return fd;
    }
}
