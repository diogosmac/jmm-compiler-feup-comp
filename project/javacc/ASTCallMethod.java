/* Generated By:JJTree: Do not edit this line. ASTCallMethod.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTCallMethod extends SimpleNode {
  public ASTCallMethod(int id) {
    super(id);
  }

  public ASTCallMethod(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }

  public String toString() {

    return "CallMethod: " + super.value;
  }
}
/* JavaCC - OriginalChecksum=43d5035f5edcac03dba3c453331bbee2 (do not edit this line) */
