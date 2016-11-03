package parser;

import java.util.ArrayList;

import scanner.Scanner;

public class SimpleExpr extends PascalSyntax {
    PrefixOperator prefixOper;
    Term term;
    TermOperator termOper;
    ArrayList<Term> termList = new ArrayList<Term>();
    ArrayList<TermOperator> termOperList = new ArrayList<TermOperator>();

    SimpleExpr(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Simple Expression> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        if (prefixOper != null) prefixOper.prettyPrint();
        termList.get(0).prettyPrint();
        //if (termOper != null) {
            for (int i = 0; i < termOperList.size(); i++) {
                termOperList.get(i).prettyPrint();
                termList.get(i + 1).prettyPrint();
            }
        //}
    }

    static SimpleExpr parse(Scanner s) {
        enterParser("simple expr");

        SimpleExpr se = new SimpleExpr(s.curLineNum());

        if (s.curToken.kind.isPrefixOpr())
            se.prefixOper = PrefixOperator.parse(s);

        se.term = Term.parse(s);
        se.termList.add(se.term);
        while (s.curToken.kind.isTermOpr()) {
            se.termOper = TermOperator.parse(s);
            se.termOperList.add(se.termOper);
            se.term = Term.parse(s);
            se.termList.add(se.term);
        }
        leaveParser("simple expr");
        return se;
    }
}