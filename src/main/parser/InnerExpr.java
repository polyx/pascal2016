package parser;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class InnerExpr extends Factor {
    Expression expr;

    @Override
    public void genCode(CodeFile f) {
        expr.genCode(f);
    }

    InnerExpr(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        expr.check(curScope, lib);
        type = expr.type;
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

    static InnerExpr parse(Scanner scanner) {
        enterParser("inner expr");
        InnerExpr innerExpr= new InnerExpr(scanner.curLineNum());

        scanner.skip(leftParToken);
        innerExpr.expr = Expression.parse(scanner);
        scanner.skip(rightParToken);

        leaveParser("inner expr");
        return innerExpr;
    }
}