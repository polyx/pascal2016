package parser;

import java.util.ArrayList;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class FuncCall extends Factor {
    String name;
    ArrayList<Expression> exprList;
    FuncDecl funcRef;

    @Override
    public void genCode(CodeFile f) {
        int functionBlock = funcRef.declLevel;

        for (int i = exprList.size()-1; i >= 0; i--) {
            exprList.get(i).genCode(f);
            f.genInstr("", "pushl", "%eax", "");
        }
        f.genInstr("", "call", "func$" + funcRef.labelName, "");
        f.genInstr("", "addl", "$8,%esp", "");
    }

    FuncCall(int lNum) {
        super(lNum);
        this.exprList = new ArrayList<>();
    }

    @Override
    public void check(Block curScope, Library lib) {
        PascalDecl declRef = curScope.findDecl(name.toLowerCase(), this);
        funcRef = (FuncDecl) declRef;
        declRef.checkWhetherFunction(this);
        if (exprList.size() != funcRef.paramList.paramDecls.size()){
            error("argument number mismatch.\nExpected: "+ funcRef.paramList.paramDecls.size()
            +"\nGot: " + exprList.size());
        }
        int counter = 0;
        for (Expression expr : exprList) {
            expr.check(curScope, lib);
            expr.type.checkType(funcRef.paramList.paramDecls.get(counter).type,
                    "param " + (counter+1), this,
                    "Illigal argument type of arg #"+ (counter+1));
            this.type = funcRef.type;
            counter++;
        }
    }

    @Override
    public String identify() {
        return "<FuncCall> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(name);
        int counter = exprList.size();
        if (exprList.size() > 0){
            Main.log.prettyPrint("(");
            for (Expression e : exprList) {
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

    public static FuncCall parse(Scanner scanner) {
        enterParser("func call");
        FuncCall func = new FuncCall(scanner.curLineNum());

        func.name = scanner.curToken.id;
        scanner.skip(nameToken);

        if (scanner.curToken.kind == leftParToken) {
            scanner.skip(leftParToken);
            func.exprList.add(Expression.parse(scanner));
            while (true) {
                if (!(scanner.curToken.kind == commaToken)) {
                    break;
                }
                scanner.skip(commaToken);
                func.exprList.add(Expression.parse(scanner));
            }
            scanner.skip(rightParToken);
        }

        leaveParser("func call");
        return func;
    }
}