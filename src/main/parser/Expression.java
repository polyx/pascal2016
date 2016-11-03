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

    static Expression parse(Scanner scanner) {
        enterParser("expression");

        Expression expr = new Expression(scanner.curLineNum());
        expr.simpleExpr = SimpleExpr.parse(scanner);
        if (scanner.curToken.kind.isRelOpr()) {
            expr.relOperator = RelOperator.parse(scanner);
            expr.simpleExpr2 = SimpleExpr.parse(scanner);
        }

        leaveParser("expression");
        return expr;
    }
}