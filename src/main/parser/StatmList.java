package parser;

import main.CodeFile;
import main.Main;
import scanner.Scanner;

import java.util.ArrayList;

import static scanner.TokenKind.*;

public class StatmList extends PascalSyntax {
    ArrayList<Statement> statmList;

    @Override
    public void genCode(CodeFile f) {

    }

    public StatmList(int n) {
        super(n);
        statmList = new ArrayList<>();
    }

    @Override
    public void check(Block curScope, Library lib) {
        statmList.forEach(statement -> statement.check(curScope, lib));
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
