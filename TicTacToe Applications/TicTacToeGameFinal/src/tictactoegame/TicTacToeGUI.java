package tictactoegame;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class TicTacToeGUI extends TicTacToe{
    JButton[] buttons;
    final private int NUM_BUTTONS = 9;
    
    public TicTacToeGUI()
    {
        super("Player1", "Player2");
    }
    
    public TicTacToeGUI(String n1, String n2){
        super(n1, n2);
    }

    @Override
    public void play()
    {
        JFrame frame = new JFrame("Tic Tac Toe!");
        frame.setSize(900, 900);
        frame.setLocation(800, 100);
        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(3, 3));
        
        MainWindow.displayMessage(playerName + "'s turn!");
        
        buttons = new JButton[NUM_BUTTONS];
        for (int i=0; i<NUM_BUTTONS; ++i)
        {
            // Only constant variables can be accesssed in the method bellow 
            final int row = i % 3;      // from 0 to 8
            final int column = i / 3;   // from 0 to 8
            
            /* Values of i for each case
            0 3 6  --> row 0
            1 4 7  --> row 1
            2 5 8  --> row 2
            */
            
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Lucida", Font.BOLD, 45));
            buttons[i].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae) {
                    playTurn(row, column);
                }
            });
            frame.add(buttons[i]);
        }
        
        frame.setVisible(true);
    }
    
    @Override
    public void playTurn(int row, int column)
    {
        char playerSymbol;
        
        // Once the button is clicked, we want the character to be displayed to be the character of the current player,
        // and, we want the message to say that it is now the next player's turn. Otherwise, the message would still display the current player, while the next
        // player is choosing his coordinates.

        if (isNextMovePlayer1)
        {
            playerSymbol = 'X';
            playerName = namePlayer1;
            MainWindow.displayMessage(namePlayer2 + "'s turn!"); 
        }
        else
        {
            playerSymbol = 'O';
            playerName = namePlayer2;
            MainWindow.displayMessage(namePlayer1 + "'s turn!");
        }

        buttons[row + 3*column].setEnabled(false);              // gives you the button number
        buttons[row + 3*column].setText("" + playerSymbol);
        
        setBoard(playerSymbol, row, column);
        isNextMovePlayer1 = !isNextMovePlayer1;
        
        // Disable all tiles if the game is finished
        if (isGameFinished())
        {
            for (int i=0; i<NUM_BUTTONS; ++i)
            {
                buttons[i].setEnabled(false);
            }
            
            if(isGameWon()){
                MainWindow.displayMessage("The winner is " + playerName + "!");
            }
            
            else if(isGameTied()){
                MainWindow.displayMessage("The game is tied!!");
            }
            
        }
    }    
}
