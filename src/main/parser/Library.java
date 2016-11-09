package parser;

import types.*;

public class Library extends Block {
    int lnum;

    BoolType booleanType = new BoolType();
    IntType intType = new IntType();
    CharType charType = new CharType();

    Block outerScrope = null;

    public Library(int lnum) {
        super(lnum);
        ProcDecl writeProc = new ProcDecl("write", lnum);
        TypeDecl intType = new TypeDecl("integer", lnum);
        TypeDecl booleanType = new TypeDecl("boolean", lnum);
        TypeDecl charType = new TypeDecl("char", lnum);
        ConstDecl cd = new ConstDecl("eol", lnum);
        TypeDecl edt = new TypeDecl("true", lnum);
        TypeDecl edf = new TypeDecl("false", lnum);
        TypeDecl tn = new TypeDecl("char", lnum);
        decls.put("write", new ProcDecl("write", lnum));
        decls.put("integer", new TypeDecl("integer", lnum));
        decls.put("boolean", new TypeDecl("boolean", lnum));
        decls.put("char", new TypeDecl("char", lnum));
        decls.put("eol", new ConstDecl("eol", lnum));
        decls.put("true", new TypeDecl("true", lnum));
        decls.put("false", new TypeDecl("false", lnum));
        decls.put("write", new ProcDecl("write", lnum));
    }

    @Override
    public String identify() {
        return "in the library";
    }
}