package parser;

import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Variable extends Factor {
    String name;
    Expression expr;
    PascalDecl pascDecl;
    ConstDecl constDecl;

    @Override
    public void genCode(CodeFile f) {
        int varBlockLevel = -4 * pascDecl.declLevel;
        int varOffset = 0;
        if (pascDecl instanceof VarDecl){
            varOffset =  -pascDecl.declOffset - 32;
        }else if(pascDecl instanceof ParamDecl){
//            varBlockLevel = varBlockLevel - 8;
            varOffset = pascDecl.declOffset;
        }

        if(constDecl != null) {
            if(constDecl.constant != null){
                constDecl.constant.genCode(f);
            } else if(constDecl.name.equalsIgnoreCase("eol")) {
                //according to kompendiet eol is used only in write
                f.genInstr("", "movl", "$" + 10 + ",%eax", "eol char");
                f.genInstr("", "pushl", "%eax", "");
                f.genInstr("", "call", "write_char", "");
                f.genInstr("", "addl", "$4,%esp", "");
            }
        } else {
            f.genInstr("", "movl", varBlockLevel + "(%ebp),%edx", "");
            f.genInstr("", "movl", varOffset + "(%edx),%eax", name);
        }
    }

    private Variable(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        pascDecl = curScope.findDecl(name.toLowerCase(), this);
        type = pascDecl.type;
        //we need reference for further code generation
        if (pascDecl instanceof ConstDecl){
            constDecl = (ConstDecl) pascDecl;
        }
        if (pascDecl instanceof ConstDecl && pascDecl.isInLibrary()){
            if (pascDecl.name.equals("true") || pascDecl.name.equals("false")) {
                type = lib.booleanType;
            }
        }

        if(expr != null) {
            expr.check(curScope, lib);
            //checking that index is an int
            expr.type.checkType(lib.intType, "array index", this, "variable is of " + type.identify());

        }
    }

    @Override public String identify() {
        return "<Variable> on line " + lineNum;
    }

    @Override public void prettyPrint() {
        Main.log.prettyPrint(name);

        if (expr == null) {
            return;
        }
        Main.log.prettyPrint("[");
        expr.prettyPrint();
        Main.log.prettyPrint("]");
    }

    static Variable parse(Scanner scanner) {
        enterParser("variable");

        Variable var = new Variable(scanner.curLineNum());
        var.name = scanner.curToken.id;
        scanner.skip(nameToken);
        //check if array reference
        if(scanner.curToken.kind == leftBracketToken) {
            scanner.skip(leftBracketToken);
            var.expr = Expression.parse(scanner);
            scanner.skip(rightBracketToken);
        }

        leaveParser("variable");
        return var;
    }
}