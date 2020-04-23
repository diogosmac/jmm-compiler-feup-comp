/* Generated By:JJTree: Do not edit this line. ASTReturnType.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTReturnType extends SimpleNode {

  public String type;
  public boolean array = false;
  public String id;

  public ASTReturnType(int id) {
    super(id);
  }

  public ASTReturnType(Parser p, int id) {
    super(p, id);
  }

  public String getType() {
    return type;
  }

  public String toString() {

    String out = "Returns: ";

    if (type == "int" || type == "bool" || type == "void") {
      out += type;
      if (type == "int" && array)
        out += "[]";
    } else
      out += id;

    return out;

  }

}
/* JavaCC - OriginalChecksum=feea0350637bac5e90d2bbfd6bc4d4c7 (do not edit this line) */
