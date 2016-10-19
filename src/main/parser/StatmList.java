package parser;

import main.Main;
import scanner.Scanner;

import java.util.ArrayList;

import static scanner.TokenKind.*;

public class StatmList extends PascalSyntax {
    ArrayList<Statement> statmList = new ArrayList<>();

    public StatmList(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        statmList.forEach(Statement::prettyPrint);
    }

    @Override
    public String identify() {
        return null;
    }

    public static StatmList parse(Scanner scanner) {
        enterParser("statm list");

        StatmList sl = new StatmList(scanner.curLineNum());

        sl.statmList.add(Statement.parse(scanner));

        while (scanner.curToken.kind == semicolonToken) {
            scanner.skip(semicolonToken);
            sl.statmList.add(Statement.parse(scanner));
        }

        leaveParser("statm list");
        return sl;
    }
}
