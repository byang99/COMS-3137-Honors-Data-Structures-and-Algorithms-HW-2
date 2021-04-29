package hw.pkg2;
public class MismatchError implements BalanceError
{
    public int line;
    public String current;
    public char popped;

    public MismatchError(int lineNumber, char currentSymbol, char symbolPopped)
    {
        line = lineNumber;
        current = "" + currentSymbol;
        popped = symbolPopped;
    }
    public MismatchError(int lineNumber, String currentSymbol, char symbolPopped)
    {
        line = lineNumber;
        current = currentSymbol;
        popped = symbolPopped;
    }

    public String toString()
    {
        return "Mismatch Error: { line: " + line + ", current: " + current + ", popped: " + popped + " }";
    }
}
