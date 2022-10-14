package se.kth.estebanmm.lab4.view;

import se.kth.estebanmm.lab4.model.Board;

public class Controller {
    private final Board model;
    private final SudokuView view;

    public Controller(Board model, SudokuView view) {
        this.model = model;
        this.view = view;
    }

    void makeMove(){

        //model.makeMove(Integer.valueOf(view.getNextStringNumber()));
    }
}
