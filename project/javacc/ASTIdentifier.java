/* Generated By:JJTree: Do not edit this line. ASTIdentifier.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTIdentifier extends SimpleNode {
  public ASTIdentifier(int id) {
    super(id);
  }

  public ASTIdentifier(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }

  @Override
  public String toString() {
    return "Identifier: " + super.jjtGetValue();
  }
}
/* JavaCC - OriginalChecksum=cda02e3d5a646e09c8ee02b927bcd83b (do not edit this line) */