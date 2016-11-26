package main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CodeFile {
    private String codeFileName;
    private PrintWriter code;
    private int numLabels = 0;

    CodeFile(String fName) {
        codeFileName = fName;
        try {
            code = new PrintWriter(fName);
        } catch (FileNotFoundException e) {
            Main.error("Cannot create code file " + fName + "!");
        }
        code.println("# Code file created by Pascal2016 compiler " +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    void finish() {
        code.close();
    }

    public String identify() {
        return "Code file named " + codeFileName;
    }


    public String getLabel(String origName) {
        return origName + "_" + (++numLabels);
    }

    public String getLocalLabel() {
        return String.format(".L%04d", ++numLabels);
    }


    public void genInstr(String label, String instr, String arg, String comment) {
        if (label.length() > 0) {
            code.println(label + ":");
            System.out.println(label + ":");
            System.out.flush();
        }
        if ((instr + arg + comment).length() > 0) {
            code.printf("        %-7s %-23s ", instr, arg);
            System.out.printf("        %-7s %-23s ", instr, arg);
            System.out.flush();
            if (comment.length() > 0) {
                code.print("# " + comment);
                System.out.print("# " + comment);
                System.out.flush();
            }
            code.println();
            System.out.println();
            System.out.flush();
        }
    }
    public void genInstr(String label, String instr, String arg) {
        if (label.length() > 0) {
            code.println(label + ":");
            System.out.println(label + ":");
            System.out.flush();
        }
        if ((instr + arg).length() > 0) {
            code.printf("        %-7s %-23s ", instr, arg);
            System.out.printf("        %-7s %-23s ", instr, arg);
            System.out.flush();
            code.println();
            System.out.println();
            System.out.flush();
        }
    }
}
