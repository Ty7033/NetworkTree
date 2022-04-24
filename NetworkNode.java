package com.company;

/**
 * This class creates a node called NetworkNode that contains information such as the name, type of component
 * is being represented, the parent of the node, an array of children, and the max children a node can have.
 *
 * @author  Tracy Yip 
 */
public class NetworkNode
{
    private String name;//The name of the node
    private boolean isNintendo;//A boolean that indicates whether the node is a NintendoNode
    private boolean isBroken;//A boolean that indicates whether the router is down
    private NetworkNode parent;//The parent of the NetworkNode
    private NetworkNode[]children;//An array of the children of the node
    private int nextAvailableSpace;//The index of the next available space in the array
    private int index;//The index of the array the NetworkNode is in
    private int level;//An int to hold the level of the node in the tree
    final int maxChildren=9;//The max children the node can have

    /**
     * This is a constructor that creates a NetworkNode that sets the name to the String passed in the parameter.
     * It sets the isNintendo and isBroken to false and its parent to null.
     *
     * @param name
     *    The name of the node
     */
    public NetworkNode(String name)
    {
        this.name=name;
        this.isNintendo=false;
        boolean isBroken=false;
        parent=null;
        nextAvailableSpace=0;
        children=new NetworkNode[maxChildren];
    }

    /**
     * This constructor takes in a String and boolean as its parameters. it sets the name to the string,
     * isNintendo to the boolean, isBroken to false, parent to null, and initializes the children array to have a
     * length of the maxChildren integer.
     *
     * @param name
     *    The name of the node
     * @param isNintendo
     *    A boolean that indicates if the node added is a Nintendo
     */
    public NetworkNode(String name, boolean isNintendo)
    {
        this.name=name;
        this.isNintendo=isNintendo;
        boolean isBroken=false;
        parent=null;
        if(!isNintendo)
        {
            children=new NetworkNode[maxChildren];
        }
    }

    /**
     * This method checks if children array has reached its maximum capacity.
     *
     * @return
     *    A boolean that indicates if the array still has space to hold another child
     * @throws FullNodeException
     *    When the children array is full
     */
    public boolean checkCapacity() throws FullNodeException
    {
        if(nextAvailableSpace + 1 > maxChildren)
        {
            throw new FullNodeException();
        }
        return true;
    }

    /**
     * This method checks if the NetworkNode has children in its array.
     *
     * @return
     *    A boolean that indicates whether the NetworkNode has children nodes
     */
    public boolean hasChildren()
    {
        for(int i=0; i<maxChildren; i++)
        {
            if(children[i]!=null)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This method gets the parent of the NetworkNode.
     *
     * @return
     *    The parent of the NetworkNode
     */
    public NetworkNode getParent()
    {
        return parent;
    }

    /**
     * This method indicates whether the NetworkNode is broken.
     *
     * @return
     *    The boolean that indicates the status of the NetworkNode
     */
    public boolean isBroken()
    {
        return isBroken;
    }

    /**
     * This method indicates whether the NetworkNode is of type Nintendo.
     *
     * @return
     *    A boolean that indicates if the NetworkNode is a Nintendo or not
     */
    public boolean isNintendo()
    {
        return isNintendo;
    }

    /**
     * This method gets the children array of the NetworkNode.
     *
     * @return
     *    The children array of the NetworkNode
     */
    public NetworkNode[] getChildren()
    {
        return children;
    }

    /**
     * This method gets the name of the NetworkNode.
     *
     * @return
     *    A String that holds the name of the NetworkNode
     */
    public String getName()
    {
        return name;
    }

    /**
     * This method sets the status of the NetworkNode to the boolean passed in its parameter.
     *
     * @param broken
     *    A boolean that indicates whether the NetworkNode is broken
     */
    public void setBroken(boolean broken)
    {
        isBroken = broken;
    }

    /**
     * This method sets the children array to the NetworkNode array passed in its parameter.
     *
     * @param children
     *    The NetworkNode array that the children array is being set to
     */
    public void setChildren(NetworkNode[] children)
    {
        this.children = children;
    }

    /**
     * This method sets the name to the String passed in its parameter.
     *
     * @param name
     *    The name of the NetworkNode
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * This method sets the type of the NetworkNode with the boolean passed in its parameter.
     *
     * @param nintendo
     *    A boolean that indicates if the NetworkNode is of type Nintendo
     */
    public void setNintendo(boolean nintendo)
    {
        isNintendo = nintendo;
    }

    /**
     * This method sets the parent of the NetworkNode with the NetworkNode passed in its parameter.
     *
     * @param parent
     *    The parent of the NetworkNode
     */
    public void setParent(NetworkNode parent)
    {
        this.parent = parent;
    }

    /**
     * This method gets the index of the last child in the children array.
     *
     * @return
     *    The index of the last child in the children array
     */
    public int getNextAvailableSpace()
    {
        return nextAvailableSpace;
    }

    /**
     * This method sets the index of the last child in the array to the integer passed in its parameter.
     *
     * @param nextAvailableSpace
     *    The index that the last child is at in the array
     */
    public void nextAvailableSpace(int nextAvailableSpace)
    {
        this.nextAvailableSpace= nextAvailableSpace;
    }

    /**
     * This method gets the level of the NetworkNode.
     *
     * @return
     *    The level of the NetworkNode
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * This method sets the level of the NetworkNode with the integer passed in its parameter.
     *
     * @param level
     *    The level of the NetworkNode
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * This method gets the maximum number of children the NetworkNode can have.
     *
     * @return
     *    An int that represents the max children NetworkNode can have
     */

    public int getMaxChildren()
    {
        return maxChildren;
    }

    /**
     * This method gets the index of the NetworkNode in the array
     *
     * @return
     *    An int that represents the position of the NetworkNode in the array
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * This method sets the index of the NetworkNode in the array.
     *
     * @param index
     *    An index of the array
     */
    public void setIndex(int index)
    {
        this.index = index;
    }
}
