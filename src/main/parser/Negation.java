package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class Negation extends Factor {
    Factor fact;

    @Override
    public void genCode(CodeFile f) {

    }

    Negation(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        fact.check(curScope, lib);
        type = fact.type;
        fact.type.checkType(lib.booleanType, "'not' operand", this, "cannot apply 'not' to a non boolean factor");
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