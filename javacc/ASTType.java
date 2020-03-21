/* Generated By:JJTree: Do not edit this line. ASTType.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTType extends SimpleNode {

  public String type;
  public boolean array = false;
  public String id;

  public ASTType(int id) {
    super(id);
  }

  public ASTType(Parser p, int id) {
    super(p, id);
  }

  public String toString() {

    if(type == "int") {
        return "Type: " + type + (array ? "[]" : "");
    } else if(type == "bool") {
        return "Type: " + type;
    } else 
        return "Type: " + id;
  }

}
/* JavaCC - OriginalChecksum=4802d77cbd42a285c599e5af18a60848 (do not edit this line) */
