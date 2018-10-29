/*
 

Author: Jose Fernandez & Lin Xiao Zheng
Date: 16 September 2018
------------------------------------------------------------------------------*/

package minesweeper;

import java.util.*;
public class MineSweeperGame {
    
    protected int[][] board = new int[8][8];
    private static Random rand = new Random();
    protected HashSet<Integer> mines = new HashSet(10);
    
    public MineSweeperGame(){
        setBoard();
    }
   
    
    public void setBoard(){ 
        
        for(int k = 0; k < 10; ++k){
            int num = rand.nextInt(64);
            
            if(mines.contains(num)){
                --k;
            }
            mines.add(num);
        }         
        
        for(int i: mines){
            int row = i / 8;
            int column = i % 8;  
            
            // Set mine placements
            board[row][column] = -1;  
            
            // Set mine counts
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
    
}
