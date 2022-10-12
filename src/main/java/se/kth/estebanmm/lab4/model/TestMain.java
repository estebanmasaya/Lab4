package se.kth.estebanmm.lab4.model;

import javafx.scene.layout.BorderPane;

public class TestMain {

    public static void main(String[] args) {
        Board b = new Board(SudokuUtilities.SudokuLevel.HARD);
        System.out.println(b.toString());

    }
}
