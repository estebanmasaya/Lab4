package se.kth.estebanmm.lab4.model;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

public class Board implements Serializable {
    private Square[][] board;
    private final Square[][] solution;

    private SudokuUtilities.SudokuLevel level;
    /**
     * Creates and initializes a 9x9 board with a certain difficulty
     * @param level the level of difficulty (easy, medium, hard)
     */
    public Board(SudokuUtilities.SudokuLevel level) {
        solution = new Square[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        initBoard(level);
    }
    /**
     * Initializes a 9x9 board with a certain difficulty
     * @param level the level of difficulty (easy, medium, hard)
     */
    public void initBoard(SudokuUtilities.SudokuLevel level){
        this.level = level;
        board = new Square[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        boolean changeableTemp;
        int[][][] tempBoard = SudokuUtilities.generateSudokuMatrix(level);
        for(int i=0; i<SudokuUtilities.GRID_SIZE; i++){
            for(int j=0; j<SudokuUtilities.GRID_SIZE; j++){
                if(tempBoard[i][j][0]==0) changeableTemp=true;
                else changeableTemp=false;
                board[i][j] = new Square(i, j, tempBoard[i][j][0], changeableTemp);
                solution[i][j] = new Square(i, j, tempBoard[i][j][1], changeableTemp);
            }
        }
    }

    /**
     * Allows a number to be added to the board
     * @param square - which square to add a number 1-9 to
     * @param value - which number to add
     */
    public void makeMove(Square square, int value){
        square.setValue(value);
    }

    public void makeMove(int row, int col, int value){
        board[row][col].setValue(value);
    }

    public Square[][] getBoard() {
        Square[][] newBoard = new Square[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        for(int i=0; i<SudokuUtilities.GRID_SIZE; i++){
            for(int j=0; j<SudokuUtilities.GRID_SIZE; j++){
                    newBoard[i][j] = new Square(board[i][j].getRow(), board[i][j].getColumn(), board[i][j].getValue(), board[i][j].isChangeable());
                }
            }
        return newBoard;
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

    /**
     * Checks if numbers added are correctly placed
     * @return - returns a boolean representing if the numbers are correct at the moment
     */
    public boolean checkIfCorrect() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (board[i][j].getValue() != 0 && board[i][j].isChangeable()) {
                    if (board[i][j].getValue() != solution[i][j].getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Allows a random valid number to randomly get placed correctly on the board
     */
    public void hintHelper() {
        ArrayList<Square> emptySquares = getEmptySquares();
        if(emptySquares.size()!=0){
            int value = (int)(Math.random()*emptySquares.size());
            int row = emptySquares.get(value).getRow();
            int column = emptySquares.get(value).getColumn();
            emptySquares.get(value).setValue(solution[row][column].getValue());
        }
    }
    /**
     *
     * @return - returns an array of Squares containing the empty squares
     */
    public ArrayList<Square> getEmptySquares(){
        ArrayList<Square> emptySquares = new ArrayList<>();
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (board[i][j].getValue() == 0) {
                    emptySquares.add(board[i][j]);
                }
            }
        }
        return  emptySquares;
    }
    /**
     * Checks if the game is completed
     * @return - returns a boolean representing if the game is completed
     */
    public boolean isGameCompleted(){
        if(getEmptySquares().size()==0) return true;
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
