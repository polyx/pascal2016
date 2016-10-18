package parser;

import scanner.Scanner;

import static scanner.TokenKind.*;


public class ArrayType extends Type {
    Constant const1, const2;
    Type type;
    public ArrayType(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {

    }

    @Override
    public String identify() {
        return null;
    }

    public static ArrayType parse(Scanner scanner){
        enterParser("array-type");
        ArrayType arrType = new ArrayType(scanner.curLineNum());
        scanner.skip(arrayToken);
        scanner.skip(leftBracketToken);
        arrType.const1 = Constant.parse(scanner);
        scanner.skip(rangeToken);
        arrType.const2 = Constant.parse(scanner);
        scanner.skip(rightBracketToken);
        scanner.skip(ofToken);
        arrType.type = Type.parse(scanner);

        leaveParser("array-type");
        return new ArrayType(scanner.curLineNum());
    }
}
