package com.company;

/**
 * This exception class is thrown to check if the tree is empty.
 *
 *  @author  Tracy Yip 
 */
public class EmptyTreeException extends Exception
{
    /**
     * Constructs an EmptyTreeException that passes
     * a string to its super class (Exception)
     */
    public EmptyTreeException()
    {
        super("The tree is currently empty and this option can't be done. Please try again.");
    }

    /**
     * Constructs an EmptyTreeException that passes
     * a specified string given in the parameter to its
     * super class (Exception)
     *
     * @param in
     *    A specified string of message that is desired to be printed
     */
    public EmptyTreeException(String in)
    {
        super(in);
    }
}
