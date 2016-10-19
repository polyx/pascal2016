package parser;

import main.Main;
import scanner.Scanner;

import java.util.ArrayList;

import static scanner.TokenKind.*;


public class ProcCallStatm extends Statement {
    String funcName;
    ArrayList<Expression> params = new ArrayList<>();

    public ProcCallStatm(int n) {
        super(n);
    }

    @Override
    public String identify() {
        return null;
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
            Main.log.prettyPrint(");");
        } else {
            Main.log.prettyPrint("( );");
        }
        /*Main.log.prettyIndent();
        Main.log.prettyOutdent();*/
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
