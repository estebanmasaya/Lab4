package se.kth.estebanmm.lab4.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SudokuUtilities implements Serializable {

    public enum SudokuLevel {EASY, MEDIUM, HARD}
    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    private Square [][] solution;
    private SudokuLevel level;

    public SudokuUtilities (SudokuUtilities.SudokuLevel level){
        solution = initBoard(level);
        this.level = level;
    }
    /**
     * Initializes a 9x9 board with a certain difficulty
     * @param level the level of difficulty (easy, medium, hard)
     */
    public Square[][] initBoard(SudokuUtilities.SudokuLevel level){
        Square[][] board = new Square[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        Square[][] solution = new Square[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
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
        return board;
    }


    /**
     * Checks if numbers added are correctly placed
     * @return - returns a boolean representing if the numbers are correct at the moment
     */
    public boolean check(Square[][] board) {
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
    public void hint(Square[][] board) {
        ArrayList<Square> emptySquares = getEmptySquares(board);
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
    public ArrayList<Square> getEmptySquares(Square[][] board){
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
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param level The level, i.e. the difficulty, of the initial standing.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is not 2*81 characters and
     *                                  for characters other than '0'-'9'.
     */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        String representationString;
        switch (level) {
            case EASY: representationString = easy; break;
            case MEDIUM: representationString = medium; break;
            case HARD: representationString = hard; break;
            default: representationString = medium;
        }

        return shuffleMatrix(convertStringToIntMatrix(representationString));
    }

    /**
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param stringRepresentation A string of 2*81 characters, 0-9. The first 81 characters represents
     *                             the initial values, '0' representing an empty cell.
     *                             The following 81 characters represents the solution.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
     * @throws IllegalArgumentException if the length of stringRepresentation is not 2*81 characters and
     *                                  for characters other than '0'-'9'.
     */
    /*package private*/
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " + stringRepresentation.length());

        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();

        int charIndex = 0;
        // initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        // solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] =
                        convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        return values;
    }

    /**
     * Mirrors the grid horizontally
     * @param original - The original grid to be mirrored
     * @return - returns the mirrored 3 dimensional grid of integers
     */
    private static int[][][] mirrorHorizontal(int[][][] original){
        int[][][] newMatrix = new int[GRID_SIZE][GRID_SIZE][2];
        for(int i=0; i < GRID_SIZE; i++){
            for(int j=0; j < GRID_SIZE; j++){
                for(int k=0; k < 2; k++) {
                    newMatrix[i][j][k] = original[GRID_SIZE-i-1][j][k];
                }
            }
        }
        return newMatrix;
    }

    /**
     * Mirrors the grid vertically
     * @param original - The original grid to be mirrored
     * @return - returns the mirrored 3 dimensional grid of integers
     */
    private static int[][][] mirrorVertical(int[][][] original){
        int[][][] newMatrix = new int[GRID_SIZE][GRID_SIZE][2];
        for(int i=0; i < GRID_SIZE; i++){
            for(int j=0; j < GRID_SIZE; j++){
                for(int k=0; k < 2; k++) {
                    newMatrix[i][j][k] = original[i][GRID_SIZE-j-1][k];
                }
            }
        }
        return newMatrix;
    }

    /**
     * Changes the position of the numbers with each other while being in the coordinates
     * @param matrix - the orignal grid to change
     * @param numA - the first number to change place
     * @param numB - the second number to change place
     * @return - returns the fliped 3 dimensional grid of integers
     */
    private static int[][][] flipNumbers(int[][][] matrix, int numA, int numB){
        for(int i=0; i < GRID_SIZE; i++){
            for(int j=0; j < GRID_SIZE; j++){
                for(int k=0; k < 2; k++) {
                    if(matrix[i][j][k]==numA){
                        matrix[i][j][k] = numB;
                    }
                    else if(matrix[i][j][k]==numB){
                        matrix[i][j][k] = numA;
                    }
                }
            }
        }
        return matrix;
    }

    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " + ch);
        return ch - '0';
    }

    /**
     * Shuffles a grid in a random manner
     * @param matrix - the grid to be shuffled
     * @return - returns a new shuffled grid
     */
    private static int[][][] shuffleMatrix(int [][][] matrix){
        for(int i=0; i<8 ; i++){
            int randomA = (int)((Math.random()*GRID_SIZE)+1);
            int randomB = (int)((Math.random()*GRID_SIZE)+1);
            matrix = flipNumbers(matrix, randomA, randomB);
        }
        int randomHorizontal = (int)(Math.random()*2);
        int randomVertical = (int)(Math.random()*2);
        if(randomHorizontal==1){
            matrix = mirrorHorizontal(matrix);
        }
        if(randomVertical==1){
            matrix = mirrorVertical(matrix);
        }
        return matrix;
    }
    private static final String easy =
                    "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" + // solution values after this substring
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" + //trean här var en 2 från början, men det var felaktig enligt Sudoku reglerna.
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";

    private static final String medium =
                    "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" +
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";

    private static final String hard =
            "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" +
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";
}
