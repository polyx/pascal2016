package parser;

import main.CodeFile;
import scanner.Scanner;

import static scanner.TokenKind.*;
import static main.Main.log;

public class ArrayType extends Type {
    Constant const1, const2;
    Type typeName;

    @Override
    public void genCode(CodeFile f) {

    }

    public ArrayType(int n) {
        super(n);
    }

    @Override
    public void check(Block curScope, Library lib) {
        const1.check(curScope, lib);
        const2.check(curScope, lib);
        typeName.check(curScope, lib);
        type = typeName.type;
        const1.type.checkType(const2.type, "array limits", this, "type mismatch in array limits");
    }

    @Override
    void prettyPrint() {
        log.prettyPrint("array[");
        const1.prettyPrint();
        log.prettyPrint("..");
        const2.prettyPrint();
        log.prettyPrint("] of ");
        typeName.prettyPrint();
    }

    @Override
    public String identify() {
        return null;
    }

    public static ArrayType parse(Scanner scanner){
        enterParser("array-typeName");
        ArrayType arrType = new ArrayType(scanner.curLineNum());
        scanner.skip(arrayToken);
        scanner.skip(leftBracketToken);
        arrType.const1 = Constant.parse(scanner);
        scanner.skip(rangeToken);
        arrType.const2 = Constant.parse(scanner);
        scanner.skip(rightBracketToken);
        scanner.skip(ofToken);
        arrType.typeName = Type.parse(scanner);

        leaveParser("array-typeName");
        return arrType;
    }
}
