package parser;

import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;

public class TypeName extends Type {
    String name;
    types.Type type;

    public TypeName(int n) {
        super(n);
    }

    @Override
    public void check(Block curScope, Library lib) {
        curScope.findDecl(name, this);
        switch (name.toLowerCase()) {
            case "integer":
                type = Main.library.intType;
                break;
            case "char":
                type = Main.library.charType;
                break;
            case "boolean":
                type = Main.library.booleanType;
                break;
        }
    }

    @Override
    void prettyPrint() {
        Main.log.prettyPrint(name);
    }

    @Override
    public String identify() {
        return "<typeName " + name + "> on line" + lineNum;
    }

    public static TypeName parse(Scanner scanner) {
        enterParser("typeName name");

        TypeName typeName = new TypeName(scanner.curLineNum());
        typeName.name = scanner.curToken.id;

        scanner.skip(nameToken);

        leaveParser("typeName name");
        return typeName;
    }
}
