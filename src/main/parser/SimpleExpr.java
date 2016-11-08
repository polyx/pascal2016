package parser;

import java.util.ArrayList;

import scanner.Scanner;

public class SimpleExpr extends PascalSyntax {
    PrefixOperator prefixOper;
    ArrayList<Term> termList = new ArrayList<>();
    ArrayList<TermOperator> termOperList = new ArrayList<>();

    SimpleExpr(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Simple Expression> on line " + lineNum;
    }

    @Override
    public void check(Block curScope, Library lib) {
        termList.forEach(term -> term.check(curScope, lib));
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