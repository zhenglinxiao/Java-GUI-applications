/*
 

Author: Jose Fernandez & Lin Xiao Zheng
Date: 16 September 2018
------------------------------------------------------------------------------*/

package minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MainMenu extends JFrame {
    
    protected JLabel message;
    protected JLabel gamesWon;
    protected JLabel gamesLost;
    protected static JButton newGame;
    private static MainMenu instance = null;
    protected static int winCount = 0;
    protected static int lossCount = 0;
    
    public MainMenu(){
        
        // Initialization of window
        super("Mine Sweeper Menu!");
        setVisible(true);
        setSize(500,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Declare Singleton
        instance = this;
        
        // Initialization of the container
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());        
        
        // Initializing components
        message = new JLabel("Press 'New Game' to start!");
        message.setHorizontalAlignment(JLabel.CENTER);        
        gamesWon = new JLabel("Games Won: 0");
        gamesLost = new JLabel("Games Lost: 0");
        newGame = new JButton("New Game");

        // Action for newGame button
        newGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                // Create a game
                MineSweeperGUI game = new MineSweeperGUI();
                displayMessage("Game in Progress...");
                
                // Disable new game button during a game
                newGame.setEnabled(false);
            }
        });    
        
        // Adding elements to window
        pane.add(message, BorderLayout.PAGE_END);
        pane.add(gamesWon, BorderLayout.LINE_START);
        pane.add(gamesLost, BorderLayout.LINE_END);
        pane.add(newGame, BorderLayout.CENTER);        
    }
    
    
    

    public static void main(String[] args) {
        MainMenu window = new MainMenu();
    }

    public static void displayMessage(String msg){
        if(instance != null){
            instance.message.setText(msg);
        }
    }
    
    public static void addWin(){
        ++winCount;
        if(instance != null){
            instance.gamesWon.setText("Games Won: " + winCount);
        }
    }
    
    public static void addLoss(){
        ++lossCount;
        if(instance != null){
            instance.gamesLost.setText("Games Lost: " + lossCount);
        }
    }    
    
}
