package parser;

import main.CodeFile;
import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class FuncDecl extends ProcDecl {
    TypeName funcTypeName;
    Block funcBody;
    String labelName;

    FuncDecl(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    public void genCode(CodeFile f) {
        labelName = f.getLabel(name.toLowerCase());
        f.genInstr("func$" + labelName,
                "enter",
                "$" + (32 + funcBody.nextOffset) + ",$" + funcBody.level,
                "Start of " + name);
        if(paramList != null) {
            paramList.genCode(f);
        }
        funcBody.genCode(f);
        f.genInstr("", "movl", "-32(%ebp), %eax", "");
        f.genInstr("", "leave", "", "");
        f.genInstr("", "ret", "", "");
    }

    @Override
    public String identify() {
        return "<func-decl> "+name+" on line " + lineNum;
    }

    @Override
    public void check(Block curScope, Library lib) {
        curScope.addDecl(name, this);
        if (paramList != null) {
            paramList.check(curScope, lib);
        }
        funcTypeName.check(curScope, lib);
        type = funcTypeName.type;
        funcBody.check(curScope, lib);
        this.declOffset = curScope.nextOffset + 4;
        this.declLevel = curScope.level;
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

    @Override
    void checkWhetherAssignable(PascalSyntax where) {

    }

    @Override
    void checkWhetherFunction(PascalSyntax where) {

    }

    @Override
    void checkWhetherProcedure(PascalSyntax where) {
        where.error(where.identify() + " is a function, not a procedure");
    }

    @Override
    void checkWhetherValue(PascalSyntax where) {
        where.error(where.identify() + " is not a value");
    }
}
