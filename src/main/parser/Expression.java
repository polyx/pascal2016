package parser;


import main.CodeFile;
import scanner.Scanner;


class Expression extends PascalSyntax {
    SimpleExpr leftSimpleExpr;
    SimpleExpr rightSimpleExpr;
    RelOperator relOperator;
    types.Type type;

    @Override
    public void genCode(CodeFile f) {
        leftSimpleExpr.genCode(f);
        if(relOperator != null) {
            f.genInstr("", "pushl", "%eax");
            rightSimpleExpr.genCode(f);
            if (relOperator.token.equals("=")) {
                f.genInstr("", "popl", "%ecx");
                f.genInstr("", "cmpl", "%eax,%ecx");
                f.genInstr("", "movl", "$0,%eax");
                f.genInstr("", "sete", "%al");
            } else if (relOperator.token.equals(">")) {
                f.genInstr("", "popl", "%ecx");
                f.genInstr("", "cmpl", "%eax,%ecx");
                f.genInstr("", "movl", "$0,%eax");
                f.genInstr("", "setg", "%al");
            } else if (relOperator.token.equals("<=")) {
                f.genInstr("", "popl", "%ecx");
                f.genInstr("", "cmpl", "%eax,%ecx");
                f.genInstr("", "movl", "$0,%eax");
                f.genInstr("", "setle", "%al", "test <=");
            } else if (relOperator.token.equals("<>")) {
                f.genInstr("", "popl", "%ecx");
                f.genInstr("", "cmpl", "%eax,%ecx");
                f.genInstr("", "movl", "$0,%eax");
                f.genInstr("", "setne", "%al", "test <>");
            } else if (relOperator.token.equals(">=")) {
                f.genInstr("", "popl", "%ecx");
                f.genInstr("", "cmpl", "%eax,%ecx");
                f.genInstr("", "movl", "$0,%eax");
                f.genInstr("", "setge", "%al", "test >=");
            } else if (relOperator.token.equals("<")) {
                f.genInstr("", "popl", "%ecx");
                f.genInstr("", "cmpl", "%eax,%ecx");
                f.genInstr("", "movl", "$0,%eax");
                f.genInstr("", "setl", "%al", "test <");
            }
        }
    }

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