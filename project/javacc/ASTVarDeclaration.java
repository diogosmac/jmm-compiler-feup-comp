/* Generated By:JJTree: Do not edit this line. ASTVarDeclaration.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTVarDeclaration extends SimpleNode {
  public ASTVarDeclaration(int id) {
    super(id);
  }

  public ASTVarDeclaration(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }

  public String toString() {
    return "VarDeclaration: " + super.value;
  }
}
/* JavaCC - OriginalChecksum=53179e302ccbe35aa55b0ed2bff97059 (do not edit this line) */
