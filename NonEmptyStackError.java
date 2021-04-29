package hw.pkg2;
public class NonEmptyStackError implements BalanceError
{
    public String top;
    public int size;
    public NonEmptyStackError(char topOfStack, int sizeOfStack)
    {
        top = "" + topOfStack;
        size = sizeOfStack;
    }

    public NonEmptyStackError(String multiline , int sizeOfStack)
    {
        top = multiline;
        size = sizeOfStack;
    }
    public String toString()
    {
        return "NonEmptyStackError: { top: " + top + ", size of stack: " + size + " }";
    }
}
