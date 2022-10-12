package se.kth.estebanmm.lab4.model;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private Square[][] board;
    private Square[][] solution;

    public Board(SudokuUtilities.SudokuLevel difficulty) {
        board = new Square[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        solution = new Square[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
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
                solution[i][j] = new Square(i, j, tempBoard[i][j][1], changeableTemp);
            }
        }
    }
    public boolean makeMove(int row, int column, int value) {
        Square square = board[row][column];
        if (square.isChangeable()){
            square.setValue(row, column, value);
            return true;
        }
        return false;
    }



    public void clearBoard() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (board[i][j].isChangeable()) {
                    board[i][j].setValue(i, j, 0);
                }
            }
        }
    }

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

    public void hintHelper() {
        ArrayList<Square> emptySquares = new ArrayList<>();
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (board[i][j].getValue() == 0) {
                   emptySquares.add(board[i][j]);
                }
            }
        }
        int value = (int)(Math.random()*emptySquares.size());
        int row = emptySquares.get(value).getRow();
        int column = emptySquares.get(value).getColumn();
        emptySquares.get(value).setValue(row, column, solution[row][column].getValue());
        System.out.println("row: " + row + "\n" + "column: " + column + " rand: " + value + " solution:" + solution[row][column].getValue() + " emptySquSize: " + emptySquares.size());


    }

    public void clearSquare(int row, int column) {
        if (board[row][column].isChangeable()) {
            board[row][column].setValue(row, column, 0);
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

    //Ett annat sätt att visa infon, i form av matris. Kan tas bort från slutprojektet när det vyn är klar.
    public String toMatrix(){
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

    //Ett annat sätt att visa solution, i form av matris. Kan tas bort från slutprojektet när det vyn är klar.
    public String solutionToMatrix(){
        String info = "";
        for(Square[] row : solution){
            for(Square s: row){
                info += s.getValue() + " ";
            }
            info+= "\n";
        }

        info += '}';
        return info;
    }

}
