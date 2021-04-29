package hw.pkg2;
import java.io.*;
import java.util.*;

/*
Implement a class called SymbolBalance.java. It should take a the name of a 
java file as a command line argument. Read in the file and check to make sure 
that all { }'s, <>'s, ( )'s, [ ]'s, " "'s, and /* */
/*'s are properly balanced. Make sure to ignore characters within literal strings, 
literal characters and all types of comments.

*/

public class SymbolBalance
{
    private File myFile;
    private BufferedReader inputFile;
    private FileReader read;
    public void setFile(String filename)
    {
        try
        {
            myFile = new File(filename);
            read = new FileReader(myFile);
            inputFile = new BufferedReader(read);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File " + filename + " not found...");
        }
    }
    public BalanceError checkFile()
    {
        //boolean error = false;
        int lineNumber = 0;

        try
        {
            Stack<Character> charStack = new Stack<>();
            String line;
            boolean inAString = false;
            boolean checkString = true;
            boolean inACharString = false;
            boolean checkCharString = true;
            boolean inMultiLineComment = false;
            boolean checkMultiLine = true;
            boolean inSingleLineComment = false;

            while((line = inputFile.readLine()) != null)
            {
                inSingleLineComment = false;
                lineNumber++;
                //System.out.print("Line " + lineNumber + ": ");
                //System.out.println(line);
                char symbol;
                for(int i = 0; i < line.length(); i++)
                {
                    //process each character
                    symbol = line.charAt(i);
                    //System.out.println(symbol);
                    //comment -> string -> braces
                    if(i < line.length() - 1)
                    {
                        if(symbol == '/' && !inMultiLineComment && checkMultiLine)
                        {
                            if(line.charAt(i+1) == '/')
                            {
                                //System.out.println("Single-line comment");
                                //single line comment, ignore the line
                                break;
                            }
                            else if(line.charAt(i+1) == '*')
                            {
                                //beginning of a multiline comment
                                //System.out.println("STARTING MULTILINE COMMENT");
                                charStack.push(line.charAt(i+1));
                                inMultiLineComment = true;
                                inAString = false;
                                checkString = false;
                                inACharString = false;
                                checkCharString = false;
                                i++;
                                continue;
                            }
                        }
                        if(inMultiLineComment)
                        {
                            if(symbol == '*')
                            {
                                if(line.charAt(i+1) == '/')
                                {
                                    //System.out.println("END MULTILINE COMMENT");
                                    //end of multiline comment
                                    charStack.pop();
                                    inMultiLineComment = false;
                                    checkString = true;
                                    checkCharString = true;
                                    checkMultiLine = true;
                                    i++;
                                    continue;
                                }
                            }
                            else
                            {
                                continue;
                            }
                        }
                        if(!inMultiLineComment && !inAString && !inACharString)
                        {
                            if(symbol == '*')
                            {
                                if(line.charAt(i+1) == '/')
                                {
                                    // There is the */ symbol by itself without /*
                                    //error = true;
                                    //return new MismatchError(lineNumber, "*/", line.charAt(i-1));
                                    if(!charStack.isEmpty())
                                    {
                                        char c = charStack.peek();
                                        charStack.pop();
                                        return new MismatchError(lineNumber, "*/", c);
                                    }
                                    else
                                    {
                                        return new EmptyStackError(lineNumber, "*/");
                                    }
                                }
                            }
                        }
                        if(symbol == '"' && !inAString && checkString)
                        {
                            //inside of a string...just read opening quotes
                            //System.out.println("INSIDE STRING");
                            charStack.push(symbol);
                            inAString = true;
                            inACharString = false;
                            inMultiLineComment = false;
                            checkMultiLine = false;
                            checkCharString = false;
                            inSingleLineComment = false;
                            continue;                            
                        }
                        if(inAString)
                        {
                            if(symbol == '"')
                            {
                                //just read closing quote
                                //no longer in the string
                                charStack.pop();
                                inAString = false;
                                checkString = true;
                                checkCharString = true;
                                checkMultiLine = true;
                                continue;                            
                            }
                        }
                        if(symbol == '\'' && !inACharString && checkCharString)
                        {
                            //inside of a string...just read opening quotes
                            //System.out.println("INSIDE CHAR STRING");
                            charStack.push(symbol);
                            inAString = false;
                            inACharString = true;
                            inMultiLineComment = false;
                            checkMultiLine = false;
                            checkString = false;
                            inSingleLineComment = false;
                            continue;                            
                        }
                        if(inACharString)
                        {
                            if(symbol == '\'')
                            {
                                //just read closing quote
                                //no longer in the string
                                charStack.pop();
                                inACharString = false;
                                checkString = true;
                                checkCharString = true;
                                checkMultiLine = true;
                                continue;                            
                            }
                        }
                    }
                    if(!inAString && !inACharString && !inMultiLineComment)
                    {
                        if(symbol == '(' || symbol == '{' || symbol == '[' || symbol == '<')
                        {
                            //System.out.println("Pushing " + symbol + " onto the stack...");
                            charStack.push(symbol);
                        }
                        if(symbol == ')' || symbol == '}' || symbol == ']' || symbol == '>')
                        {
                            if(charStack.isEmpty())
                            {
                                //System.out.println("Error: " + symbol);
                                //error = true;
                                return new EmptyStackError(lineNumber, symbol);
                            }
                            else
                            {
                                char c = charStack.peek();
                                charStack.pop();
                                
                                if(symbol == ')' && c != '(')
                                {
                                    //System.out.println("Error: " + c);
                                    //error = true;
                                    return new MismatchError(lineNumber, symbol, c);
                                }
                                else if(symbol == ']' && c != '[')
                                {
                                    //System.out.println("Error: " + c);
                                    //error = true;
                                    return new MismatchError(lineNumber, symbol, c);                                }
                                else if(symbol == '}' && c != '{')
                                {
                                    //System.out.println("Error: " + c);
                                    //error = true;
                                    return new MismatchError(lineNumber, symbol, c);   
                                }
                                else if(symbol == '>' && c != '<')
                                {
                                    //System.out.println("Error: " + c);
                                    //error = true;
                                    return new MismatchError(lineNumber, symbol, c);
                                }
                            }
                        }
                    }
                }
            }
            if(!charStack.isEmpty() || inMultiLineComment || inAString || inACharString)
            {
                if(inMultiLineComment)
                {
                    return new NonEmptyStackError("/*", charStack.size());
                }
                return new NonEmptyStackError(charStack.peek(), charStack.size());
            }
        }
        catch(IOException e)
        {
            System.out.println("IO Exception");
        }
        
        return new NoErrorFound();        
    }
    
    public static void main(String [] args)
    {
        SymbolBalance test = new SymbolBalance();
//         test.setFile(args[0]);
//         BalanceError error = test.checkFile();
        
//         System.out.println(error);
        test.setFile("TestFiles/Test1.java");
        BalanceError error = test.checkFile();
        System.out.println(error);
        
        test.setFile("TestFiles/Test2.java");
        error = test.checkFile();
        System.out.println(error);
        
        test.setFile("TestFiles/Test3.java");
        error = test.checkFile();
        System.out.println(error);
        
        test.setFile("TestFiles/Test4.java");
        error = test.checkFile();
        System.out.println(error);
        
        test.setFile("TestFiles/Test5.java");
        error = test.checkFile();
        System.out.println(error);
        
        test.setFile("TestFiles/Test6.java");
        error = test.checkFile();
        System.out.println(error);
        
        test.setFile("TestFiles/Test7.java");
        error = test.checkFile();
        System.out.println(error);
        
        test.setFile("TestFiles/Test8.java");
        error = test.checkFile();
        System.out.println(error);   
    }
}
