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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class MainWindow extends JFrame{
    
    private JButton startButton;
    private JTextField name1;   // Takes first player's name
    private JTextField name2;   // Takes second player's name
    private JLabel message;     //Displays who's turn it is and who won
    private static MainWindow instance = null;
    private JMenuBar menuBar;      // Has the exit item
    private JMenu menu;
    private JMenuItem exit;
    private int nameCount = 0;
    
    
    public static void displayMessage(String message){
        
        if(instance != null){
            instance.message.setText(message);
        }
    }
    
    
    public MainWindow(){
        
        // TO DO : make background text for the textfields, enable button after both names entered (wihout having to press enter)
        
        
        // Instantiating components
        
        super("TicTacToe!");
        setSize(640, 480);
        instance = this;
        
        name1 = new JTextField();
        name2 = new JTextField();
        message = new JLabel();
        
        // Menu
        
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        exit = new JMenuItem("Exit");
        
        menu.setPreferredSize(new Dimension(640,50));
        menu.getAccessibleContext().setAccessibleDescription("Exit the game.");
        
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            } 
        });
    
        menu.add(exit);
        menuBar.add(menu);
        super.setJMenuBar(menuBar);
        
        // Textfield and Label handling 
        
        setFocusable(true);
        
        message.setText("Welcome to TicTacToe!");
        
//        name1.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(!name1.getText().equals("") && !name2.getText().equals("")){
//                    startButton.setEnabled(true);
//                }
//            }
//            
//        });
//        
//        name2.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(!name1.getText().equals("") && !name2.getText().equals("")){
//                    startButton.setEnabled(true);
//                }
//            }
//            
//        });
        
            // Alternative for activating startButton
        
        name1.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                // Empty
            }

            @Override
            public void keyPressed(KeyEvent e) {
                ++nameCount;
                if(nameCount >= 2 && !name1.getText().equals("") && !name2.getText().equals("")){
                    startButton.setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
            
        });
        
         name2.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                // Empty
            }

            @Override
            public void keyPressed(KeyEvent e) {
                ++nameCount;
                if(nameCount >= 2 && !name1.getText().equals("") && !name2.getText().equals("")){
                    startButton.setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
            
        });
        
        
        
        // Button action
        
        startButton = new JButton("Start game");
        startButton.setEnabled(false);
        
        
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                TicTacToe game = new TicTacToeGUI(name1.getText(), name2.getText());
                game.play();
//                message.setText(name1.getText() + "'s turn!");
            }
        });
               
       
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(5,1));
        pane.add(name1);
        pane.add(name2); 
        pane.add(startButton);
        pane.add(message);
    }
    
    public static void main(String[] args) {
        
//      TicTacToe game = new TicTacToe();
//      TicTacToe game = new TicTacToeAI();

        MainWindow window = new MainWindow();
        window.setVisible(true);
        
      
//      TicTacToe game = new TicTacToeGUI();
//      game.play();
    }
}
