package se.kth.estebanmm.lab4.view;

import se.kth.estebanmm.lab4.model.Board;
import se.kth.estebanmm.lab4.model.Square;
import se.kth.estebanmm.lab4.model.SudokuUtilities;

import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static se.kth.estebanmm.lab4.model.FileIO.loadFile;
import static se.kth.estebanmm.lab4.model.FileIO.saveFile;

public class Controller {
    private Board model;
    private final SudokuView view;

    public Controller(Board model, SudokuView view) {
        this.model = model;
        this.view = view;
    }

    void HandleMove(Square square){
        model.makeMove(square, parseInt(view.getNextStringNumber()));
        view.updateFromModel();
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
        System.exit(0);
    }

    void handleSave(File file, Board board) {
        if(file!=null) {
            try {
                saveFile(file, board);
            } catch (IOException exception) {
                file = view.chooseFileToSave();
                handleSave(file, board);
            }
        }
    }

    void handleLoad(File file) {
        if(file!=null) {
            try {
                Board newModel = loadFile(file);
                view.changeModel(newModel);
                model = newModel;
            } catch (IOException | ClassNotFoundException exception) {
                file = view.chooseFileToLoad();
                handleLoad(file);
            } finally {
                view.updateFromModel();
            }
        }
    }
}
