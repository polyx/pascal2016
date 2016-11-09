package parser;

import scanner.Scanner;
import scanner.TokenKind;

import static scanner.TokenKind.*;

abstract class Factor extends PascalSyntax {
    static Factor factor;
    types.Type type;
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

    static Factor parse(Scanner scanner) {
        enterParser("factor");
        TokenKind tokenKind = scanner.curToken.kind;

        //either func call or variable reference reference
        if(tokenKind == nameToken){
            if (scanner.nextToken.kind == leftBracketToken){ // array reference
                factor = Variable.parse(scanner);
            }else if (scanner.nextToken.kind == leftParToken){ // func call
                factor = FuncCall.parse(scanner);
            }else {
                factor = Variable.parse(scanner);
            }
        // unsigned constant, ignoring named token and treating name tokens as variable above;
        // TODO: fix in part 3 as per piazza comment from Dag.
        }else if (tokenKind == intValToken || tokenKind == charValToken){
            factor = UnsignedConstant.parse(scanner);
        }else if(tokenKind == leftParToken){ //inner condition
            factor = InnerExpr.parse(scanner);
        }else if(tokenKind == notToken){ //negation
            factor = Negation.parse(scanner);
        }else{
            //give error if didnt find any of the above saying that we expect factor
            scanner.testError("factor");
        }

        leaveParser("factor");
        return factor;
    }
}