package parser;

import scanner.Scanner;
import static scanner.TokenKind.*;

/**
 * Created by filos on 17/10/2016.
 */
public abstract class Type extends PascalSyntax{
    public Type(int n) {
        super(n);
    }

    public static Type parse(Scanner scanner){
        enterParser("type");
        Type t = null;
        if (scanner.curToken.kind == arrayToken){
            t = ArrayType.parse(scanner);
        }else {
            t = TypeName.parse(scanner);
        }
        leaveParser("type");
        return t;
    }
}
