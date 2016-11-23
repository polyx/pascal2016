package parser;

import java.util.ArrayList;

import main.CodeFile;
import scanner.Scanner;
import types.*;
import types.Type;

public class SimpleExpr extends PascalSyntax {
    PrefixOperator prefixOper;
    ArrayList<Term> termList = new ArrayList<>();
    ArrayList<TermOperator> termOperList = new ArrayList<>();
    types.Type type;

    @Override
    public void genCode(CodeFile f) {

    }

    SimpleExpr(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Simple Expression> on line " + lineNum;
    }

    @Override
    public void check(Block curScope, Library lib) {
        for (int i = 0; i < termList.size(); i++) {
            Term term = termList.get(i);
            term.check(curScope, lib);
            type = term.type;
            if (i >= 1) {
                int counter = i - 1;
                if (termOperList.size() > 0) {
                    if (termOperList.get(counter).name.equals("+") ||
                            termOperList.get(counter).name.equals("-")) {
                        termList.get(i - 1).type.checkType(lib.intType,
                                "left " + termOperList.get(counter).name + " operand", this,
                                "left operand to " + termOperList.get(counter).name + " is not a number");
                        term.type.checkType(lib.intType,
                                "right " + termOperList.get(counter).name + " operand", this,
                                "right operand to " + termOperList.get(counter).name + " is not a number");
                    }else if (termOperList.get(counter).name.equals("or")){
                        termList.get(i - 1).type.checkType(lib.booleanType,
                                "left or operand", this,
                                "left operand to or is not a boolean");
                        term.type.checkType(lib.booleanType,
                                "right or operand", this,
                                "right operand to or is not a boolean");
                    }
                }
            }
        }
        if (prefixOper != null){
            termList.get(0).type.checkType(lib.intType, "prefix " + prefixOper.name + " operand", this, "cannont apply " + prefixOper.name + "to term of type " + termList.get(0).type);
        }
    }

    @Override
    public void prettyPrint() {
        if (prefixOper != null) prefixOper.prettyPrint();
        termList.get(0).prettyPrint();
            for (int i = 0; i < termOperList.size(); i++) {
                termOperList.get(i).prettyPrint();
                termList.get(i + 1).prettyPrint();
            }
    }

    static SimpleExpr parse(Scanner scanner) {
        enterParser("simple expr");

        SimpleExpr se = new SimpleExpr(scanner.curLineNum());

        if (scanner.curToken.kind.isPrefixOpr()) {
            se.prefixOper = PrefixOperator.parse(scanner);
        }

        se.termList.add(Term.parse(scanner));
        while (scanner.curToken.kind.isTermOpr()) {
            se.termOperList.add(TermOperator.parse(scanner));
            se.termList.add(Term.parse(scanner));
        }

        leaveParser("simple expr");
        return se;
    }
}