package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class TypeDecl extends PascalDecl {

    TypeDecl(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    public void genCode(CodeFile f) {

    }

    @Override
    public void check(Block curScope, Library lib) {

    }

    @Override
    void prettyPrint() {

    }

    @Override
    public String identify() {
        if (isInLibrary()){
            return "<type decl> "+name+" in the library";
        }else {
            return null;
        }
    }

    @Override
    void checkWhetherAssignable(PascalSyntax where) {
        where.error("Cannot assign to a type");
    }

    @Override
    void checkWhetherFunction(PascalSyntax where) {
        where.error("Type is not a function");
    }

    @Override
    void checkWhetherProcedure(PascalSyntax where) {
        where.error("Type is not a procedure");
    }

    @Override
    void checkWhetherValue(PascalSyntax where) {
        where.error("Type is not a value");
    }
}