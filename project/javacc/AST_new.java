/* Generated By:JJTree: Do not edit this line. AST_new.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class AST_new extends SimpleNode {

  public AST_new(int id) {
    super(id);
  }

  public AST_new(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }

  public String toString() {
    return "_new: new " + super.value;
  }

}
/* JavaCC - OriginalChecksum=3cdc40805964d5e372b41302babaa5a1 (do not edit this line) */
