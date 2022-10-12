package se.kth.estebanmm.lab4.model;

import java.util.Arrays;

public class Board {
    private Square[][] board;

    public Board(SudokuUtilities.SudokuLevel difficulty) {
        board = new Square[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        initBoard(difficulty);
    }

    public void initBoard(SudokuUtilities.SudokuLevel difficulty){
        boolean changeableTemp;
        int[][][] tempBoard = SudokuUtilities.generateSudokuMatrix(difficulty);
        for(int i=0; i<SudokuUtilities.GRID_SIZE; i++){
            for(int j=0; j<SudokuUtilities.GRID_SIZE; j++){
                if(tempBoard[i][j][0]==0) changeableTemp=true;
                else changeableTemp=false;
                board[i][j] = new Square(i, j, tempBoard[i][j][0], changeableTemp);
            }
        }
    }

    

    @Override
    public String toString() {
        String info = "Board{";
        for(Square[] row : board){
            for(Square s: row){
                info += s + "\n";
            }
        }

        info += '}';
        return info;
    }
}
