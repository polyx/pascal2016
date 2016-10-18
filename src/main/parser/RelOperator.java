package parser;

import java.util.ArrayList;

import main.*;
import scanner.*;

import static scanner.TokenKind.*;

public class RelOperator extends Operator {
    String token;

    RelOperator(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Rel Operator> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(" " + token + " ");
    }

    static RelOperator parse(Scanner s) {
        enterParser("rel-operator");
        RelOperator ro = new RelOperator(s.curLineNum());

        ro.token = s.curToken.kind.toString();
        switch (s.curToken.kind) {
            case equalToken:
                s.skip(equalToken);
                break;
            case notEqualToken:
                s.skip(notEqualToken);
                break;
            case greaterToken:
                s.skip(greaterToken);
                break;
            case lessEqualToken:
                s.skip(lessEqualToken);
                break;
            case lessToken:
                s.skip(lessToken);
                break;
            case greaterEqualToken:
                s.skip(greaterEqualToken);
                break;
        }

        leaveParser("rel-operator");
        return ro;
    }
}