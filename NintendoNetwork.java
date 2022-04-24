package com.company;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class sets up the network for the game. It contains a NetworkTree and the main method where user
 * interacts with the program to add or remove NetworkNodes in the system.
 *
 * @author  Tracy Yip Student ID: 114527635 Recitation: R-03
 */
public class NintendoNetwork
{
    private static NetworkTree tree=new NetworkTree();//The tree for the game Network
    private static NetworkNode copy;//A NetworkNode that stores the cut cursor

    /**
     * This is the main method that lets user enter the input an option and perform the corresponding action
     * to the choice. It runs the Nintendo Network Manager.
     */
    public static void main(String[] args)
    {
        Scanner scan=new Scanner (System.in);
        System.out.println("Welcome to the Nintendo Network Manager");
        printMenu();
        System.out.println("Please select an option:");
        String input=scan.nextLine();
        while(!checkOption(input))
        {
            System.out.println("The option you entered does not exist. Please enter a different option.");
            input=scan.nextLine();
        }
        while (!input.equalsIgnoreCase("Q"))
        {
            switch (input.toUpperCase())
            {
                 //Loads a File
                 case "L":
                     optionL(scan);
                     break;
                //Prints the tree
                case "P":
                    optionP();
                    break;
                //Moves the cursor to a specified child in the array
                case "C":
                    optionC(scan);
                    break;
                //Adds a child to the array
                case "A":
                    try
                    {
                        optionA(scan);
                        break;
                    }
                    catch(EmptyTreeException s)
                    {
                        System.out.print("");
                    }
                //Moves the cursor to the parent
                case "U":
                    optionU();
                    break;
                //Cuts/Delete the cursor
                case "X":
                    optionX();
                    break;
                //Moves the cursor to root
                case "R":
                    optionR();
                    break;
                //Saves the tree to a file
                case "S":
                    optionS(scan);
                    break;
                //Moves the cursor to the minimal subtree containing all faults
                case "M":
                    optionM();
                    break;
                //Pastes subtree at the indicated index
                case "V":
                    optionV(scan);
                    break;
                //Mark as broken or fixed
                case "B":
                    optionB();
                    break;
            }
            System.out.println("Please select an option:");
            input=scan.nextLine();
            while(!checkOption(input))
            {
                System.out.println("The option you entered does not exist. Please enter a different option.");
                input=scan.nextLine();
            }
        }
        System.out.println("Make like a tree and leave!");
    }

    /**
     * This method is called when the user wants to print the tree.
     */
    public static void optionP()
    {
        try
        {
            tree.printTree(tree.getRoot());
        }
        catch(EmptyTreeException y)
        {
           System.out.println("There are no NetworkNodes in the tree. Please try adding new NetworkNodes in" +
               "tree before printing it");
        }
    }

    /**
     * This method is called when the user wants to load and create a new NetworkTree for the file entered.
     *
     * @param scan
     *    The scanner used to gather user input
     */
    public static void optionL(Scanner scan)
    {
        System.out.println("Please enter filename:");
        String filename = scan.nextLine();
        try
        {
            tree = tree.readFromFile(filename);
        }
        catch (FileNotFoundException q)
        {
            System.out.print(filename + " not found.");
            return;
        }
    }

    /**
     * This method is called when the user wants to move the cursor to the child to a specific index in the children array.
     *
     * @param scan
     *    A scanner used to gather user input
     */
    public static void optionC(Scanner scan)
    {
        try
        {
            System.out.println("Please enter an index");
            int index=scan.nextInt();
            scan.nextLine();
            checkIndex(index);
            NetworkNode temp=tree.getCursor();
            tree.cursorToChild(index);
            if(tree.getRoot()!=null && tree.getCursor()!=null && temp.getChildren()!=null)
            {
                System.out.println("Cursor moved to " + tree.getCursor().getName() + ".");
            }
        }
       catch(EmptyTreeException a)
       {
           System.out.println(a.getMessage());
       }
       catch(InvalidPositionException b)
       {
           System.out.println(b.getMessage());
           optionC(scan);
       }
    }

    /**
     * This method is called when the user wants to add a new children to the NetworkNode the cursor is
     * currently at.
     *
     * @param scan
     *    A scanner that gathers the user input
     */
    public static void optionA(Scanner scan) throws EmptyTreeException
    {
        if(tree.getRoot()==null)
        {
            throw new EmptyTreeException();
        }
        else
        {
            try
            {
                if(tree.getCursor().isNintendo())
                {
                    System.out.println("The current node cannot have a child node. Please move the cursor and try again.");
                }
                else
                {
                    System.out.println("Please enter an index:");
                    int input=scan.nextInt();
                    scan.nextLine();
                    checkIndex(input);
                    System.out.println("Please enter a device name:");
                    String name=scan.nextLine();
                    System.out.println("Is this Nintendo(y/n)?: ");
                    String nintendo=scan.nextLine();
                    NetworkNode newNode=new NetworkNode(name);
                    String type="";
                    if(!nintendo.equalsIgnoreCase("Y") &&!nintendo.equalsIgnoreCase("N"))
                    {
                        System.out.println("The option you entered is invalid. Please try again");
                    }
                    else if (nintendo.equalsIgnoreCase("Y"))
                    {
                        newNode=new NetworkNode(name, true);
                        type="Nintendo";
                    }
                    else
                    {
                        newNode=new NetworkNode(name, false);
                        type="Raspberry";
                    }
                    newNode.setParent(tree.getCursor());
                    tree.addChild(input,newNode);
                    System.out.println( type + " added");
                }
            }
            catch(InvalidPositionException|EmptyTreeException q)
            {
                System.out.println("The index you entered will cause a problem. Please try again.");
                optionA(scan);
            }
        }
    }

    /**
     * This method is called when the user wants to paste the last cut NetworkNode at the current position.
     */
    public static void optionU()
    {
        try {
            tree.cursorToParent();
            System.out.println("Cursor moved to " + tree.getCursor().getName() + ".");
        }
        catch (EmptyTreeException|AtRootException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is called on when the user wants to cut or delete the cursor.
     */
    public static void optionX()
    {
       try
       {
           copy=tree.cutCursor();
           System.out.println(copy.getName() + " cut, cursor is at "  + tree.getCursor().getName());
       }
       catch (EmptyTreeException e)
       {
           System.out.println(e.getMessage());
       }
    }

    /**
     * This method is called when the user wants to paste the last cut NetworkNode at the current position.
     *
     * @param scan
     *    A scanner that gathers the user input
     */
    public static void optionV(Scanner scan)
    {
        try
        {
            System.out.println("Please enter an index: ");
            int input=scan.nextInt();
            scan.nextLine();
            checkIndex(input);
            if(copy==null)
            {
                System.out.println("There is nothing to paste.");
            }
            else
            {
                tree.addChild(input,copy);
                System.out.println(copy.getName() + " pasted as child of " + tree.getCursor().getParent().getName());
            }
        }
        catch(EmptyTreeException r)
        {
            System.out.println(r.getMessage());
        }
        catch (InvalidPositionException r)
        {
           System.out.println(r.getMessage());
           optionV(scan);
        }
    }

    /**
     * This method is called on when the user wants to move the cursor to the root.
     */
    public static void optionR()
    {
        if(tree.getRoot()==null)
        {
            System.out.println("The tree is empty and the root is null");
        }
        else if(tree.getCursor()==tree.getRoot())
        {
            System.out.println("Cursor is at root");
        }
        else
        {
            tree.setCursor(tree.getRoot());
            System.out.println("Cursor moved to " + tree.getRoot().getName() + ".");
        }
    }

    /**
     * This method is called on when the user wants to save the tree to a file.
     *
     * @param scan
     *    A scanner that gathers the user input
     */
    public static void optionS(Scanner scan)
    {
        System.out.println("Please enter a filename: ");
        String file= scan.nextLine();
        try
        {
            tree.writeToFile(tree,file);
            System.out.println("File saved.");
        }
        catch (FileNotFoundException x )
        {
            System.out.println("The file you entered was not found. Please create the file before trying again.");
        }
        catch(EmptyTreeException u)
        {
            System.out.println(u.getMessage());
        }
    }

    /**
     * This method is called on when the user wants to move the cursor to the root of minimal subtree containing
     * all the faults.
     */
    public static void optionM()
    {
        try
        {
            tree.cursorToMinimalBrokenSubtree();
            System.out.println("Cursor moved to "+ tree.getCursor().getName());
        }
        catch(EmptyTreeException x)
        {
            System.out.println("The tree is currently empty. Please try another option.");
        }
    }

    /**
     * This method is called on when the user want to mark the cursor as broken/fixed.
     */
    public static void optionB()
    {
        tree.changeCursorBrokenStatus();
        if(tree.getCursor()!=null && tree.getCursor().isBroken())
        {
            System.out.println(tree.getCursor().getName()+ " marked as broken.");
        }
        else if(tree.getCursor()!=null)
        {
            System.out.println(tree.getCursor().getName()+ " marked as fixed.");
        }
    }

    /**
     * This method checks if the option entered by user is valid.
     *
     * @param input
     *    The option entered by users
     * @return
     *    A boolean that indicates whether the option is valid or not
     */
    public static boolean checkOption(String input)
    {
        String [] options={"l","L","R","r","X","x","V","v","C","c","A","a","S","s","U","u","M","m","B","b",
            "P","p","Q","q"};
        boolean check=false;
        for(int i=0; i< options.length; i++)
        {
            if(options[i].equals(input))
            {
                check=true;
                break;
            }
        }
        return check;
    }

    /**
     * This method prints the menu of options.
     */
    public static void printMenu()
    {
        System.out.println("Menu:\n L) Load from file\n P) Print tree\n C) Move cursor to a child node\n " +
            "R) Move cursor to root\n U) Move cursor up to parent\n A) Add a child\n " +
            "X) Remove/Cut Cursor and its subtree\n V) Paste Cursor and its subtree\n S) Save to file\n " +
            "M) Cursor to root of minimal subtree containing all faults\n B) Mark cursor as broken/fixed\n " +
            "Q) Quit ");
    }

    /**
     * This method checks if the index passed in the parameters is valid.
     *
     * @param input
     *    An int that represents the position in the children array
     * @throws InvalidPositionException
     *    When the index entered is negative or equal to 0 or will cause a hole in the array
     */
    public static void checkIndex(int input) throws InvalidPositionException
    {
        if(tree.getCursor()!=null && input-1> tree.getCursor().getNextAvailableSpace() || input<=0)
        {
            throw new InvalidPositionException();
        }
    }
}
