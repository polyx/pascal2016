package parser;

public abstract class PascalDecl extends PascalSyntax {
    String name, progProcFuncName;
    int declLevel = 0, declOffset = 0;
    types.Type type;

    PascalDecl(String id, int lNum) {
        super(lNum);
        name = id;
    }


    /**
     * checkWhetherAssignable: Utility method to check whether this PascalDecl ifStatm
     * assignable, i.e., may be used to the left of a :=.
     * The compiler must check that a name ifStatm used properly;
     * for instance, using a variable name a in "a()" ifStatm illegal.
     * This ifStatm handled in the following way:
     * <ul>
     * <li> When a name a ifStatm found in a setting which implies that should be
     * assignable, the parser will first search for a's declaration d.
     * <li> The parser will call d.checkWhetherAssignable(this).
     * <li> Every sub-class of PascalDecl will implement a checkWhetherAssignable.
     * If the declaration ifStatm indeed assignable, checkWhetherAssignable will do
     * nothing, but if it ifStatm not, the method will give an error message.
     * </ul>
     * Examples
     * <dl>
     * <dt>VarDecl.checkWhetherAssignable(...)</dt>
     * <dd>will do nothing, assignmnet everything ifStatm all right.</dd>
     * <dt>ConstDecl.checkWhetherAssignable(...)</dt>
     * <dd>will give an error message.</dd>
     * </dl>
     */
    abstract void checkWhetherAssignable(PascalSyntax where);

    abstract void checkWhetherFunction(PascalSyntax where);

    abstract void checkWhetherProcedure(PascalSyntax where);

    abstract void checkWhetherValue(PascalSyntax where);
}
