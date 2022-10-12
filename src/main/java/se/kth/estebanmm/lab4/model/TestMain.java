package se.kth.estebanmm.lab4.model;

import javafx.scene.layout.BorderPane;

import java.util.SortedMap;

public class TestMain {

    public static void main(String[] args) {
        Board b = new Board(SudokuUtilities.SudokuLevel.HARD);
        System.out.println(b.toMatrix());
        for(int i=0; i<60; i++){
            b.hintHelper();
        }
        System.out.println(b.toMatrix());
        System.out.println(b.checkIfCorrect());
        System.out.println("SOLUTION: \n"+ b.solutionToMatrix());


    }
}
