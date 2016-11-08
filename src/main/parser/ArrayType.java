package parser;

import scanner.Scanner;

import static scanner.TokenKind.*;
import static main.Main.log;

public class ArrayType extends Type {
    Constant const1, const2;
    Type type;
    public ArrayType(int n) {
        super(n);
    }

    @Override
    public void check(Block curScope, Library lib) {
        const1.check(curScope, lib);
        const2.check(curScope, lib);
    }

    @Override
    void prettyPrint() {
        log.prettyPrint("array[");
        const1.prettyPrint();
        log.prettyPrint("..");
        const2.prettyPrint();
        log.prettyPrint("] of ");
        type.prettyPrint();
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
        return arrType;
    }
}
