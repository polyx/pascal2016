package parser;

import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Variable extends Factor {
    String name;
    private Expression expr;

    private Variable(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        PascalDecl pascDecl = curScope.findDecl(name, this);

        if(expr != null) {
            expr.check(curScope, lib);
        }
    }

    @Override public String identify() {
        return "<Variable> on line " + lineNum;
    }

    @Override public void prettyPrint() {
        Main.log.prettyPrint(name);

        if (expr == null) {
            return;
        }
        Main.log.prettyPrint("[");
        expr.prettyPrint();
        Main.log.prettyPrint("]");
    }

    static Variable parse(Scanner scanner) {
        enterParser("variable");

        Variable var = new Variable(scanner.curLineNum());
        var.name = scanner.curToken.id;
        scanner.skip(nameToken);
        //check if array reference
        if(scanner.curToken.kind == leftBracketToken) {
            scanner.skip(leftBracketToken);
            var.expr = Expression.parse(scanner);
            scanner.skip(rightBracketToken);
        }

        leaveParser("variable");
        return var;
    }
}