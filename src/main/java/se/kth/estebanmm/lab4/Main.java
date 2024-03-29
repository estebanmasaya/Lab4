package se.kth.estebanmm.lab4;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.kth.estebanmm.lab4.model.Board;
import se.kth.estebanmm.lab4.model.FileIO;
import se.kth.estebanmm.lab4.model.SudokuUtilities;
import se.kth.estebanmm.lab4.view.GridView;
import se.kth.estebanmm.lab4.view.SudokuView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        FileIO fileIO = new FileIO();
        Board model = new Board(SudokuUtilities.SudokuLevel.EASY);
        primaryStage.setTitle("Sudoku");
        SudokuView root = new SudokuView(model);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}