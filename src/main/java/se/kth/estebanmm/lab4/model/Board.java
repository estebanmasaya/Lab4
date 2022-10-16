package se.kth.estebanmm.lab4.model;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private Square[][] board;
    private Square[][] solution;

    private SudokuUtilities.SudokuLevel level;

    public Board(SudokuUtilities.SudokuLevel level) {
        solution = new Square[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        initBoard(level);
    }

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
    public boolean makeMove(int row, int column, int value) {
        Square square = board[row][column];
        if (square.isChangeable()){
            square.setValue(value);
            return true;
        }
        return false;
    }

    public boolean makeMove(Square square, int value){
        if (square.isChangeable()){
            square.setValue(value);
            return true;
        }
        return false;
    }

    //KAN VI RETURNERA HELA BOARD HÄR, ELLER BORDE VI CLONeA DEN?
    public Square[][] getBoard() {
        return board;
    }

    public SudokuUtilities.SudokuLevel getLevel() {
        return level;
    }

    public void clearBoard() {
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if (board[i][j].isChangeable()) {
                    board[i][j].setValue(0);
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
        ArrayList<Square> emptySquares = getEmptySquares();
        if(emptySquares.size()!=0){
            int value = (int)(Math.random()*emptySquares.size());
            int row = emptySquares.get(value).getRow();
            int column = emptySquares.get(value).getColumn();
            emptySquares.get(value).setValue(solution[row][column].getValue());
            System.out.println("row: " + row + "\n" + "column: " + column + " rand: " + value + " solution:" + solution[row][column].getValue() + " emptySquSize: " + emptySquares.size());
        }


    }

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

    public void clearSquare(int row, int column) {
        if (board[row][column].isChangeable()) {
            board[row][column].setValue(0);
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
    public String isChangeableToMatrix(){
        String info = "";
        for(Square[] row : solution){
            for(Square s: row){
                info += s.isChangeable() + " ";
            }
            info+= "\n";
        }

        info += '}';
        return info;
    }



}
