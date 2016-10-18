package parser;

import java.util.ArrayList;

import scanner.Scanner;

public class Term extends PascalSyntax {
    Factor factor;
    FactorOperator factorOperator;
    ArrayList<Factor> factorList = new ArrayList<>();
    ArrayList<FactorOperator> faopList = new ArrayList<>();

    Term(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Term> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        factorList.get(0).prettyPrint();
        if (factorOperator != null) {
            for (int i = 0; i < faopList.size(); i++) {
                faopList.get(i).prettyPrint();
                factorList.get(i + 1).prettyPrint();
            }
        }
    }

    static Term parse(Scanner s) {
        enterParser("term");
        Term t = new Term(s.curLineNum());

        t.factor = Factor.parse(s);
        t.factorList.add(t.factor);
        while (s.curToken.kind.isFactorOpr()) {
            t.factorOperator = FactorOperator.parse(s);
            t.faopList.add(t.factorOperator);
            t.factor = Factor.parse(s);
            t.factorList.add(t.factor);
        }

        leaveParser("term");
        return t;
    }
}