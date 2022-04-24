package com.company;

/**
 * This exception class is thrown to check if the cursor is at the root.
 *
 *  @author  Tracy Yip Student ID: 114527635 Recitation: R-03
 */
public class AtRootException extends Exception
{
    /**
     * Constructs an AtRootException that passes
     * a string to its super class (Exception)
     */
    public AtRootException()
    {
        super("This action can not be performed because the cursor is currently at the root");
    }

    /**
     * Constructs an AtRootException that passes
     * a specified string given in the parameter to its
     * super class (Exception)
     *
     * @param in
     *    A specified string of message that is desired to be printed
     */
    public AtRootException(String in)
    {
        super(in);
    }
}
