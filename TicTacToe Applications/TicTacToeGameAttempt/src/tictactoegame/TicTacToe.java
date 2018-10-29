package tictactoegame;

import java.util.Scanner;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class TicTacToe extends JFrame{
    
    
    protected char[][] board = {{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}};
    private Scanner kb = new Scanner(System.in);
    protected String namePlayer1 = "";
    protected String namePlayer2 = "";
    protected boolean isNextMovePlayer1 = true;
    
    public TicTacToe(String namePlayer1, String namePlayer2)
    {
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
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
        // Initialize data for this turn
        // - Get player name
        // - Get player symbol
        // - Set next turn to other player
        String playerName;
        char playerSymbol;
        
        if (isNextMovePlayer1)
        {
            playerName = namePlayer1;
            playerSymbol = 'X';
        }
        else
        {
            playerName = namePlayer2;
            playerSymbol = 'O';
        }
        
        isNextMovePlayer1 = !isNextMovePlayer1;
         
        
        // Interactive prompt to ask player for coordinate to play
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
