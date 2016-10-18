package parser;

import main.Main;
import scanner.Scanner;

import java.util.ArrayList;

import static scanner.TokenKind.*;

/**
 * Created by filos on 15/10/2016.
 */
public class StatmList extends PascalSyntax {
    ArrayList<Statement> statmList = new ArrayList<>();

    public StatmList(int n) {
        super(n);
    }

    @Override
    void prettyPrint() {
        for (Statement statm : statmList) {
            statm.prettyPrint();
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
