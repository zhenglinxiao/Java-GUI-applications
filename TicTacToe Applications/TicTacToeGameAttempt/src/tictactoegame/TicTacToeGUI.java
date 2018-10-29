package tictactoegame;

import java.util.Scanner;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class TicTacToeGUI extends JFrame{
    
    private JButton topLeft;
    private JButton topMid;
    private JButton topRight;
    private JButton midLeft;
    private JButton midCenter;
    private JButton midRight;
    private JButton bottomLeft;
    private JButton bottomMid;
    private JButton bottomRight;
 
    private String currentSymbol = "X";
    
    private Scanner kb = new Scanner(System.in);
    protected String namePlayer1 = "";
    protected String namePlayer2 = "";
    protected boolean isNextMovePlayer1 = true;
    
    public TicTacToeGUI(String namePlayer1, String namePlayer2)
    {
        super("TicTacToe");
        
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
        
        setSize(900, 900);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Initiating buttons
        
        topLeft = new JButton("Click Here");
        topLeft.setFont(new Font("Tahoma", Font.BOLD, 32));
        topMid = new JButton("Click Here");
        topMid.setFont(new Font("Tahoma", Font.BOLD, 32));
        topRight = new JButton("Click Here");
        topRight.setFont(new Font("Tahoma", Font.BOLD, 32));
        midLeft = new JButton("Click Here");
        midLeft.setFont(new Font("Tahoma", Font.BOLD, 32));
        midCenter = new JButton("Click Here");
        midCenter.setFont(new Font("Tahoma", Font.BOLD, 32));
        midRight = new JButton("Click Here");
        midRight.setFont(new Font("Tahoma", Font.BOLD, 32));
        bottomRight = new JButton("Click Here");
        bottomRight.setFont(new Font("Tahoma", Font.BOLD, 32));
        bottomMid = new JButton("Click Here");
        bottomMid.setFont(new Font("Tahoma", Font.BOLD, 32));
        bottomLeft = new JButton("Click Here");
        bottomLeft.setFont(new Font("Tahoma", Font.BOLD, 32));
        
        
        // Making button event actions
        
        topLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                playTurn(topLeft);
                topLeft.setEnabled(false);
                
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } 
            
        });
        
        topMid.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                playTurn(topMid);
                topMid.setEnabled(false);
                
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        topRight.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                playTurn(topRight);
                topRight.setEnabled(false);
                
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        midLeft.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                playTurn(midLeft);
                midLeft.setEnabled(false);
                
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        midCenter.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                playTurn(midCenter);
                midCenter.setEnabled(false);
                
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        midRight.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                playTurn(midRight);
                midRight.setEnabled(false);
                
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        bottomLeft.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                playTurn(bottomLeft);
                bottomLeft.setEnabled(false);
                
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        bottomMid.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                playTurn(bottomMid);
                bottomMid.setEnabled(false);
                
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        bottomRight.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                playTurn(bottomRight);
                bottomRight.setEnabled(false);
                
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(3,3));
        
        pane.add(topLeft);
        pane.add(topMid);
        pane.add(topRight);
        pane.add(midLeft);
        pane.add(midCenter);
        pane.add(midRight);
        pane.add(bottomLeft);
        pane.add(bottomMid);
        pane.add(bottomRight);    
    }
    
    
    public boolean isGameFinished()
    {
        return isGameWon() || isGameTied();
    }
    
    public boolean isGameWon()
    {
        // Test diagonals
        if (!midCenter.isEnabled()) 
        {
            if (!topLeft.isEnabled() && !bottomRight.isEnabled() && midCenter.getText().equals(topLeft.getText()) && midCenter.getText().equals(bottomRight.getText()))
                return true;

            if (!topRight.isEnabled() && !bottomLeft.isEnabled() && midCenter.getText().equals(topRight.getText()) && midCenter.getText().equals(bottomLeft.getText()))
                return true;
        }
            
        // Test rows
        
        if (!topLeft.isEnabled() && !topRight.isEnabled() && !topMid.isEnabled() && topMid.getText().equals(topLeft.getText()) && topMid.getText().equals(topRight.getText()))
            return true;
        
        if (!midLeft.isEnabled() && !midRight.isEnabled() && !midCenter.isEnabled() && midCenter.getText().equals(midLeft.getText()) && midCenter.getText().equals(midRight.getText()))
            return true;
        
        if (!bottomLeft.isEnabled() && !bottomRight.isEnabled() && !bottomMid.isEnabled() && bottomMid.getText().equals(bottomLeft.getText()) && bottomMid.getText().equals(bottomRight.getText()))
            return true;
        
        
        // Test columns
        
        if (!topLeft.isEnabled() && !midLeft.isEnabled() && !bottomLeft.isEnabled() && midLeft.getText().equals(topLeft.getText()) && midLeft.getText().equals(bottomLeft.getText()))
            return true;
            
        if(!topMid.isEnabled() && !midCenter.isEnabled() && !bottomMid.isEnabled() && midCenter.getText().equals(topMid.getText()) && midCenter.getText().equals(bottomMid.getText()))
            return true;
            
        if(!topRight.isEnabled() && !midRight.isEnabled() && !bottomRight.isEnabled() && midRight.getText().equals(topRight.getText()) && midRight.getText().equals(bottomRight.getText()))
            return true;
            
        
        return false;
    }
    
    public boolean isGameTied()
    {
        if (isGameWon() == false && !topRight.isEnabled() && !topMid.isEnabled() && !topLeft.isEnabled() 
                && !midRight.isEnabled() && !midCenter.isEnabled() && !midLeft.isEnabled()
                && !bottomRight.isEnabled() && !bottomMid.isEnabled() && !bottomLeft.isEnabled())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void playTurn(JButton b)
    {
        // Initialize data for this turn
        // - Get player name
        // - Get player symbol
        // - Set next turn to other player
        String playerName;
        
        if (currentSymbol.equals("X"))
        {
            playerName = namePlayer1;
            b.setText("X");
            currentSymbol = "O";
        }
        else
        {
            playerName = namePlayer2;
            b.setText("O");
            currentSymbol = "X";
        }
        
        isNextMovePlayer1 = !isNextMovePlayer1;
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
    
    public static void main(String[] args) {   
        TicTacToeGUI gameWindow = new TicTacToeGUI("Jose", "Lin");
        gameWindow.setVisible(true);
        
        while(!gameWindow.isGameFinished())
        {
            if(gameWindow.isGameTied()){
                System.out.println("It's a tie!!");
                System.exit(0);
            }
        
            else if(gameWindow.isGameWon()){
                System.out.println(gameWindow.getLastPlayerName() + " has won the game!");
                System.exit(0);
            }
        }
    }
    
    
}

