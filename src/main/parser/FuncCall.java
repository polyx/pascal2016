package parser;

import java.util.ArrayList;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class FuncCall extends Factor {
    String name;
    ArrayList<Expression> exprList;

    FuncCall(int lNum) {
        super(lNum);
        this.exprList = new ArrayList<>();
    }

    @Override
    public String identify() {
        return "<FuncCall> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(name);
        Main.log.prettyPrint("(");
        if (exprList.size() > 0){ // log params if any otherwise will just print "() "
            for (Expression e : exprList) {
                e.prettyPrint();
                Main.log.prettyPrint(", ");
            }
        }
        Main.log.prettyPrint(")");

    }

    public static FuncCall parse(Scanner scanner) {
        enterParser("func call");
        FuncCall func = new FuncCall(scanner.curLineNum());

        func.name = scanner.curToken.id;
        scanner.skip(nameToken);

        if (scanner.curToken.kind == leftParToken) {
            scanner.skip(leftParToken);
            func.exprList.add(Expression.parse(scanner));
            while (true) {
                if (!(scanner.curToken.kind == commaToken)) {
                    break;
                }
                scanner.skip(commaToken);
                func.exprList.add(Expression.parse(scanner));
            }
            scanner.skip(rightParToken);
        }

        leaveParser("func call");
        return func;
    }
}