package parser;


import scanner.Scanner;


class Expression extends PascalSyntax {
    private SimpleExpr leftSimpleExpr;
    private SimpleExpr rightSimpleExpr;
    private RelOperator relOperator;

    private Expression(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        leftSimpleExpr.check(curScope, lib);
        if(relOperator != null) {
            relOperator.check(curScope, lib);
            rightSimpleExpr.check(curScope, lib);
        }
    }
    @Override
    public String identify() {
        return "<Expression> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        leftSimpleExpr.prettyPrint();
        if (relOperator != null) {
            relOperator.prettyPrint();
            rightSimpleExpr.prettyPrint();
        }
    }

    static Expression parse(Scanner scanner) {
        enterParser("expression");

        Expression expr = new Expression(scanner.curLineNum());
        expr.leftSimpleExpr = SimpleExpr.parse(scanner);
        if (scanner.curToken.kind.isRelOpr()) {
            expr.relOperator = RelOperator.parse(scanner);
            expr.rightSimpleExpr = SimpleExpr.parse(scanner);
        }

        leaveParser("expression");
        return expr;
    }
}