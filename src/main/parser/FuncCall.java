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

    public static FuncCall parse(Scanner s) {
        enterParser("function call");
        FuncCall func = new FuncCall(s.curLineNum());

        func.name = s.curToken.id;
        s.skip(nameToken);

        if (s.curToken.kind == leftParToken) {
            s.skip(leftParToken);
//            func.condition = Expression.parse(s);
            func.exprList.add(Expression.parse(s));

            while (s.curToken.kind == commaToken) {
                s.skip(commaToken);
//                func.condition = Expression.parse(s);
                func.exprList.add(Expression.parse(s));
            }
            s.skip(rightParToken);
        }

        leaveParser("function call");
        return func;
    }
}