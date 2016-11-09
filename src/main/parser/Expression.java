package parser;


import scanner.Scanner;


class Expression extends PascalSyntax {
    SimpleExpr leftSimpleExpr;
    SimpleExpr rightSimpleExpr;
    RelOperator relOperator;
    types.Type type;

    private Expression(int lNum) {
        super(lNum);
    }

    @Override
    public void check(Block curScope, Library lib) {
        leftSimpleExpr.check(curScope, lib);

        type = leftSimpleExpr.type;
        if(relOperator != null) {
            rightSimpleExpr.check(curScope, lib);
            String operatorName = relOperator.token;
            type.checkType(rightSimpleExpr.type, operatorName+" operands", this,
                    "Operands to "+operatorName+" are of different typeName!");
            type = lib.booleanType;
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