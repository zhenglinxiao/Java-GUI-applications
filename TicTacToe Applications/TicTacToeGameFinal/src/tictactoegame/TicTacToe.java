package tictactoegame;

import java.util.Scanner;

public class TicTacToe {
    protected char[][] board = {{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
    private Scanner kb = new Scanner(System.in);
    protected String namePlayer1 = "";
    protected String namePlayer2 = "";
    protected boolean isNextMovePlayer1 = true;
    protected String playerName;
    
    public TicTacToe(String namePlayer1, String namePlayer2)
    {
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2; 
        playerName = namePlayer1;
    }
    
    public void play()
    {
        boolean playAgain = true;
        
        while (playAgain)
        {

            while(isGameFinished() == false)
            {
                displayBoard();
                playTurn();
            }

            displayBoard();

            if (isGameTied())
            {
                System.out.println("The game is tied!!");
            }        
            else
            {
                System.out.println(getLastPlayerName() + " has won the game!");
            }
            
            System.out.println("Would you like to play again (Y/N)? ");
            String answer = kb.next();
            answer = answer.toUpperCase();
            
            playAgain = answer.equals("Y");
        }
    }
    
    public void displayBoard()
    {
        System.out.println();
        
        for (int i=0; i<3; ++i)
        {
            for (int j=0; j<3; ++j)
            {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\n");
    }
    
    public boolean isGameFinished()
    {
        return isGameWon() || isGameTied();
    }
    
    public boolean isGameWon()
    {
        // Test diagonals
        if (board[1][1] != '-')
        {
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2])
                return true;

            if (board[2][0] == board[1][1] && board[1][1] == board[0][2])
                return true;
        }
            
        // Test rows
        for (int i=0; i<3; ++i)
        {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return true;
        }
        
        // Test columns
        for (int j=0; j<3; ++j)
        {
            if (board[0][j] != '-' && board[0][j] == board[1][j] && board[1][j] == board[2][j])
                return true;
        }
        
        return false;
    }
    
    public boolean isGameTied()
    {
        if (isGameWon() == false)
        {
            for (int i=0; i<3; ++i)
            {
                for (int j=0; j<3; ++j)
                {
                    if (board[i][j] == '-')
                    {
                        return false;
                    }
                }
            }
            
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void playTurn()
    {
        // Interactive prompt to ask player for coordinate to play
        
        if (isNextMovePlayer1)
        {
            playerName = namePlayer1;
        }
        else
        {
            playerName = namePlayer2;
        }

        System.out.println(playerName + ", enter the coordinates of your next move (eg: 1 2 is middle-right): ");

        int i = kb.nextInt();
        int j = kb.nextInt();

        // Validate that the requested coordinate is valid
        while(i < 0 || i > 2 || j < 0 || j > 2 || board[i][j] != '-')
        {
            System.out.println("Invalid coordinate, enter the coordinates of your next move (eg: 1 2 is middle-right): ");

            i = kb.nextInt();
            j = kb.nextInt();                
        }
        
        playTurn(i, j);
    }
    
    public void playTurn(int i, int j)
    {
        char playerSymbol;
        
        if (isNextMovePlayer1)
        {
            playerSymbol = 'X';
        }
        else
        {
            playerSymbol = 'O';
        }
        
        isNextMovePlayer1 = !isNextMovePlayer1;
        
        setBoard(playerSymbol, i, j);
    }
    
    public void setBoard(char playerSymbol, int row, int column)
    {
        // Assumptions on the coordinate
        assert(row >= 0 && row < 3 && column >= 0 && column < 3);
        
        // Assumptions on the player symbol
        assert(playerSymbol == 'X' || playerSymbol == 'O');
        
        board[row][column] = playerSymbol;        
    }
    
    public String getLastPlayerName()
    {
        if (isNextMovePlayer1 == false)
        {
            return namePlayer1;
        }
        else
        {
            return namePlayer2;
        }
    }

    public String getNextPlayerName()
    {
        if (isNextMovePlayer1)
        {
            return namePlayer1;
        }
        else
        {
            return namePlayer2;
        }
    }
    
    
}
