package parser;

import main.*;

public abstract class PascalSyntax {
    public int lineNum;

    public PascalSyntax(int n) {
        lineNum = n;
    }

    boolean isInLibrary() {
        return lineNum < 0;
    }

    public void error(String message) {
        Main.error("Error at line " + lineNum + ": " + message);
    }

    static void enterParser(String nonTerm) {
        Main.log.enterParser(nonTerm);
    }

    static void leaveParser(String nonTerm) {
        Main.log.leaveParser(nonTerm);
    }

    abstract public void check(Block curScope, Library lib);

    // del 2
    abstract void prettyPrint();
    // Del 3: abstract void check(Block curScope, Library lib);
    // Del 4: abstract void genCode(CodeFile factor);
    abstract public String identify();
}
