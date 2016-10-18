package parser;


import scanner.Scanner;


class Expression extends PascalSyntax {
    SimpleExpr simpleExpr;
    SimpleExpr simpleExpr2;
    RelOperator relOperator;

    Expression(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Expression> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        simpleExpr.prettyPrint();
        if (relOperator != null) {
            relOperator.prettyPrint();
            simpleExpr2.prettyPrint();
        }
    }

    static Expression parse(Scanner s) {
        enterParser("expression");

        Expression expr = new Expression(s.curLineNum());
        expr.simpleExpr = SimpleExpr.parse(s);
        if (s.curToken.kind.isRelOpr()) {
            expr.relOperator = RelOperator.parse(s);
            expr.simpleExpr2 = SimpleExpr.parse(s);
        }

        leaveParser("expression");
        return expr;
    }
}