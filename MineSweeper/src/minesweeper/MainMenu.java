/*


Author: Jose Fernandez
Date: 16 September 2018
------------------------------------------------------------------------------*/
package minesweeper;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class MainMenu extends JFrame{
    
    protected JLabel message;
    protected JLabel gamesWon;
    protected JLabel gamesLost;
    protected static JButton newGame;
    private static MainMenu instance = null;
    protected static int winCount = 0;
    protected static int lossCount = 0;
    
    
    public static void main(String[] args) {
        MainMenu window = new MainMenu();
        window.setVisible(true);
    }
    
    public MainMenu(){
        
        // Initialization of window
        
        super("Mine Sweeper Menu!");
        setSize(500,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        instance = this;
        
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        
        // Initializing components
        
        message = new JLabel("Press \'New Game\' to start!");
        message.setHorizontalAlignment(JLabel.CENTER);
        gamesWon = new JLabel("Games Won: 0");
        gamesLost = new JLabel("Games Lost: 0");
        newGame = new JButton("New Game");
        
        // Action for newGame button
        
        newGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                MineSweeperGUI game = new MineSweeperGUI();
                
                newGame.setEnabled(false);
            }
            
        });
        
        
        
        // Adding elements to window
        
        pane.add(message, BorderLayout.PAGE_END);
        pane.add(gamesWon, BorderLayout.LINE_START);
        pane.add(gamesLost, BorderLayout.LINE_END);
        pane.add(newGame, BorderLayout.CENTER);
        
    }
    
    public static void displayMessage(String message){
        
        if(instance != null){
            instance.message.setText(message);
        }
    }
    
    public static void addWin(){
        
        if(instance != null){
            instance.gamesWon.setText("Games Won: " + ++winCount);
        }
    }
    
     public static void addLoss(){
        
        if(instance != null){
            instance.gamesLost.setText("Games Lost: " + ++lossCount);
        }
    }
     
     public static void newGame(boolean b){
         
         newGame.setEnabled(b);
     }
}
