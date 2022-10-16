package se.kth.estebanmm.lab4.view;

import javafx.scene.control.Menu;
import se.kth.estebanmm.lab4.model.Board;
import se.kth.estebanmm.lab4.model.Square;
import se.kth.estebanmm.lab4.model.SudokuUtilities;

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

    boolean HandleCheck() {
        return model.checkIfCorrect();
    }

    void HandleHints(){
        model.hintHelper();
        view.updateFromModel();
    }

    void onInitNewGameRoundSelected(SudokuUtilities.SudokuLevel level){
        model.initBoard(level);
        view.updateFromModel();
    }

    void handleReset(){
        model.clearBoard();
        view.updateFromModel();
    }

    void handleExit(){

    }

}
