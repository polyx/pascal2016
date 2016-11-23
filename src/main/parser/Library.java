package parser;

import main.CodeFile;
import types.*;

public class Library extends Block {
    int lnum;

    BoolType booleanType = new BoolType();
    IntType intType = new IntType();
    CharType charType = new CharType();

    Block outerScrope = null;

    public void genCode(CodeFile f){

    }

    public Library(int lnum) {
        super(lnum);
        decls.put("write", new ProcDecl("write", lnum));

        decls.put("integer", new TypeDecl("integer", lnum));
        decls.put("boolean", new TypeDecl("boolean", lnum));
        decls.put("char", new TypeDecl("char", lnum));

        decls.put("eol", new ConstDecl("eol", lnum));
        decls.put("true", new ConstDecl("true", lnum));
        decls.put("false", new ConstDecl("false", lnum));

        decls.put("write", new ProcDecl("write", lnum));
    }

    @Override
    public String identify() {
        return "in the library";
    }
}