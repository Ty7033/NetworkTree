package com.company;

/**
 * This exception class is thrown to check if the children array of the NetworkNode is full.
 *
 *  @author  Tracy Yip Student ID: 114527635 Recitation: R-03
 */
public class FullNodeException extends Exception
{
    /**
     * Constructs an FullNodeException that passes
     * a string to its super class (Exception)
     */
    public FullNodeException()
    {
        super("The node can not have any more children nodes. Please try another node.");
    }

    /**
     * Constructs an FullNodeException that passes
     * a specified string given in the parameter to its
     * super class (Exception)
     *
     * @param in
     *    A specified string of message that is desired to be printed
     */
    public FullNodeException(String in)
    {
        super(in);
    }

}
