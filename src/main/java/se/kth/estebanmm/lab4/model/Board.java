package se.kth.estebanmm.lab4.model;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

public class Board implements Serializable {
    private Square[][] board;
    private SudokuUtilities.SudokuLevel level;
    private SudokuUtilities utilities;
    /**
     * Creates and initializes a 9x9 board with a certain difficulty
     * @param level the level of difficulty (easy, medium, hard)
     */
    public Board(SudokuUtilities.SudokuLevel level) {
        utilities = new SudokuUtilities(level);
        board = utilities.initBoard(level);
        this.level = level;
    }

    public SudokuUtilities getUtilities() {
        return utilities;
    }

    /**
     * Allows a number to be added to the board
     * @param square - which square to add a number 1-9 to
     * @param value - which number to add
     */
    public void makeMove(Square square, int value){
        square.setValue(value);
    }
    public Square[][] getBoard() {
        return board;
    }

    public SudokuUtilities.SudokuLevel getLevel() {
        return level;
    }
    /**
     * Clears the board to the initial board that was created
     */
    public void clearBoard() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (board[i][j].isChangeable()) {
                    board[i][j].setValue(0);
                }
            }
        }
    }

    public boolean checkIfCorrect(){
        return utilities.check(board);
    }

    public void hintHelper(Square [][] board){
        utilities.hint(board);
    }

    /**
     * Checks if the game is completed
     * @return - returns a boolean representing if the game is completed
     */
    public boolean isGameCompleted(){
        if(utilities.getEmptySquares(board).size()==0) return true;
        return false;
    }
    /**
     * Gives a better visualized representation of the board
     * @return - returns a string of the board
     */
    @Override
    public String toString() {
        String info = "";
        for(Square[] row : board){
            for(Square s: row){
                info += s.getValue() + " ";
            }
            info+= "\n";
        }
        info += '}';
        return info;
    }
}
