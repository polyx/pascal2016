package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class Negation extends Factor {
    Factor fact;
    Negation(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Negation> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint("not ");
        fact.prettyPrint();
    }

    static Negation parse(Scanner scanner) {
        enterParser("negation");
        Negation negation = new Negation(scanner.curLineNum());

        scanner.skip(notToken);
        negation.fact = Factor.parse(scanner);

        leaveParser("negation");
        return negation;
    }
}