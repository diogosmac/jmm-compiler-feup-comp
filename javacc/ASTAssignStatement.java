/* Generated By:JJTree: Do not edit this line. ASTAssignStatement.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTAssignStatement extends SimpleNode {
  public boolean array = false;

  public ASTAssignStatement(int id) {
    super(id);
  }

  public ASTAssignStatement(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }

  public String toString() {
    return "Assignment";
//    return "Assignment" + type + ((id != null) ? (" " + id) : "") + (array ? "[]" : "") + (assign ? " =" : "");
  }

  @Override
  public Object jjtGetValue() {
    return "statement";
  }
}
/* JavaCC - OriginalChecksum=9d9f1a0a449037b7428e259f93835c4f (do not edit this line) */
