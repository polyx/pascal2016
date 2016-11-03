package parser;

import java.util.ArrayList;

import scanner.Scanner;

public class Term extends PascalSyntax {
    ArrayList<Factor> factorList = new ArrayList<>();
    ArrayList<FactorOperator> factorOperators = new ArrayList<>();

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
        if (factorOperators.size() > 0) {
            for (int i = 0; i < factorOperators.size(); i++) {
                factorOperators.get(i).prettyPrint();
                factorList.get(i + 1).prettyPrint(); // factors are 1 ahead of operators
            }
        }
    }

    static Term parse(Scanner scanner) {
        enterParser("term");
        Term term = new Term(scanner.curLineNum());

        term.factorList.add(Factor.parse(scanner));
        while (scanner.curToken.kind.isFactorOpr()) {
            term.factorOperators.add(FactorOperator.parse(scanner));
            term.factorList.add(Factor.parse(scanner));
        }

        leaveParser("term");
        return term;
    }
}