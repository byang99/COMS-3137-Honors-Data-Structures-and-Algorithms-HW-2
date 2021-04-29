package hw.pkg2;
import java.util.*;
import java.io.*;


public class Problem2
{
    public static void main(String [] args)
    {
        String postfix = "34 2 * 5 6 / +";
        ExpressionTree test = new ExpressionTree(postfix);
        
        System.out.println("Result: " + test.eval());
        System.out.println("Postfix: " + test.postfix());
        System.out.println("Prefix: " + test.prefix());
        System.out.println("Infix: " + test.infix());        
    } 
}

















