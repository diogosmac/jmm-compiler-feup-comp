import Exceptions.SemanticErrorException;
import SymbolTable.MethodDescriptor;
import SymbolTable.VariableDescriptor;
import SymbolTable.ImportDescriptor;
import SymbolTable.SymbolTable;

import java.util.LinkedList;

public class SemanticAnalyser implements ParserVisitor {

    private SymbolTable table;

    public SemanticAnalyser(SymbolTable table) {
        this.table = table;
    }

    private void printError(String message, int line, int column) {
        System.out.println("SEMANTIC ERROR: " + message + " at line: " + line + ", column: " + column + ".");
    }

    @Override
    public Object visit(SimpleNode node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTProgram node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTImportDeclaration node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTImport node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTClassDeclaration node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTVarDeclaration node, Object data) {
        return node.childrenAccept(this, data);
    }

    @Override
    public Object visit(ASTRegularMethod node, Object data) {
        // get method id node children
        String methodIdentifier = (String) node.jjtGetValue();
        // get method parameters
        // method parameters is always second child
        // NOTE: may not exist
        LinkedList<String> parameterList = new LinkedList<>();
        if (node.jjtGetChild(1) instanceof ASTMethodParams) {
            parameterList = (LinkedList<String>) node.jjtGetChild(1).jjtAccept(this, data);
        }
        // lookup method with methodIdentifier and parameterList
        // visit RegularMethod children with MethodDescriptor
        // for method specific symbol table lookups
        try {
            MethodDescriptor method = this.table.lookupMethod(methodIdentifier, parameterList);
            return node.childrenAccept(this, method);
        } catch (SemanticErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(ASTReturn node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTMethodParams node, Object data) {
        // get parameters node children
        Node[] children = node.jjtGetChildren();
        // create parameter type list
        // for each child node (ASTMethodParam) get type
        LinkedList<String> parameterList = new LinkedList<>();
        for (Node child : children) {
            String parameterType = (String) child.jjtAccept(this, data);
            parameterList.add(parameterType);
        }
        return parameterList;
    }

    @Override
    public Object visit(ASTMethodParam node, Object data) {
        // return parameter type
        return node.jjtGetChild(0).jjtAccept(this, data);
    }

    @Override
    public Object visit(ASTMainMethod node, Object data) {
        // special case of MainMethod: main(String[] <parameter_name>);
        // get method id node children
        String methodIdentifier = "main";
        // get method parameters
        LinkedList<String> parameterList = new LinkedList<>();
        parameterList.add("String[]");
        // lookup method with methodIdentifier and parameterList
        // visit RegularMethod children with MethodDescriptor
        // for method specific symbol table lookups
        try {
            MethodDescriptor method = this.table.lookupMethod(methodIdentifier, parameterList);
            return node.childrenAccept(this, method);
        } catch (SemanticErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object visit(ASTMainParams node, Object data) {
        // not necessary since argument is always of type 'String[]'
        return null;
    }

    @Override
    public Object visit(ASTType node, Object data) {
        return node.jjtGetValue();
    }

    @Override
    public Object visit(ASTReturnType node, Object data) {
        return node.jjtGetValue();
    }

    @Override
    public Object visit(ASTAssignment node, Object data) {
        // An Assignment has two child nodes
        // first child is the assignee (left side)
        String assigneeType = (String) node.jjtGetChild(0).jjtAccept(this, data);
        // second child is the assigner (right side)
        String assignerType = (String) node.jjtGetChild(1).jjtAccept(this, data);
        // check for past semantic errors
        if (assigneeType == null || assignerType == null)
            return null;
        // check if types are the same
        if (!assigneeType.equals(assignerType))
            this.printError("Assignment of different types '" + assigneeType + "' and '" + assignerType + "'", node.line, node.column);
        return null;
    }

    @Override
    public Object visit(ASTObjectCall node, Object data) {
        // an object call has two child nodes
        // first child is the object (caller)
        String objectType = (String) node.jjtGetChild(0).jjtAccept(this, data);
        // second child is the method (callee)
        ASTCallMethod callMethod = (ASTCallMethod) node.jjtGetChild(1);
        String methodIdentifier = (String) callMethod.jjtGetValue();
        LinkedList<String> parameterList = (LinkedList<String>) callMethod.jjtAccept(this, data);
        // lookup 'objectType.methodName'
        //      1. objectType = class name
        if (objectType.contains(this.table.getClassName())) {
            try {
                MethodDescriptor descriptor = this.table.lookupMethod(methodIdentifier, parameterList);
                return descriptor.getType();
            } catch (SemanticErrorException e) {
                printError(e.getMessage(), callMethod.line, callMethod.column);
            }
        }
        //      2. objectType = imported class name
        else {
            try {
                ImportDescriptor descriptor = this.table.lookupImport(objectType + "." + callMethod, parameterList);
                return descriptor.getType();
            } catch (SemanticErrorException e) {
                printError(e.getMessage(), callMethod.line, callMethod.column);
            }
        }
        return null;
    }

    @Override
    public Object visit(ASTIfElseBlock node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTWhileBlock node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTand node, Object data) {
        SimpleNode leftChild = (SimpleNode) node.jjtGetChild(0);
        SimpleNode rightChild = (SimpleNode) node.jjtGetChild(1);

        String leftChildType = (String) node.jjtGetChild(0).jjtAccept(this, data);
        String rightChildType = (String) node.jjtGetChild(1).jjtAccept(this, data);

        // verify data type
        if (!leftChildType.equals("boolean"))
            printError("Operand " + leftChild.jjtGetValue() + " of '&&' is not of 'boolean' type", node.line, node.column);
        else if(!rightChildType.equals("boolean"))
            printError("Operand " + rightChild.jjtGetValue() + " of '&&' is not of 'boolean' type", node.line, node.column);

        // '&&' operator returns an int
        return "boolean";
    }

    @Override
    public Object visit(ASTlt node, Object data) {
        SimpleNode leftChild = (SimpleNode) node.jjtGetChild(0);
        SimpleNode rightChild = (SimpleNode) node.jjtGetChild(1);

        String leftChildType = (String) node.jjtGetChild(0).jjtAccept(this, data);
        String rightChildType = (String) node.jjtGetChild(1).jjtAccept(this, data);

        // verify data type
        if (!leftChildType.equals("boolean"))
            printError("Operand " + leftChild.jjtGetValue() + " of '<' is not of 'boolean' type", node.line, node.column);
        else if(!rightChildType.equals("boolean"))
            printError("Operand " + rightChild.jjtGetValue() + " of '<' is not of 'boolean' type", node.line, node.column);

        // '<' operator returns a boolean
        return "boolean";
    }

    @Override
    public Object visit(ASTsum node, Object data) {
        SimpleNode leftChild = (SimpleNode) node.jjtGetChild(0);
        SimpleNode rightChild = (SimpleNode) node.jjtGetChild(1);

        String leftChildType = (String) node.jjtGetChild(0).jjtAccept(this, data);
        String rightChildType = (String) node.jjtGetChild(1).jjtAccept(this, data);

        // verify data type
        if (!leftChildType.equals("integer"))
            printError("Operand " + leftChild.jjtGetValue() + " of '+' is not of 'integer' type", node.line,node.column);
        else if(!rightChildType.equals("integer"))
            printError("Operand " + rightChild.jjtGetValue() + " of '+' is not of 'integer' type", node.line,node.column);

        // '+' operator returns an int
        return "integer";
    }

    @Override
    public Object visit(ASTsub node, Object data) {
        SimpleNode leftChild = (SimpleNode) node.jjtGetChild(0);
        SimpleNode rightChild = (SimpleNode) node.jjtGetChild(1);

        String leftChildType = (String) node.jjtGetChild(0).jjtAccept(this, data);
        String rightChildType = (String) node.jjtGetChild(1).jjtAccept(this, data);

        // verify data type
        if (!leftChildType.equals("integer"))
            printError("Operand " + leftChild.jjtGetValue() + " of '-' is not of 'integer' type", node.line,node.column);
        else if(!rightChildType.equals("integer"))
            printError("Operand " + rightChild.jjtGetValue() + " of '-' is not of 'integer' type", node.line,node.column);

        // '-' operator returns an int
        return "integer";
    }

    @Override
    public Object visit(ASTmult node, Object data) {
        SimpleNode leftChild = (SimpleNode) node.jjtGetChild(0);
        SimpleNode rightChild = (SimpleNode) node.jjtGetChild(1);

        String leftChildType = (String) node.jjtGetChild(0).jjtAccept(this, data);
        String rightChildType = (String) node.jjtGetChild(1).jjtAccept(this, data);

        // verify data type
        if (!leftChildType.equals("integer"))
            printError("Operand " + leftChild.jjtGetValue() + " of '*' is not of 'integer' type", node.line,node.column);
        else if(!rightChildType.equals("integer"))
            printError("Operand " + rightChild.jjtGetValue() + " of '*' is not of 'integer' type", node.line,node.column);

        // '*' operator returns an int
        return "integer";
    }

    @Override
    public Object visit(ASTdiv node, Object data) {
        SimpleNode leftChild = (SimpleNode) node.jjtGetChild(0);
        SimpleNode rightChild = (SimpleNode) node.jjtGetChild(1);

        String leftChildType = (String) node.jjtGetChild(0).jjtAccept(this, data);
        String rightChildType = (String) node.jjtGetChild(1).jjtAccept(this, data);

        // verify data type
        if (!leftChildType.equals("integer"))
            printError("Operand " + leftChild.jjtGetValue() + " of '/' is not of 'integer' type", node.line,node.column);
        else if(!rightChildType.equals("integer"))
            printError("Operand " + rightChild.jjtGetValue() + " of '/' is not of 'integer' type", node.line, node.column);

        // '/' operator returns an int
        return "integer";
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        // get method descriptor
        MethodDescriptor method = (MethodDescriptor) data;
        // get variable id
        String variableIdentifier = (String) node.jjtGetValue();
        // lookup variable in method
        VariableDescriptor descriptor;
        try {
            descriptor = method.lookupVariable(variableIdentifier);
            return descriptor.getType();
        } catch (SemanticErrorException ignored) {
        }
        // lookup variable in class
        try {
            descriptor = table.lookupAttribute(variableIdentifier);
            return descriptor.getType();
        } catch (SemanticErrorException e) {
            this.printError(e.getMessage(), node.line, node.column);
        }
        // Semantic error: return null
        return null;
    }

    @Override
    public Object visit(ASTGetLength node, Object data) {
        return "int";
    }

    @Override
    public Object visit(ASTCallMethod node, Object data) {
        // get method id
        String methodIdentifier = (String) node.jjtGetValue();
        // get argument node children
        Node[] children = node.jjtGetChildren();
        // get method call arguments list
        // for each child node get type
        LinkedList<String> parameterList = new LinkedList<>();
        for (Node child : children) {
            String parameterType = (String) child.jjtAccept(this, data);
            parameterList.add(parameterType);
        }
        return parameterList;
    }

    @Override
    public Object visit(ASTinteger node, Object data) {
        return "int";
    }

    @Override
    public Object visit(ASTbool node, Object data) {
        return "boolean";
    }

    @Override
    public Object visit(AST_this node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTnot node, Object data) {
        // '!' (not) is a unary operator, therefore it only has one child node
        // that child must be of type boolean
        SimpleNode child = (SimpleNode) node.jjtGetChild(0);
        String childType = (String) node.jjtGetChild(0).jjtAccept(this, data);
        // verify data type
        if (!childType.equals("boolean"))
            printError("Operand " + child.jjtGetValue() + " of '!' is not of 'boolean' type", node.line, node.column );
        // '!' operator returns a boolean
        return "boolean";
    }

    @Override
    public Object visit(AST_new node, Object data) {
        return null;
    }
}
