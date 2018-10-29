package tictactoegame;

public class TicTacToeAI extends TicTacToe{
    public TicTacToeAI(String namePlayer1)
    {        
        super(namePlayer1, "StupidAI");
    }
    
    @Override
    public void playTurn()
    {
        if (isNextMovePlayer1)
        {
            super.playTurn();
        }
        else // AI playing automatically
        {
            // find empty cell
            int i, j;

            do
            {
                i = (int)(Math.random() * 3);
                j = (int)(Math.random() * 3);
            }
            while (board[i][j] != '-');
            
            isNextMovePlayer1 = !isNextMovePlayer1;
            System.out.println(namePlayer2 + " plays " + i + " " + j);
            setBoard('O', i, j);
        }
        
    }
}
