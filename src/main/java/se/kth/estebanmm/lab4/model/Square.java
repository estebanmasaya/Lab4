package se.kth.estebanmm.lab4.model;

import java.io.Serializable;

public class Square implements Serializable {
    private final int row;
    private final int column;
    private int value;
    private final boolean changeable;


    /**
     * Creates a new square representing a place in the sudoku grid
     * @param row - row of the square
     * @param column - column of the square
     * @param value - value of the square
     * @param changeable - if the square is changeable
     */
    public Square(int row, int column, int value, boolean changeable) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.changeable = changeable;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getValue() {
        return value;
    }

    /**
     * Checks if the square is changeable. The starting numbers on the board are NOT changeable
     * @return - A boolean representing if the square is changeable
     */
    public boolean isChangeable() {
        return changeable;
    }

    /**
     * Sets the value of a square
     * @param value - the value to change it to
     */
    public void setValue(int value) {
        if(isChangeable()){
            this.value = value;
        }
    }
    /**
     * Gives a visual representation of a square
     * @return - returns a string of showing the square
     */
    @Override
    public String toString() {
        return "Square{" +
                "r=" + row +
                ", c=" + column +
                ", value=" + value +
                ", change=" + changeable +
                '}';
    }
}
