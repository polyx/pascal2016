package parser;

import main.CodeFile;
import main.Main;
import scanner.Scanner;

import static scanner.TokenKind.*;


public class Program extends PascalDecl {
    Block pb;
    String pname;

    Program(String id, int lNum) {
        super(id, lNum);
    }

    @Override
    public String identify() {
        return "<Program on line >" + lineNum;
    }


    @Override
    public void genCode(CodeFile f) {
        int occbyte = 32 + pb.nextOffset;
        String labelName = f.getLabel(pname.toLowerCase());
        pname = pname.toLowerCase();

        f.genInstr("", ".extern write_char", "", "");
        f.genInstr("", ".extern write_int", "", "");
        f.genInstr("", ".extern write_String", "", "");
//        f.genInstr("", ".globl _main", "", "");
        f.genInstr("", ".globl main", "", "");
//        f.genInstr("_main", "", "", "");
        f.genInstr("main", "call", "prog$" + pname + "_" + pb.level, "Start program");
        f.genInstr("", "movl", "$0,%eax", "Set status 0 and");
        f.genInstr("", "ret", "", "terminate the program");
        pb.genCode(f);
        f.genInstr("prog$" + labelName, "enter", "$" + occbyte + "," + "$" + pb.level, "Start of " + pname);
        pb.statmList.genCode(f);
        f.genInstr("", "leave", "", "");
        f.genInstr("", "ret", "", "");
    }

    @Override
    public void check(Block curScope, Library lib) {
        pb.check(curScope, lib);
    }

    public void prettyPrint() {
        Main.log.prettyPrintLn("program " + pname + ";");
        pb.prettyPrint();
        Main.log.prettyPrint(".");
    }

    public static Program parse(Scanner scanner) {
        enterParser("program");

        scanner.skip(programToken);
//        System.out.println(scanner.nextToken.id.toLowerCase());
        Program p = new Program(scanner.curToken.id, scanner.curLineNum());
        p.pname = scanner.curToken.id;
        scanner.skip(nameToken);
        scanner.skip(semicolonToken);
        p.pb = Block.parse(scanner);
        scanner.skip(dotToken);

        leaveParser("program");
        return p;
    }

    @Override
    void checkWhetherAssignable(PascalSyntax where) {

    }

    @Override
    void checkWhetherFunction(PascalSyntax where) {

    }

    @Override
    void checkWhetherProcedure(PascalSyntax where) {

    }

    @Override
    void checkWhetherValue(PascalSyntax where) {

    }
}
