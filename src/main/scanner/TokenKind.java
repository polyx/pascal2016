package scanner;

// Note that tokens found in standard Pascal but not in Pascal2016
// have been commented out.

public enum TokenKind {
    nameToken("name"),
    intValToken("number"),
    charValToken("char"),
    addToken("+"),
    assignToken(":="),
    colonToken(":"),
    commaToken(","),
    rangeToken(".."),
    dotToken("."),
    equalToken("="),
    notEqualToken("<>"),
    greaterEqualToken(">="),
    lessEqualToken("<="),
    greaterToken(">"),
    lessToken("<"),
    multiplyToken("*"),
    leftBracketToken("["),
    rightBracketToken("]"),
    leftParToken("("),
    rightParToken(")"),
    semicolonToken(";"),
    subtractToken("-"),
    andToken("and"), 
    arrayToken("array"),
    beginToken("begin"),
    constToken("const"),
    divToken("div"), 
    doToken("do"),
    elseToken("else"),
    endToken("end"),
    functionToken("function"),
    ifToken("if"),
    modToken("mod"),
    notToken("not"),
    ofToken("of"), 
    orToken("or"),
    procedureToken("procedure"), 
    programToken("program"),
    thenToken("then"),
    varToken("var"),
    whileToken("while"),
    eofToken("e-o-f");
    /* withToken("with"), */
    /* toToken("to"), */
    /* typeToken("type"),     */
    /* untilToken("until"),   */
    /* recordToken("record"), */
    /* repeatToken("repeat"), */
    /* setToken("set"), */
    /* packedToken("packed"), */
    /* nilToken("nil"), */
    /* inToken("in"), */
    /* labelToken("label"), */
    /* upArrowToken("^"), */
    /* gotoToken("goto"), */
    /* fileToken("file"), */
    /* forToken("for"), */
    /* downtoToken("downto"), */
    /* divideToken("/"), */
    /* caseToken("case"), */

    private String image;

    TokenKind(String im) {
	image = im;
    }


    public String identify() {
	return image + " token";
    }

    @Override public String toString() {
	return image;
    }

    /**
     * @return true if token is not a constant or a name
     */
    public boolean isReservedToken(){
        return this!=nameToken || this!=intValToken || this!=charValToken;
    }

    public boolean isKeyword(){
        return this==whileToken || this==varToken || this==thenToken || this==programToken
                || this==procedureToken || this==orToken || this==ofToken || this==notToken
                || this==modToken || this==ifToken || this==functionToken || this==endToken
                || this==elseToken || this==doToken || this==constToken || this==beginToken
                || this==arrayToken || this==andToken;
    }

    public boolean isFactorOpr() {
	return this==multiplyToken || this==divToken ||
	    this==modToken || this==andToken;
    }

    public boolean isPrefixOpr() {
	return this==addToken || this==subtractToken;
    }

    public boolean isRelOpr() {
	return this==equalToken || this==notEqualToken ||
	    this==lessToken || this==lessEqualToken ||
	    this==greaterToken || this==greaterEqualToken;
    }

    public boolean isTermOpr() {
	return isPrefixOpr() || this==orToken;
    }
}
