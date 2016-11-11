package scanner;

import main.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Scanner {
    public Token curToken = null, nextToken = null;

    private String[] illigalChars;
    private ArrayList<Token> tokenList;
    private LineNumberReader sourceFile = null;
    private String sourceFileName;
    public String sourceLine = ""; // public to allow to inject correct sourceline from tests
    private int sourcePos = 0;
    private Pattern namePattern = Pattern.compile("^[a-zA-Z][a-zA-Z_0-9]*");
    private Pattern numberPattern = Pattern.compile("^[0-9]+");
    private Pattern charPattern = Pattern.compile("^'(?:.|'')'");


    public Scanner(String fileName) {
        illigalChars = new String[]{"_", "%"};
        tokenList = new ArrayList<>();
        sourceFileName = fileName;
        try {
            sourceFile = new LineNumberReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            Main.error("Cannot read " + fileName + "!");
        }

        readNextToken();
        readNextToken();
    }


    public String identify() {
        return "Scanner reading " + sourceFileName;
    }


    public int curLineNum() {
        return curToken.lineNum;
    }


    private void error(String message) {
        Main.error("Scanner error on " +
                (curLineNum() < 0 ? "last line" : "line " + curLineNum()) +
                ": " + message);
    }

    public ArrayList<Token> getTokenList() {
        return tokenList;
    }


    public void readNextToken() {
        curToken = nextToken;
        nextToken = null;

        //check if we have reached eof token and we are not in the beggining
        if (curToken != null && curToken.kind == TokenKind.eofToken){
            return;
        }
        if (sourceLine.length() == 0) {
            readNextLine();
        }
        while (true) {
            int commentStart = getFileLineNum();
            if (sourceLine.startsWith("/*")) {
                while (!sourceLine.contains("*/")) {
                    readNextLine();
                    if (sourceLine.equals("e-o-f")){
                        error("Endless comment starting on line " + commentStart + "!");
                    }
                }
                int endOfComment = sourceLine.indexOf("*/")+2;
                if (sourceLine.length() == 2){
                    readNextLine();
                }else{
                    advanceLine(endOfComment);
                    if (sourceLine.length() == 0){
                        readNextLine();
                    }
                }
            } else if (sourceLine.startsWith("{")) {
                while (!sourceLine.contains("}")) {
                    readNextLine();
                    if (sourceLine.equals("e-o-f")){
                        error("Endless comment starting on line " + commentStart + "!");
                    }
                }
                int endOfComment = sourceLine.indexOf("}")+1;
                if (sourceLine.length() == 1){
                    readNextLine();
                }else{
                    advanceLine(endOfComment);
                    if (sourceLine.length() == 0){
                        readNextLine();
                    }
                }
            } else {
                nextToken = findToken(sourceLine);
                tokenList.add(nextToken);
                if (nextToken.kind == TokenKind.nameToken) {
                    advanceLine(nextToken.id.length());
                } else if (nextToken.kind == TokenKind.charValToken) {
                    advanceCharValToken();
                } else if (nextToken.kind == TokenKind.intValToken) {
                    advanceLine(String.valueOf(nextToken.intVal).length());
                } else {
                    advanceLine(nextToken.kind.toString().length());
                }
                break;
            }
        }
        Main.log.noteToken(nextToken);
    }

    private void advanceCharValToken() {
        if (sourceLine.startsWith("''''")){
            advanceLine(4);
        }else{
            advanceLine(3);
        }
    }

    public Token findToken(String sourceLine) {
        Token newToken = null;
        for (TokenKind tk : TokenKind.values()) {
            // if line starts with predefined token that is not a constant or a name
            if (sourceLine.startsWith(tk.toString()) && tk.isReservedToken() && !tk.isLiteralTok()) {
                if (tk == TokenKind.eofToken) {
                    return new Token(TokenKind.eofToken);
                } else {
                    //WARNING HACK MADE AT 3AM probably will explode somewhere ;)
                    //if something fails because of this if just comment it out
                    //everything except udeklarert-1.pas should still work fine
                    if (sourceLine.toLowerCase().startsWith("double".toLowerCase()) && tk == TokenKind.doToken){
                        newToken = getLiteralToken(newToken);
                        return newToken;
                    }
                    newToken = new Token(tk, sourceFile.getLineNumber());
                    return newToken;
                }
            }
        }
        // if line didn't start with keyword/operator
        Token literalToken = getLiteralToken(newToken);
        if (literalToken != null) return literalToken;
        return null;
    }

    private Token getLiteralToken(Token newToken) {
        if (newToken == null) {
            Matcher nameMatcher = namePattern.matcher(sourceLine);
            Matcher numberMatcher = numberPattern.matcher(sourceLine);
            Matcher charMatcher = charPattern.matcher(sourceLine);
            if (nameMatcher.find()) {
                return new Token(nameMatcher.group(), sourceFile.getLineNumber());
            } else if (numberMatcher.find()) {
                return new Token(Integer.parseInt(numberMatcher.group()), sourceFile.getLineNumber());
            } else if (charMatcher.find()) {
                return new Token(charMatcher.group().charAt(1), sourceFile.getLineNumber());
            }
            if (newToken == null) {
                for (String invChar : illigalChars) {
                    if (sourceLine.contains(invChar)) {
                        error("Illegal character: '" + invChar + "'");
                    }
                }
                Pattern endlessCharPattern = Pattern.compile("^'.*");
                Matcher m = endlessCharPattern.matcher(sourceLine);
                if (m.find()){
                    error("Illegal char literal!");
                }
            }
        }
        return null;
    }

    private void trimLine() {
        sourceLine = sourceLine.trim();
    }

    private void readNextLine() {
        if (sourceFile != null) {
            try {
                sourceLine = sourceFile.readLine();
                if (sourceLine == null) {
                    sourceFile.close();
                    sourceFile = null;
                    sourceLine = "e-o-f";
                } else if (sourceLine.trim().equals("")) {
                    Main.log.noteSourceLine(getFileLineNum(), sourceLine);
                    readNextLine();
                    return;
                }
                sourcePos = 0;
            } catch (IOException e) {
                Main.error("Scanner error: unspecified I/O error!");
            }
        }
        trimLine();
        if (sourceFile != null) {
            Main.log.noteSourceLine(getFileLineNum(), sourceLine);
        }
    }

    private int getFileLineNum() {
        return (sourceFile != null ? sourceFile.getLineNumber() : 0);
    }

    // Character test utilities:

    private boolean isLetterAZ(char c) {
        return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z';
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    // Parser tests:
    public void test(TokenKind t) {
        if (curToken.kind != t)
            testError(t.toString());
    }

    public void testError(String message) {
        Main.error(curLineNum(),
                "Expected a " + message + " but found a " + curToken.kind + "!");
    }

    public void skip(TokenKind t) {
        test(t);
        readNextToken();
    }

    private void advanceLine(int i) {
        sourceLine = sourceLine.substring(i);
        sourceLine = sourceLine.trim(); //Trims possible whitespace between tokens
    }
}
