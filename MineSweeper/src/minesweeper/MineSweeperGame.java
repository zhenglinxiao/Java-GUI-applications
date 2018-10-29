/*
Author: Jose Fernandez
Date: 16 September 2018
------------------------------------------------------------------------------*/
package minesweeper;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MineSweeperGame {

    private Random rand = new Random();
    protected int[][] board = new int[8][8];

    // Constructor
    public MineSweeperGame() {
        setBoard();
    }

    // Method to initialize the board
    
    public void setBoard() {

        // Setting the mines
        
        for (int i = 0; i < 10; ++i) {

            int row = rand.nextInt(8);
            int column = rand.nextInt(8);

            while (board[row][column] == -1) {
                row = rand.nextInt(8);
                column = rand.nextInt(8);
            }

            board[row][column] = -1;
            
            // Go from one on top to one under

            for (int j = row - 1; j <= row + 1; j++) {

                // Go from one to the left to one to the right
                
                for (int k = column - 1; k <= column + 1; k++) {

                    if (j >= 0 && j < 8 && k >= 0 && k < 8 && board[j][k] != -1) {
                        ++board[j][k];
                    }
                }
            }
        }

    }

    
    public void displayBoard() {
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        MineSweeperGame game = new MineSweeperGame();
        game.displayBoard();
    }
}
