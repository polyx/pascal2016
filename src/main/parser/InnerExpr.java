package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class InnerExpr extends Factor {
    Expression expr;

    InnerExpr(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<InnerExpr> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint("(");
        expr.prettyPrint();
        Main.log.prettyPrint(")");
    }

    static InnerExpr parse(Scanner s) {
        enterParser("inner-expression");
        InnerExpr innerExpr= new InnerExpr(s.curLineNum());

        s.skip(leftParToken);
        innerExpr.expr = Expression.parse(s);
        s.skip(rightParToken);

        leaveParser("inner-expression");
        return innerExpr;
    }
}