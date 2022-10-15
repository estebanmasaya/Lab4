package se.kth.estebanmm.lab4.view;

import se.kth.estebanmm.lab4.model.Board;
import se.kth.estebanmm.lab4.model.Square;

import static java.lang.Integer.parseInt;

public class Controller {
    private final Board model;
    private final SudokuView view;

    public Controller(Board model, SudokuView view) {
        this.model = model;
        this.view = view;
    }

    void HandleMove(Square square){
        model.makeMove(square, parseInt(view.getNextStringNumber()));
        view.updateFromModel();
    }
}
