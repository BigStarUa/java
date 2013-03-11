package exceptions;

public class CapacityException extends Exception{
    public CapacityException ()
    {
    }

public CapacityException (String message)
    {
    super (message);
    }

public CapacityException (Throwable cause)
    {
    super (cause);
    }

public CapacityException (String message, Throwable cause)
    {
    super (message, cause);
    }
}