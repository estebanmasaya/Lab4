package se.kth.estebanmm.lab4.model;

public class Square {
    private int row;
    private int column;
    private int value;
    private final boolean changeable;

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

    public boolean isChangeable() {
        return changeable;
    }

    public void setValue(int row, int column, int value) {
        this.value = value;
    }

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
