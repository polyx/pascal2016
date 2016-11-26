package parser;

import java.util.ArrayList;

import main.CodeFile;
import scanner.Scanner;

public class Term extends PascalSyntax {
    ArrayList<Factor> factorList = new ArrayList<>();
    ArrayList<FactorOperator> factorOperators = new ArrayList<>();
    types.Type type;

    @Override
    public void genCode(CodeFile f) {
        factorList.get(0).genCode(f);
        for(int i = 0; i < factorOperators.size(); i++) {
            f.genInstr("", "pushl", "%eax");
            factorList.get(i+1).genCode(f);
            factorOperators.get(i).genCode(f);
        }
    }

    Term(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        int counter = 0;
        for (int i = 0; i < factorList.size(); i++) {
            Factor f = factorList.get(i);
            f.check(curScope, lib);
            type = f.type;
            if (i>=1){
                counter = i-1;
                if (factorOperators.size() > 0){
                    if (factorOperators.get(counter).id.equals("mod") ||
                        factorOperators.get(counter).id.equals("div")||
                        factorOperators.get(counter).id.equals("*")) {
                        //left hand side
                        factorList.get(i-1).type.checkType(lib.intType,
                                "left "+ factorOperators.get(counter).id +" operand", this,
                                "left operand to "+ factorOperators.get(counter).id +" is not a number");

                        //right hand side
                        f.type.checkType(lib.intType,
                                "right "+ factorOperators.get(counter).id + " operand", this,
                                "right operand to "+ factorOperators.get(counter).id +" is not a number");
                    }else if(factorOperators.get(counter).id.equals("and")){
                        //left hand side
                        factorList.get(i-1).type.checkType(lib.booleanType,
                                "left and operand", this,
                                "left operand to and is not a boolean");

                        //right hand side
                        f.type.checkType(lib.booleanType,
                                "right and operand", this,
                                "right operand to and is not a boolean");
                    }
                }
            }
        }
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