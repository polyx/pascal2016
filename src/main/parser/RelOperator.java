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
    public void check(Block curScope, Library lib) {
        //pass nothing to check
    }

    @Override
    public String identify() {
        return "<Rel Operator> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        Main.log.prettyPrint(" " + token + " ");
    }

    static RelOperator parse(Scanner scanner) {
        enterParser("rel opr");
        RelOperator relOperator = new RelOperator(scanner.curLineNum());

        relOperator.token = scanner.curToken.kind.toString();

        if (scanner.curToken.kind == equalToken) {
            scanner.skip(equalToken);
        } else if (scanner.curToken.kind == notEqualToken) {
            scanner.skip(notEqualToken);
        } else if (scanner.curToken.kind == greaterToken) {
            scanner.skip(greaterToken);
        } else if (scanner.curToken.kind == lessEqualToken) {
            scanner.skip(lessEqualToken);
        } else if (scanner.curToken.kind == lessToken) {
            scanner.skip(lessToken);
        } else if (scanner.curToken.kind == greaterEqualToken) {
            scanner.skip(greaterEqualToken);
        }

        leaveParser("rel opr");
        return relOperator;
    }
}