package parser;

import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class FactorOperator extends Operator {
    String id;

    FactorOperator(int lNum) {
        super(lNum);
    }

    @Override public String identify() {
        return "<Factor Operator " + id + "> on line " + lineNum;
    }

    @Override public void prettyPrint() {
        if(id.equals("*"))
            Main.log.prettyPrint(id);
        else
            Main.log.prettyPrint(" " + id + " ");
    }

    static FactorOperator parse(Scanner s) {
        enterParser("factor-operator");
        FactorOperator operator = new FactorOperator(s.curLineNum());

        operator.id = s.curToken.kind.toString();
        switch(s.curToken.kind) {
            case multiplyToken: s.skip(multiplyToken); 	break;
            case divToken: s.skip(divToken);			break;
            case modToken: s.skip(modToken);			break;
            case andToken: s.skip(andToken);			break;
        }

        leaveParser("factor-operator");
        return operator;
    }
}