package parser;

import main.Main;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class TypeName extends Type {
    String name;
    public TypeName(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        Main.log.prettyPrint(name);
    }

    @Override
    public String identify() {
        return null;
    }

    public static TypeName parse(Scanner scanner){
        enterParser("type name");
        TypeName typeName = new TypeName(scanner.curLineNum());
        typeName.name = scanner.curToken.id;
        scanner.skip(nameToken);
        leaveParser("type name");
        return typeName;
    }
}
