import static org.junit.Assert.*;

import scanner.Token;
import scanner.TokenKind;
import org.junit.Test;
import scanner.Scanner;

/**
 * Created by dmitry on 01.09.16.
 */
public class ScannerTest {
    @Test
    public void testTokenListLength() {
        System.out.println("Testing the length of TokenList, to see if we parsed expected number of tokens");
        Scanner s = new Scanner("src/tests/10star.pas");
        while (s.nextToken.kind != TokenKind.eofToken) {
            s.readNextToken();
        }
        assertEquals("Token list for file 10star.pas should be of size 44",
                44, s.getTokenList().size());
    }

    @Test
    public void testFindToken(){
        System.out.println("Testing findToken()");
        Scanner s = new Scanner("src/tests/10star.pas");
        Token t1 = s.findToken("+ 13;");
        Token t2 = s.findToken(":= 13;");
        Token t3 = s.findToken(": 13;");
        Token t4 = s.findToken(", 13;");
        Token t5 = s.findToken(". 13;");
        Token t6 = s.findToken("= 13;");
        Token t7 = s.findToken(">= 13;");
        Token t8 = s.findToken("> 13;");
        Token t9 = s.findToken("<= 13;");
        Token t10 = s.findToken("< 13;");
        Token t11 = s.findToken("* 13;");
        Token t12 = s.findToken("<> 13;");
        Token t13 = s.findToken(".. 13;");
        Token t14 = s.findToken("[ 13;");
        Token t15 = s.findToken("] 13;");
        Token t16 = s.findToken("( 13;");
        Token t17 = s.findToken(") 13;");
        Token t18 = s.findToken("; 13;");
        Token t19 = s.findToken("- 13;");
        Token t20 = s.findToken("and 13;");
        Token t21 = s.findToken("array 13;");
        Token t22 = s.findToken("begin 13;");
        Token t23 = s.findToken("const 13;");
        Token t24 = s.findToken("div 13;");
        Token t25 = s.findToken("do 13;");
        Token t26 = s.findToken("else 13;");
        Token t27 = s.findToken("end 13;");
        Token t28 = s.findToken("function 13;");
        Token t29 = s.findToken("if 13;");
        Token t30 = s.findToken("mod 13;");
        Token t31 = s.findToken("not 13;");
        Token t32 = s.findToken("of 13;");
        Token t33 = s.findToken("or 13;");
        Token t34 = s.findToken("procedure 13;");
        Token t35 = s.findToken("program 13;");
        Token t36 = s.findToken("then 13;");
        Token t37 = s.findToken("var 13;");
        Token t38 = s.findToken("while 13;");
        Token t39 = s.findToken("e-o-f 13;");
        s.sourceLine = "someName 13;";
        Token t40 = s.findToken("someName 13;");
        s.sourceLine = "123 13;";
        Token t41 = s.findToken("123 13;");
        s.sourceLine = "'s' 13;";
        Token t42 = s.findToken("'s' 13;");
        assertTrue(t1.kind == TokenKind.addToken);
        assertTrue(t2.kind == TokenKind.assignToken);
        assertTrue(t3.kind == TokenKind.colonToken);
        assertTrue(t4.kind == TokenKind.commaToken);
        assertTrue(t5.kind == TokenKind.dotToken);
        assertTrue(t6.kind == TokenKind.equalToken);
        assertTrue(t7.kind == TokenKind.greaterEqualToken);
        assertTrue(t8.kind == TokenKind.greaterToken);
        assertTrue(t9.kind == TokenKind.lessEqualToken);
        assertTrue(t10.kind == TokenKind.lessToken);
        assertTrue(t11.kind == TokenKind.multiplyToken);
        assertTrue(t12.kind == TokenKind.notEqualToken);
        assertTrue(t13.kind == TokenKind.rangeToken);
        assertTrue(t14.kind == TokenKind.leftBracketToken);
        assertTrue(t15.kind == TokenKind.rightBracketToken);
        assertTrue(t16.kind == TokenKind.leftParToken);
        assertTrue(t17.kind == TokenKind.rightParToken);
        assertTrue(t18.kind == TokenKind.semicolonToken);
        assertTrue(t19.kind == TokenKind.subtractToken);
        assertTrue(t20.kind == TokenKind.andToken);
        assertTrue(t21.kind == TokenKind.arrayToken);
        assertTrue(t22.kind == TokenKind.beginToken);
        assertTrue(t23.kind == TokenKind.constToken);
        assertTrue(t24.kind == TokenKind.divToken);
        assertTrue(t25.kind == TokenKind.doToken);
        assertTrue(t26.kind == TokenKind.elseToken);
        assertTrue(t27.kind == TokenKind.endToken);
        assertTrue(t28.kind == TokenKind.functionToken);
        assertTrue(t29.kind == TokenKind.ifToken);
        assertTrue(t30.kind == TokenKind.modToken);
        assertTrue(t31.kind == TokenKind.notToken);
        assertTrue(t32.kind == TokenKind.ofToken);
        assertTrue(t33.kind == TokenKind.orToken);
        assertTrue(t34.kind == TokenKind.procedureToken);
        assertTrue(t35.kind == TokenKind.programToken);
        assertTrue(t36.kind == TokenKind.thenToken);
        assertTrue(t37.kind == TokenKind.varToken);
        assertTrue(t38.kind == TokenKind.whileToken);
        assertTrue(t39.kind == TokenKind.eofToken);
        assertTrue(t40.kind == TokenKind.nameToken && t40.id.equals("someName"));
        assertTrue(t41.kind == TokenKind.intValToken && t41.intVal == 123);
        assertTrue(t42.kind == TokenKind.charValToken && t42.charVal == 's');
    }
}
