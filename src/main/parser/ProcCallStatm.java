package parser;

import main.CodeFile;
import main.Main;
import scanner.Scanner;

import java.util.ArrayList;

import static scanner.TokenKind.*;


public class ProcCallStatm extends Statement {
    String funcName;
    ArrayList<Expression> params = new ArrayList<>();

    @Override
    public void genCode(CodeFile f) {
        if(funcName.equals("write")) {
            for (Expression param : params) {
                param.genCode(f);
                if (param.leftSimpleExpr.termList.get(0).factorList.get(0) instanceof NumberLiteral) {
                    f.genInstr("", "pushl", "%eax", "");
                    f.genInstr("", "call", "write_int", "");
                    f.genInstr("", "addl", "$4,%esp", "");
                }
                if (param.leftSimpleExpr.termList.get(0).factorList.get(0) instanceof CharLiteral) {
                    CharLiteral ch = (CharLiteral) param.leftSimpleExpr.termList.get(0).factorList.get(0);
                    int chASCIIValue = (int) ch.charVal.charAt(0);
                    String constant = "$" + chASCIIValue;
                    f.genInstr("", "movl", constant + ",%eax", "");
                    f.genInstr("", "pushl", "%eax", "");
                    f.genInstr("", "call", "write_char", "");
                    f.genInstr("", "addl", "$4,%esp", "");
                }
//                if(params.get(i).leftSimpleExpr.termList.get(0).factorList.get(0) instanceof Variable) {
//
//                }
            }
        } else {
//            int fc_block = procRef.declLevel;
//            for (int i = params.size()-1; i >= 0; i--) {
//                params.get(i).genCode(f);
//                f.genInstr("", "pushl", "%eax", "");
//            }
//            f.genInstr("", "call", "proc$" + funcName + "_" + fc_block, "");
//            f.genInstr("", "addl", "$8,%esp", "");
        }
    }

    public ProcCallStatm(int n) {
        super(n);
    }

    @Override
    public String identify() {
        return funcName;
    }

    @Override
    public void prettyPrint() {

        Main.log.prettyPrint(funcName);
        int counter = params.size();
        if (params.size() != 0) {
            Main.log.prettyPrint("(");
            for (Expression e : params) {
                e.prettyPrint();
                //dont put comma after last arg :)
                if (--counter == 0) {
                    break;
                }
                Main.log.prettyPrint(", ");
            }
            Main.log.prettyPrint(")");
        }
    }

    @Override
    public void check(Block curScope, Library lib) {
        PascalDecl d = curScope.findDecl(funcName.toLowerCase(), this);
        ProcDecl pd = (ProcDecl) d;
        d.checkWhetherProcedure(this);
        for (Expression param : params) {
            param.check(curScope, lib);
        }
        if (!pd.name.equals("write")){
            if (params.size() != 0 && pd.paramList.paramDecls != null){
                if (params.size() != pd.paramList.paramDecls.size()){
                    error("argument number mismatch.\nExpected: "+ pd.paramList.paramDecls.size()
                            +"\nGot: " + params.size());
                }
            }
            int counter = 0;
            for (Expression expr : params) {
//                expr.check(curScope, lib);
                expr.type.checkType(pd.paramList.paramDecls.get(counter).type,
                        "param #" + (counter+1), this,
                        "Illigal argument type of arg #"+ (counter+1));
                counter++;
            }
        }
    }


    static ProcCallStatm parse(Scanner s) {
        enterParser("proc call");

        ProcCallStatm pc = new ProcCallStatm(s.curLineNum());
        pc.funcName = s.curToken.id;
        s.skip(nameToken);

        if (s.curToken.kind == leftParToken) {
            s.skip(leftParToken);
            pc.params.add(Expression.parse(s));
            while (s.curToken.kind == commaToken) {
                s.skip(commaToken);
                pc.params.add(Expression.parse(s));
            }
            s.skip(rightParToken);
        }
        leaveParser("proc call");
        return pc;
    }
}
