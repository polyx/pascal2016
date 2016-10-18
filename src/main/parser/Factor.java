package parser;

import scanner.Scanner;
import scanner.TokenKind;

import static scanner.TokenKind.*;

abstract class Factor extends PascalSyntax {
    static Factor factor;

    Factor(int lNum) {
        super(lNum);
    }

    @Override
    public String identify() {
        return "<Factor> on line " + lineNum;
    }

    @Override
    public void prettyPrint() {
        if (factor != null) factor.prettyPrint();
    }

    static Factor parse(Scanner s) {
        enterParser("factor");
        TokenKind tokenKind = s.curToken.kind;

        //either func call or variable reference reference
        if(tokenKind == nameToken){
            if (s.nextToken.kind == leftBracketToken){ // array reference
                factor = Variable.parse(s);
            }else if (s.nextToken.kind == leftParToken){ // func call
                factor = FuncCall.parse(s);
            }else {
                factor = Variable.parse(s);
            }
        // unsigned constant, ignoring named token and treating name tokens as variable above;
        // TODO: fix in part 3 as per piazza comment from Dag.
        }else if (tokenKind == intValToken || tokenKind == charValToken){
            factor = UnsignedConstant.parse(s);
        }else if(tokenKind == leftParToken){ //inner condition
            factor = InnerExpr.parse(s);
        }else if(tokenKind == notToken){ //negation
            factor = Negation.parse(s);
        }else{
            //give error if didnt find any of the above saying that we expect factor
            s.testError("factor");
        }

        leaveParser("factor");
        return factor;
    }
}