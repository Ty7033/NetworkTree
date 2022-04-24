package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class creates a tree called NetworkTree that manages the game network. It contains the methods to
 * perform actions such as cutting a cursor or adding a child
 * to the tree of the network.
 *
 * @author  Tracy Yip 
 */
public class NetworkTree
{
    private NetworkNode root;//A NetworkNode that holds the root of the tree
    private NetworkNode cursor;//A NetworkNode that holds the cursor of the tree
    private static int currentLevel = 0;//An int that indicates the current level of the tree
    private static int currentLeastMinFaultCount=0;//An int that keeps track of the least number of faults
    private static NetworkNode leastMinFault;//The node that has the least number of faulty children

    /**
     * This is a constructor of a NetworkTree that sets the root and cursor to null.
     */
    public NetworkTree()
    {
        root = null;
        cursor = null;
    }

    /**
     * This method moves the cursor back to the root of the tree.
     */
    public void cursorToRoot() throws EmptyTreeException
    {
        if(root==null)
        {
            throw new EmptyTreeException();
        }
        else
        {
            cursor = root;
        }
    }

    /**
     * This method saves the cursor and all of its children in a NetworkNode. It then removes the cursor and
     * all of its children. The cursor is moved to the parent of the removed cursor.
     *
     * @return
     *    The NetworkNode that was removed
     */
    public NetworkNode cutCursor() throws EmptyTreeException
    {
        if (root == null || cursor==null)
        {
            throw new EmptyTreeException();
        }
        NetworkNode cutOut = cursor;
        NetworkNode parent = cursor.getParent();
        cutOut.setParent(null);
        for(int i = cursor.getIndex(); i < parent.getChildren().length-1; i++)
        {
            if (parent.getChildren()[cursor.getIndex() + 1] == null)
            {
                parent.getChildren()[cursor.getIndex()] = null;
                break;
            }
            else
            {
                NetworkNode nextNode = parent.getChildren()[i+1];
                parent.getChildren()[i] = nextNode;
                if(nextNode!=null)
                {
                    nextNode.setIndex(i);
                }
            }
        }
        cursor = parent;
        cursor.nextAvailableSpace(cursor.getNextAvailableSpace()-1);
        return cutOut;
    }

    /**
     * This method adds a new child NetworkNode to the cursor's children array.
     *
     * @param index
     *    The index of the array the child is being added into
     * @param node
     *    The node that is being added
     * @throws InvalidPositionException
     *    When the index passed in the parameter is equal to or less than 0 or will cause a hole in the array
     */
    public void addChild(int index, NetworkNode node) throws InvalidPositionException, EmptyTreeException
    {
        if (root == null || cursor == null)
        {
            throw new EmptyTreeException();
        }
        try
        {
            cursor.checkCapacity();
        }
        catch (FullNodeException x)
        {
            System.out.println(x.getMessage());
        }
        if (index - 1 > cursor.getNextAvailableSpace() || index <= 0)
        {
            throw new InvalidPositionException();
        }
        else
        {
            if (cursor.getChildren()[index - 1] != null)
            {
                for (int i = cursor.getChildren().length - 2; i >= index - 1; i--)
                {
                    cursor.getChildren()[i + 1] = cursor.getChildren()[i];
                    if(cursor.getChildren()[i+1]!=null)
                    {
                        cursor.getChildren()[i+1].setIndex(i);
                    }
                }
            }
            cursor.getChildren()[index-1] = node;
            cursor.nextAvailableSpace(cursor.getNextAvailableSpace() + 1);
            node.setParent(cursor);
            node.setLevel(cursor.getLevel()+1);
            node.setIndex(index - 1);
            cursor = node;
        }
    }

    /**
     * This method moves the cursor to the child in the given index of the array.
     *
     * @param index
     *    The position of the child in the array that the cursor is being moved to
     * @throws InvalidPositionException
     *    When the cursor does not have children or the given index is negative or equal to 0
     *    or the index will cause a hole in the array
     */
    public void cursorToChild(int index) throws EmptyTreeException,InvalidPositionException
    {
        if (root == null)
        {
            throw new EmptyTreeException();
        }
        else if (cursor.isNintendo() || !cursor.hasChildren() )
        {
            System.out.println("The cursor does not have child.");
        }
        else if ( index - 1 > cursor.getNextAvailableSpace() || index <= 0)
        {
            throw new InvalidPositionException();
        }
        else
        {
            cursor = cursor.getChildren()[index-1];
        }
    }

    /**
     * This method moves the cursor to parent of the current node.
     *
     * @throws EmptyTreeException
     *    When there are no NetworkNodes in the tree
     * @throws AtRootException
     *    When the cursor is currently at the root
     */
    public void cursorToParent() throws EmptyTreeException, AtRootException
    {
        if (cursor == null)
        {
            throw new EmptyTreeException();
        }
        else if (cursor == root)
        {
            throw new AtRootException();
        }
        else
        {
            cursor = cursor.getParent();
        }
    }

    /**
     * This method reads the text from the file passed in the parameter and constructs a NetworkTree
     * based on the information in the file.
     *
     * @param filename
     *    The file that is being read
     * @return
     *    The tree created
     * @throws FileNotFoundException
     *    When there is no such file
     */
    public static NetworkTree readFromFile(String filename) throws FileNotFoundException
    {
        File file = new File(filename);
        NetworkTree tree = new NetworkTree();

        try
        {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine())
            {
                String input = scan.nextLine();
                String[] in = input.split("(?<=\\d)(?=\\D)");
                String nodeName = in.length > 1 ? in[1] : in[0];
                NetworkNode newNode = new NetworkNode(nodeName);
                if (in.length == 1 && tree.root == null)
                {
                    tree.root = newNode;
                    tree.cursor = newNode;
                    leastMinFault=tree.root;
                }
                else
                {
                    if (in[1].charAt(0) == '-')
                    {
                        newNode.setNintendo(true);
                        newNode.setName(nodeName.substring(1));
                        newNode.setChildren(null);
                    }
                    try
                    {
                        if (currentLevel < in[0].length())
                        {
                            currentLevel++;
                        }
                        else if (currentLevel == in[0].length())
                        {
                            tree.cursor = tree.cursor.getParent();
                        }
                        else
                        {
                            while (currentLevel != in[0].length())
                            {
                                currentLevel--;
                                try
                                {
                                    tree.cursorToParent();
                                }
                                catch (EmptyTreeException | AtRootException d)
                                {
                                    System.out.print(d.getMessage());
                                }
                            }
                            tree.cursor = tree.cursor.getParent();
                        }
                        newNode.setParent(tree.cursor);
                        tree.addChild(tree.cursor.getNextAvailableSpace()+1, newNode);
                        newNode.setLevel(currentLevel);
                    }
                    catch (InvalidPositionException | EmptyTreeException x)
                    {
                        System.out.println(x.getMessage());
                    }
                }
            }
        }
        catch (FileNotFoundException y)
        {
            System.out.println(filename + " not found.");
        }
        if (file.exists())
        {
            System.out.println(filename + " loaded.");
        }
        try
        {
            tree.cursorToRoot();
        }
        catch(EmptyTreeException x)
        {
            System.out.println("");
        }
        return tree;
    }

    /**
     * This method generates a text file that contains has the information of the tree, including the position,
     * whether they are NintendoNodes, and the name of each NetworkNodes.
     *
     * @param tree
     *    The tree that is being formatted into a file
     * @param filename
     *    The file that the formatted information of the tree is added into
     * @throws FileNotFoundException
     *    When the file passed in the parameter can not be found
     */
    public static void writeToFile(NetworkTree tree, String filename) throws FileNotFoundException, EmptyTreeException
    {
        File file = new File(filename);
        PrintWriter pw = new PrintWriter(file);
        if (tree.getRoot() == null)
        {
            throw new EmptyTreeException("The tree is currently empty and there is nothing to save. Please try a different option");
        }
        else
        {
            printNode("", tree.getRoot(),pw);
            pw.close();
        }
    }

    /**
     * This method traverses through the tree and writes the NetworkNodes with their numerical position
     * in a newly created file.
     *
     * @param digitString
     *    A String that holds the numerical representation of the NetworkNodes in the tree
     * @param node
     *    A node that needs to be included into the file
     * @param pw
     *    A PrintWriter that writes the information into the file
     */
    public static void printNode(String digitString, NetworkNode node, PrintWriter pw)
    {
        if(node.isNintendo())
        {
            pw.println(digitString + "-"+node.getName());
        }
        else
        {
            pw.println(digitString + node.getName());
        }
        if(node.getChildren()==null||isEmpty(node))
        {
            return;
        }
        for(int i=0; i<node.getChildren().length; i++)
        {
            if(node.getChildren()[i]!=null)
            {
                printNode(digitString+(i+1), node.getChildren()[i],pw);
            }
        }
    }

    /**
     * This method moves the cursor to the node where all the broken nodes are either the cursor
     * or its descendants.
     */
    public void cursorToMinimalBrokenSubtree() throws EmptyTreeException
    {
        if (root == null)
        {
            throw new EmptyTreeException();
        }
        if (!root.hasChildren())
        {
            cursor = root;
        }
        else
        {
            findLeastFaultTree(root);
            if (currentLeastMinFaultCount > 0)
            {
                cursor = leastMinFault;
            }
        }
    }

    /**
     * This method goes through the tree to find the node with the least broken nodes.
     *
     * @param node
     *    The node we are currently checking
     */
        public static void findLeastFaultTree(NetworkNode node)
        {
            int newFaultCount = 0;
            if (node == null || node.getChildren() == null || isEmpty(node) || currentLeastMinFaultCount == 1)
            {
                return;
            }
            for (NetworkNode x : node.getChildren())
            {
                if (x != null)
                {
                    if (x.isBroken())
                    {
                        newFaultCount++;
                    }
                }
            }
            if (newFaultCount < currentLeastMinFaultCount || currentLeastMinFaultCount == 0)
            {
                leastMinFault = node;
                currentLeastMinFaultCount = newFaultCount;
            }
            for (NetworkNode y : node.getChildren())
            {
                findLeastFaultTree(y);
            }
        }

    /**
     * This method changes the status of the node the cursor is at to broken or fixed.
     */
    public void changeCursorBrokenStatus()
    {
        leastMinFault=root;
        if(cursor==null)
        {
            System.out.println("The cursor is currently null");
        }
        else if(cursor.isBroken())
        {
            cursor.setBroken(false);
        }
        else
        {
            cursor.setBroken(true);
        }
    }

    /**
     * This method organizes the NetworkTree into a string format with proper indentation, correct indication of the
     * node type, and the position of the cursor to be printed.
     */
    public void printTree(NetworkNode node) throws EmptyTreeException
    {
        String cursorPrint="->";
        String raspberry="+";
        String nintendo="-";
        String broken=" ~Fault~";
        String output="";
        if(root==null)
        {
            throw new EmptyTreeException();
        }
        if(node==cursor)
        {
            output+=cursorPrint;
        }
        if(!node.isNintendo() && node!=cursor)
        {
            output+=raspberry + node.getName();
        }
        else if(node!=cursor && node.isNintendo())
        {
            output+= nintendo+ node.getName();
        }
        else
        {
           output+=node.getName();
        }
        if(node.isBroken())
        {
            output+=broken;
        }
        if(node==root)
        {
            System.out.print(output.indent(node.getLevel()));
        }
        else
        {
            System.out.print(output.indent(node.getLevel()*2));
        }
        if (node.getChildren()==null||!node.hasChildren())
        {
            return;
        }
        else
        {
            for(NetworkNode x: node.getChildren())
            {
                if(x != null)
                {
                    printTree(x);
                }
            }
        }
    }

    /**
     * This method gets the cursor of the tree
     *
     * @return
     *    The cursor of the tree
     */
    public NetworkNode getCursor()
    {
        return cursor;
    }

    /**
     * This method sets the cursor to the NetworkNode passed in the parameter.
     *
     * @param cursor
     *    The NetworkNode the cursor is being set to
     */
    public void setCursor(NetworkNode cursor)
    {
        this.cursor = cursor;
    }

    /**
     * This method gets the root of the tree
     *
     * @return
     *    The root of the tree
     */
    public NetworkNode getRoot()
    {
        return root;
    }

    /**
     * This method checks if the children array of the NetworkNode is empty
     *
     * @param node .
     *    The node being checked
     */
    public static boolean isEmpty(NetworkNode node)
    {
        for(NetworkNode x:node.getChildren())
        {
            if(x!=null)
            {
                return false;
            }
        }
        return true;
    }
}
