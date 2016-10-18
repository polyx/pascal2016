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

    static Negation parse(Scanner s) {
        enterParser("negation");
        Negation n = new Negation(s.curLineNum());

        s.skip(notToken);
        n.fact = Factor.parse(s);

        leaveParser("negation");
        return n;
    }
}