package com.company;

/**
 * This exception class is thrown to check if the position index entered is valid.
 *
 *  @author  Tracy Yip 
 */
public class InvalidPositionException extends Exception
{
    /**
     * Constructs an InvalidPositionException that passes
     * a string to its super class (Exception)
     */
    public InvalidPositionException()
    {
        super("The index you entered will cause an error. Please try another index.");
    }

    /**
     * Constructs an InvalidPositionException that passes
     * a specified string given in the parameter to its
     * super class (Exception)
     *
     * @param in
     *    A specified string of message that is desired to be printed
     */
    public InvalidPositionException(String in)
    {
        super(in);
    }
}
