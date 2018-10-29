/*
 

Author: Jose Fernandez
Date: 16 September 2018
------------------------------------------------------------------------------*/
package minesweeper;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MineSweeperGUI extends JFrame implements KeyListener {

    protected JButton[][] buttons = new JButton[8][8];
    protected boolean crtlPressed = false;
    protected static boolean DEBUG_MODE = true;
    protected int clickCount = 0;

    public MineSweeperGUI() {

        // Initialization of window
        super("Mine Sweeper Game!");
        setVisible(true);
        setSize(800, 800);
        this.setLocation(600, 200);
        setFocusable(true);

        // Instantiate the board logic
        MineSweeperGame game = new MineSweeperGame();

        // Making window
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(8, 8));

        // Creating buttons array
        for (int i = 0; i < 64; i++) {

            // Creating the buttons and placing them
            JButton tile = new JButton();

            int row = i % 8;
            int column = i / 8;

            buttons[row][column] = tile;

            if (DEBUG_MODE) {
                tile.setText("" + game.board[row][column]);
            }

            // Adding action listeners to the buttons
            tile.addKeyListener(this);

            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    // Tagging a mine
                    if (crtlPressed) {
                        if (!tile.getText().equals("MINE!")) {
                            tile.setText("MINE!");
                        } else {
                            tile.setText("");
                        }
                    } else {

                        // Add to clickCount
                        ++clickCount;

                        // Clicking around a zero
                        if (game.board[row][column] == 0) {

                            // Disable the zero that was clikced 
                            tile.setText("");
                            tile.setEnabled(false);

                            // Click around the button
                            // Go from one on top to one under
                            for (int j = row - 1; j <= row + 1; j++) {

                                // Go from one to the left to one to the right
                                for (int k = column - 1; k <= column + 1; k++) {

                                    if (j >= 0 && j < 8 && k >= 0 && k < 8) {
                                        if (buttons[j][k].isEnabled()) {
                                            buttons[j][k].doClick(1);
                                        }

                                    }
                                }
                            }
                        } // Normal button click
                        else {

                            tile.setText("" + game.board[row][column]);
                            tile.setEnabled(false);
                        }

                        // Losing condition
                        if (game.board[row][column] == -1) {

                            // Update main menu labels
                            MainMenu.displayMessage("You Lose! Press \'New Game\' to play again!");
                            MainMenu.addLoss();

                            // Display rest of board
                            for (int j = 0; j < 64; j++) {

                                int row = j % 8;
                                int column = j / 8;

                                switch (game.board[row][column]) {
                                    case -1:
                                        buttons[row][column].setText("Mine!");
                                        break;
                                    case 0:
                                        buttons[row][column].setText("");
                                        break;
                                    default:
                                        buttons[row][column].setText("" + game.board[row][column]);
                                        break;
                                }

                                buttons[row][column].setEnabled(false);
                            }

                            // Re-enable new game button
                            MainMenu.newGame(true);
                        } // Winning condition
                        else if (clickCount >= 54) {

                            buttons[row][column].setEnabled(false);

                            MainMenu.addWin();
                            MainMenu.displayMessage("You won, you expert bomb defuser you!");

                            for (int j = 0; j < 64; j++) {

                                int row = j % 8;
                                int column = j / 8;

                                if (game.board[row][column] == -1 && buttons[row][column].isEnabled()) {
                                    buttons[row][column].setText("Mine!");
                                    buttons[row][column].setEnabled(false);
                                }
                            }

                            MainMenu.newGame(true);
                        }
                    }
                }
            });

            // Adding to window
            pane.add(tile);

        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

        crtlPressed = ke.isControlDown();
    }

    @Override
    public void keyReleased(KeyEvent ke) {

        crtlPressed = ke.isControlDown();
    }

}
