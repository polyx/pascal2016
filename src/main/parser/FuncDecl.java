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
    public void check(Block curScope, Library lib) {
        curScope.addDecl(name, this);
        if (paramList != null) {
            paramList.check(curScope, lib);
        }
        funcBody.check(curScope, lib);
        funcTypeName.check(curScope, lib);
        type = funcTypeName.type;
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

    public static FuncDecl parse(Scanner scanner) {
        enterParser("func decl");

        FuncDecl funcDecl = new FuncDecl(scanner.nextToken.id, scanner.curLineNum());

        scanner.skip(functionToken);
        scanner.skip(nameToken);

        if (scanner.curToken.kind == leftParToken) {
            funcDecl.paramList = ParamDeclList.parse(scanner);
        }

        scanner.skip(colonToken);
        funcDecl.funcTypeName = TypeName.parse(scanner);
        scanner.skip(semicolonToken);
        funcDecl.funcBody = Block.parse(scanner);
        scanner.skip(semicolonToken);

        leaveParser("func decl");
        return funcDecl;
    }
}
