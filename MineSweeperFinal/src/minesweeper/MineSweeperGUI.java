/*
 

Authors: Jose Fernandez & Lin Xiao Zheng
Date: 16 September 2018
------------------------------------------------------------------------------*/

package minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
public class MineSweeperGUI extends JFrame implements KeyListener {
    
    protected JButton[] buttons = new JButton[64];
    protected boolean ctrlPressed = false;
    protected static boolean DEBUG_MODE = false;
    protected int clickCount = 0;    
    protected File winSound;
    protected File lossSound; 
    protected MineSweeperGame game = new MineSweeperGame();
    private final int BOARD_SIDELENGTH = 8;
    private final int NUMBER_BUTTONS = 64;
  
    public MineSweeperGUI(){
        
        // Initialization of window
        super("Mine Sweeper Game!");
        setVisible(true);
        setSize(800, 800);
        this.setLocation(600, 200);
        setFocusable(true);

        // Initialization of container
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(BOARD_SIDELENGTH,BOARD_SIDELENGTH));

        // Creating buttons array
        for (int i = 0; i < NUMBER_BUTTONS; i++) {

            int row = i % BOARD_SIDELENGTH;
            int column = i / BOARD_SIDELENGTH;
            int current = i;

            // Create button
            buttons[current] = new JButton();
            
            // Display mine placements and numbers if debug mode is activated
            if(DEBUG_MODE){
                buttons[current].setText("" + game.board[row][column]);
            }

            // Add key and action listeners to the button
            buttons[current].addKeyListener(this);
            buttons[current].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    // Tag/Untag a mine
                    
                    if (ctrlPressed) {
                        
                        if(!buttons[current].getText().equals("Mine!")){
                            buttons[current].setText("Mine!");
                        }
                        else{
                            if(DEBUG_MODE)
                                buttons[current].setText("" + game.board[row][column]);
                            else
                                buttons[current].setText("");
                        }
                    } 
                    
                    // Normal button click
                    
                    else{
                        
                        // Add to clickCount
                        
                        ++clickCount;
                        
                        
                        // Has the game ended?

                        // Losing condition
                        
                        if(game.board[row][column] == -1){
                            
                            // Update main menu
                            MainMenu.displayMessage("BOOM! You lose! Press \'New Game\' to play again!");
                            MainMenu.addLoss();
                            
                            // Playing losing sound
                            lossSound = new File("Explosion.wav");
                            
                            try {
                                AudioInputStream ais = AudioSystem.getAudioInputStream(lossSound);
                                Clip clip = AudioSystem.getClip();
                                clip.open(ais);
                                clip.start();
                            } catch (UnsupportedAudioFileException ex) {
                                Logger.getLogger(MineSweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MineSweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (LineUnavailableException ex) {
                                Logger.getLogger(MineSweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                       
                            // Display rest of board
                            for(int k = 0; k < NUMBER_BUTTONS; ++k){
                                
                                int row = k % BOARD_SIDELENGTH;
                                int column = k / BOARD_SIDELENGTH;
                                
                                switch (game.board[row][column]) {
                                    case -1:
                                        buttons[row+column*BOARD_SIDELENGTH].setText("Mine!");break;
                                    case 0: 
                                        buttons[row+column*BOARD_SIDELENGTH].setText("");break;
                                    default:
                                        buttons[row+column*BOARD_SIDELENGTH].setText("" + game.board[row][column]);break;
                                }
                                
                                buttons[row+column*BOARD_SIDELENGTH].setEnabled(false);
                            }
                            
                            // Re-enable new game button to play again
                            MainMenu.newGame.setEnabled(true);
                        }
                        
                        // Winning condition
                        
                        else if(clickCount == 54){
                            
                            // Update main menu
                            MainMenu.displayMessage("You won, you expert bomb defuser you! Press \'New Game\' to play again!");
                            MainMenu.addWin();  
                            
                            // Playing winning sound
                            winSound = new File("yay.wav");
                            
                            try {
                                AudioInputStream ais = AudioSystem.getAudioInputStream(winSound);
                                Clip clip = AudioSystem.getClip();
                                clip.open(ais);
                                clip.start();
                            } catch (UnsupportedAudioFileException ex) {
                                Logger.getLogger(MineSweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MineSweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (LineUnavailableException ex) {
                                Logger.getLogger(MineSweeperGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            // Display remaining mines
                            for(int k = 0; k < NUMBER_BUTTONS; ++k){
                                
                                int row = k % BOARD_SIDELENGTH;
                                int column = k / BOARD_SIDELENGTH;
                                
                                if(game.board[row][column] == -1) {
                                        buttons[row+column*BOARD_SIDELENGTH].setText("Mine!");
                                }
                                
                                buttons[row+column*BOARD_SIDELENGTH].setEnabled(false);
                            }  
                            
                            // Re-enable new game button to play again
                            MainMenu.newGame.setEnabled(true);                        
                        } 
                        
                        // If game is still in progress, continue with game mechanics.
                        
                        // Clicking around a zero
                        
                        else if(game.board[row][column] == 0){
                            
                            buttons[current].setEnabled(false);
                            buttons[current].setText("");
                            
                            // Click buttons around current
                            
                            // Go from one on top to one under
                            for (int j = row - 1; j <= row + 1; j++) {
                                
                                // Go from one to the left to one to the right
                                for (int k = column - 1; k <= column + 1; k++) {
                                    
                                    if (j >= 0 && j < BOARD_SIDELENGTH && k >= 0 && k < BOARD_SIDELENGTH) {
                                        
                                        if(buttons[j+k*BOARD_SIDELENGTH].isEnabled()){
                                            buttons[j+k*BOARD_SIDELENGTH].doClick(1);
                                        }
                                    }
                                }
                            }   
                        }
                        
                        // Click one button and display number of mines around it
                        
                        else{
                            
                            buttons[current].setText("" + game.board[row][column]);
                            buttons[current].setEnabled(false);
                        }   
                    }
                }
            });
            
            // Adding the current button to the game window
            
            pane.add(buttons[current]);
        }        
    }

    
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        ctrlPressed = ke.isControlDown();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        ctrlPressed = ke.isControlDown();
    }    
}
