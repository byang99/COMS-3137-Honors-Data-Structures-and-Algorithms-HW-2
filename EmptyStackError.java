package hw.pkg2;
public class EmptyStackError implements BalanceError
{
    public int line;
    public String current;

    
    public EmptyStackError(int lineNumber, char currentSymbol)
    {
        line = lineNumber;
        current = "" + currentSymbol;
    }
    
    public EmptyStackError(int lineNumber, String currentSymbol)
    {
        line = lineNumber;
        current = currentSymbol;
    }

    public String toString()
    {
        return "EmptyStackError: { line: " + line + ", symbol: " + current + "}";
    }
}

