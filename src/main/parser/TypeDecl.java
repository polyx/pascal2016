package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class TypeDecl extends PascalDecl {

    TypeDecl(String id, int lNum) {
        super(id, lNum);
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
            return "integer in library";
        }else {
            return null;
        }
    }

    @Override
    void checkWhetherAssignable(PascalSyntax where) {

    }

    @Override
    void checkWhetherFunction(PascalSyntax where) {

    }

    @Override
    void checkWhetherProcedure(PascalSyntax where) {

    }

    @Override
    void checkWhetherValue(PascalSyntax where) {

    }
}