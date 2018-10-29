/* ----------------------------------------------------------------------------
- This is a skeleton of a tic tac toe game using the console.
- This current project is not really object oriented. 
- The project has bugs to fix!
- Next week, we will make a user interface to make it easier to play!!

To get started with coding, you can look at the TODO list below...

TODO:
1- Bugs in the code - Fix them!
 - The game doesn't detect tie games
 - The game doesn't handle the "/" diagonal
 - The winner is the wrong name

2- Create a class TicTacToe that will manage the board for a single game
 - The constructor will take the name of the 2 players
 - When the game ends, give the option to the player to play another game

3- To play against the computer, create a class TicTacToeAI extending TicTacToe
 - The computer plays "dumb" and chooses a random available cell each turn

For each exercise, try to write the minimal amount of code, and reuse as much
as possible from the current project.

Author: Nicolas Bergeron
Date:   22/08/2017
---------------------------------------------------------------------------- */

package tictactoegame;

import java.util.Scanner;

public class TicTacToeGame {
    private static Scanner kb = new Scanner(System.in);

    public static TicTacToe initializeGame()
    {
        System.out.print("Enter name for player 1: ");
        String namePlayer1 = kb.next();
        System.out.print("Enter name for player 2: ");
        String namePlayer2 = kb.next();              
        
        return new TicTacToe(namePlayer1, namePlayer2);
    }

    public static TicTacToe initializeGameAI()
    {
        System.out.print("Enter name for player 1: ");
        String namePlayer1 = kb.next();

        return new TicTacToeAI(namePlayer1);        
    }
            

    public static void main(String[] args) {
        boolean playAgain = true;
        
        while (playAgain)
        {
            TicTacToe game = initializeGameAI(); // this decides whether its vs the AI or the player 2 

            while(game.isGameFinished() == false)
            {
                game.displayBoard();
                game.playTurn();
            }

            game.displayBoard();

            if (game.isGameTied())
            {
                System.out.println("The game is tied!!");
            }        
            else
            {
                System.out.println(game.getLastPlayerName() + " has won the game!");
            }
            
            System.out.println("Would you like to play again (Y/N)? ");
            String answer = kb.next();
            answer = answer.toUpperCase();
            
            playAgain = answer.equals("Y");

        }
        
    }
}
