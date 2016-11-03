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
        int counter = statmList.size();
        for (Statement statm : statmList) {
            statm.prettyPrint();
            if (--counter != 0) {
                Main.log.prettyPrint(";");
            }
            Main.log.prettyPrintLn();
        }
    }

    @Override
    public String identify() {
        return null;
    }

    public static StatmList parse(Scanner scanner) {
        enterParser("statm list");

        StatmList sl = new StatmList(scanner.curLineNum());
        Statement statm = Statement.parse(scanner);
        sl.statmList.add(statm);
        while (scanner.curToken.kind == semicolonToken) {
            scanner.skip(semicolonToken);
            statm = Statement.parse(scanner);
            sl.statmList.add(statm);
        }

        leaveParser("statm list");
        return sl;
    }
}
