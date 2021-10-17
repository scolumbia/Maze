
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * A maze game.
 * 
 * @author Sophie Columbia
 * @version 1/23/2020
 */
public class MazeGame
{
    /**
     * The size of each side of the game map.
     */
    private final static int HEIGHT = 19;
    private final static int WIDTH = 39;

    /**
     * The game map, as a 2D array of ints.
     */
    private boolean[][] blocked;
    
    /**
     * A 2D array of visited locations.
     */
    private boolean[][] visitedLocations = new boolean[HEIGHT][WIDTH];
    
    /**
     * The current location of the player vertically.
     */
    // TODO: add field here.
    private int userCol;

    /**
     * The current location of the player horizontally.
     */
    // TODO: add field here.
    private int userRow;

    /**
     * The scanner from which each move is read.
     */
    // TODO: add field here.
    private Scanner moveScanner;

    /**
     * The row and column of the goal.
     */
    // TODO: add fields here.
    private int goalCol;
    private int goalRow;
    
    /**
     * The row and column of the start.
     */
    // TODO: add fields here.
    private int startCol;
    private int startRow;

    /**
     * Constructor initializes the maze with the data in 'mazeFile'.
     * @param mazeFile the input file for the maze
     */
    public MazeGame(String mazeFile)
    {
        // TODO
        loadMaze(mazeFile);
        moveScanner = new Scanner(System.in);
    }

    /**
     * Constructor initializes the maze with the 'mazeFile' and the move 
     * scanner with 'moveScanner'.
     * @param mazeFile the input file for the maze
     * @param moveScanner the scanner object from which to read user moves
     */
    public MazeGame(String mazeFile, Scanner moveScanner)
    {
        // TODO
        loadMaze(mazeFile);
        this.moveScanner = moveScanner;
    }

    /**
     * getMaze returns a copy of the current maze for testing purposes.
     * 
     * @return the grid
     */
    public boolean[][] getMaze()
    {
        if (blocked == null)
        {
            return null;
        }
        boolean[][] copy = new boolean[HEIGHT][WIDTH];
        for (int row = 0; row < HEIGHT; row++)
        {
            for (int col = 0; col < WIDTH; col++)
            {
                copy[row][col] = blocked[row][col];
            }
        }
        return copy;
    }

    /**
     * setMaze sets the current map for testing purposes.
     * 
     * @param maze
     *            another maze.
     */
    public void setMaze(boolean[][] maze)
    {
        this.blocked = maze;
    }
    
    /**
     * getBlocked returns boolean of blocked.
     * 
     * @return the value of blocked
     */
    public boolean[][] getBlocked()
    {
        return blocked;
    }
    
    /**
     * setBlocked sets the field blocked to this argument.
     * 
     * @param blocked a boolean
     */
    public void setBlocked(boolean[][] blocked)
    {
        this.blocked = blocked;
    }
    
    /**
     * Returns value of userCol.
     * 
     * @return vertical value
     */
    public int getUserCol()
    {
        return userCol;
    }
    
    /**
     * Sets value of userCol to argument.
     * 
     * @param userCol new vertical location
     */
    public void setUserCol(int userCol)
    {
        this.userCol = userCol;
    }
    
    /**
     * Returns value of userRow.
     * 
     * @return horizontal value
     */
    public int getUserRow()
    {
        return userRow;
    }
    
    /**
     * Sets value of userRow to argument.
     * 
     * @param userRow new horizontal value
     */
    public void setUserRow(int userRow)
    {
        this.userRow = userRow;
    }
    
    /**
     * Returns the moveScanner.
     * 
     * @return the scanner
     */
    public Scanner getMoveScanner()
    {
        return moveScanner;
    }
    
    /**
     * Sets moveScanner to argument.
     * 
     * @param moveScanner the new scanner
     */
    public void setMoveScanner(Scanner moveScanner)
    {
        this.moveScanner = moveScanner;
    }
    
    /**
     * Function loads the data from the maze file and creates the 'blocked' 
     * 2D array.
     *  
     * @param mazeFile the input maze file.
     */
    public void loadMaze(String mazeFile)
    {
        // TODO: private void loadMaze(String mazeFile)
        blocked = new boolean[HEIGHT][WIDTH];
        try
        {
            File myFile = new File(mazeFile);
            Scanner s = new Scanner(myFile);
            for (int row = 0; row < HEIGHT; row++)
            {
                String wholeRow = s.nextLine();
                for (int col = 0; col < WIDTH * 2; col+=2)
                {
                    char value = wholeRow.charAt(col);
                    
                    if (value == '1')
                    {
                        blocked[row][col/2] = true;
                    }
                    else if (value == '0')
                    {
                        blocked[row][col/2] = false;
                    }
                    else if (value == 'S')
                    {
                        startCol = col/2;
                        startRow = row;
                        setUserCol(startCol);
                        setUserRow(startRow);
                    }
                    else if (value == 'G')
                    {
                        goalCol = col/2;
                        goalRow = row;
                    }
                }
            }
            s.close();
            
        }
        catch (IOException o)
        {
            System.out.println("Something went wrong.");
        }
    }

    /**
     * Actually plays the game.
     */
    public void playGame()
    {
        String userInput = "";
        int moveCount = 0;
        System.out.println(goalRow + " " + goalCol);
        while (!userInput.equals("quit") && !playerAtGoal())
        {
            printMaze();
            System.out.println("Type direction: ");
            userInput = moveScanner.nextLine();
            makeMove(userInput);
            moveCount++;
        }
    }

    /**
     * Checks to see if the player has won the game.
     * @return true if the player has won.
     */
    // TODO: public boolean playerAtGoal()
    public boolean playerAtGoal()
    {
        return (getUserCol() == goalCol && getUserRow() == goalRow);
    }

    /**
     * Makes a move based on the String.
     * 
     * @param move
     *            the direction to make a move in.
     * @return whether the move was valid.
     */
    public boolean makeMove(String move)
    {
        // TODO
        char input = move.toLowerCase().charAt(0);
        if (input == 'u')
        {
            if (validMoveUp())
            {
                setUserRow(getUserRow() - 1);
                visitedLocations[getUserRow()][getUserCol()] = true;
                return true;
            }
        }
        else if (input == 'd')
        {
            if (validMoveDown())
            {
                setUserRow(getUserRow() + 1);
                visitedLocations[getUserRow()][getUserCol()] = true;
                return true;
            }
        }
        else if (input == 'l')
        {
            if (validMoveLeft())
            {
                setUserCol(getUserCol() - 1);
                visitedLocations[getUserRow()][getUserCol()] = true;
                return true;
            }
        }
        else if (input == 'r')
        {
            if (validMoveRight())
            {
                setUserCol(getUserCol() + 1);
                visitedLocations[getUserRow()][getUserCol()] = true;
                return true;
            }
        }
        else if (move.equals("quit"))
        {
            return true;
        }
        return false;
    }
    
    /**
     * Sees if player can move right.
     * 
     * @return if possible
     */
    public boolean validMoveRight()
    {
        int location = getUserCol() + 1;
        return (location <= WIDTH && !blocked[getUserRow()][location]);
    }
    
    /**
     * Sees if player can move left.
     * 
     * @return if possible
     */
    public boolean validMoveLeft()
    {
        int location = getUserCol() - 1;
        return (location >= 0 && !blocked[getUserRow()][location]);
    }
    
    /**
     * Sees if player can move up.
     * 
     * @return if possible
     */
    public boolean validMoveUp()
    {
        int location = getUserRow() - 1;
        return (location >= 0 && !blocked[location][getUserCol()]);
    }
    
    /**
     * Sees if player can move down.
     * 
     * @return if possible
     */
    public boolean validMoveDown()
    {
        int location = getUserRow() + 1;
        return (location <= HEIGHT && !blocked[location][getUserCol()]);
    }

    /**
     * Prints the map of the maze.
     */
    public void printMaze()
    {
        // TODO
        System.out.println("*-----------------"
            + "----------------------*");
        for (int row = 0; row < HEIGHT; row++)
        {
            System.out.print("|");
            for (int col = 0; col < WIDTH; col++)
            {
                if (row == getUserRow() && col == getUserCol())
                {
                    System.out.print('@');
                }
                else if (visitedLocations[row][col] && !blocked[row][col])
                {
                    System.out.print('.');
                }
                else if (blocked[row][col])
                {
                    System.out.print('X');
                }
                else if (!blocked[row][col] && (row != goalRow || col != goalCol))
                {
                    System.out.print(' ');
                }
                else if (row == startRow && col == startCol)
                {
                    System.out.print('S');
                }
                if (row == goalRow && col == goalCol)
                {
                    System.out.print('G');
                }
                
            }
            System.out.println("|");
        }
        System.out.println("*-----------------" 
                        + "----------------------*");
    }

    /**
     * Creates a new game, using a command line argument file name, if one is
     * provided.
     * 
     * @param args the command line arguments
     */

    public static void main(String[] args)
    {
        String mapFile = "hard.txt";
        Scanner scan = new Scanner(System.in);
        MazeGame game = new MazeGame(mapFile, scan);
        game.loadMaze(mapFile);

        game.playGame();
    }
}
