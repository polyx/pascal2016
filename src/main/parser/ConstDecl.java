package parser;

import main.CodeFile;
import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class ConstDecl extends PascalDecl {
    Constant constant;

    ConstDecl(String id, int lNum) {
        super(id, lNum);
        if (name.equals("false") || name.equals("true")){
            Constant c = new Constant(lNum);
            c.constant = new NumberLiteral(lNum);
            if (name.equals("true")){
                c.constant.constVal = 1;
            }else{
                c.constant.constVal = 0;
            }
            constant = c;
        }
    }

    @Override public String identify() {
        if (isInLibrary()){
            return "<const decl> " + name + " in the library";
        }else {
            return "<const decl> " + name + " on line " + lineNum;
        }

    }

    @Override
    public void genCode(CodeFile f) {

    }

    @Override
    public void check(Block curScope, Library lib) {
        constant.check(curScope, lib);
        type = constant.type;
        curScope.addDecl(name,this);
    }

    @Override void prettyPrint() {
        Main.log.prettyPrint(name + " = ");
        constant.prettyPrint();
        Main.log.prettyPrint("; ");
    }

    static ConstDecl parse(Scanner scanner) {
        enterParser("const decl");

        ConstDecl constDecl = new ConstDecl(scanner.curToken.id, scanner.curLineNum());
        scanner.skip(nameToken);
        scanner.skip(equalToken);
        constDecl.constant = Constant.parse(scanner);
        scanner.skip(semicolonToken);

        leaveParser("const decl");
        return constDecl;
    }

    @Override
    void checkWhetherAssignable(PascalSyntax where) {
        where.error("Cannot assign to a constant");
    }

    @Override
    void checkWhetherFunction(PascalSyntax where) {
        where.error(where.identify() + " is not a function");
    }

    @Override
    void checkWhetherProcedure(PascalSyntax where) {
        where.error(where.identify() + " is not a procedure");
    }

    @Override
    void checkWhetherValue(PascalSyntax where) {

    }
}