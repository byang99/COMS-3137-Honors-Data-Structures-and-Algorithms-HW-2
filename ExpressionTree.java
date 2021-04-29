package hw.pkg2;
import java.util.*;

/*
Implement a class called ExpressionTree . The constructor to ExpressionTree 
will take in a String that contains a postfix expression. The operands will 
be integers and the binary operators will be restricted to +, -, *, and divide. 
Individual tokens, that is the operands and operators, will be delimited 
by spaces. So for example:

34 2 - 5 *

would mean (34-2)*5.

Your constructor will run the stack based algorithm we discussed in class 
to build an expression tree. ExpressionNodes will be a nested class within 
ExpressionTree. You may use any code posted on canvas or from the Weiss 
textbook as a starting point for this class.

*/
public class ExpressionTree
{
    private static class ExpressionNodes
    {
        int operand;
        char operator;
        String symbol;
        ExpressionNodes left;
        ExpressionNodes right;
        
        public ExpressionNodes()
        {
            symbol = "";
            operand = 0;
            operator = ' ';
            left = null;
            right = null;
        }
        
        public ExpressionNodes(String s, ExpressionNodes l, ExpressionNodes r)
        {
            symbol = s;
            left = l;
            right = r;
        }
    }
    
    ExpressionNodes root;
    
    public ExpressionTree(String postfixExpression)
    {
        Stack<ExpressionNodes> expStack = new Stack<>();
        
        String arr[] = postfixExpression.split(" ");
        for(int i = 0; i < arr.length; i++)
        {
            if(!isOperator(arr[i]))
            {
                ExpressionNodes operandNode = new ExpressionNodes(arr[i], null, null);
                operandNode.operand = Integer.parseInt(arr[i]);
                expStack.push(operandNode);
            }
            else if(isOperator(arr[i]))
            {
                if(expStack.size() < 2)
                {
                    System.out.println("Postfix expression invalid.");
                    return;
                    //extra operator
                }
                else
                {
                    ExpressionNodes right = expStack.pop();
                    ExpressionNodes left = expStack.pop();
                    ExpressionNodes operatorNode = new ExpressionNodes(arr[i], left, right);
                    operatorNode.operator = arr[i].charAt(0);
                    expStack.push(operatorNode);
                }
            }   
        }
        if(expStack.size() > 1)
        {
            System.out.println("Postfix expression invalid.");
            return;
            //extra operand
        }
        else
        {
            root = expStack.pop();
        }
    }
    
    private boolean isOperator(String s)
    {
        if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))
        {
            return true;
        }
        return false;
    }
    
    public int calculate(int left, int right, char operator)
    {
        if(operator == '+')
        {
            return left + right;
        }
        else if(operator == '-')
        {
            return left - right;
        }
        else if(operator =='*')
        {
            return left * right;
        }
        else //if(operator == '/')
        {
            return left / right;
        }
    }
    
    
    private int evalRec(ExpressionNodes t)
    {
        if(t.left == null && t.right == null)
        {
            return t.operand;
        }
        else
        {
            int lval = evalRec(t.left);
            int rval = evalRec(t.right);
            
            return calculate(lval, rval, t.operator);
        }
    }
    
    public int eval()
    {
        return evalRec(root);        
    }
    
    private String postfixRecursive(ExpressionNodes t)
    {
        if(t != null)
        {
            return postfixRecursive(t.left) + " " + postfixRecursive(t.right) + " " + t.symbol;
        }
        else
        {
            return "";
        }
    }
    
    
    public String postfix()
    {
        return postfixRecursive(root);        
    }
    
    
    private String prefixRecursive(ExpressionNodes t)
    {
        if(t != null)
        {
            return t.symbol + " " + prefixRecursive(t.left) + " " + prefixRecursive(t.right);
        }
        else
        {
            return "";        
        }
    }
    
    public String prefix()
    {
        return prefixRecursive(root);
    }
    
    
    private String infixRecursive(ExpressionNodes t)
    {
        if(t != null)
        {
            if(!isOperator(t.symbol))
            {
                return t.symbol;
            }
            else
            {
                return "(" + infixRecursive(t.left) + t.symbol + infixRecursive(t.right) + ")";
            }
        }
        else
        {
            return "";
        }
    }
    public String infix()
    {
        return infixRecursive(root);
    }
}













