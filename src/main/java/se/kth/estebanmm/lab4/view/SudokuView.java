package se.kth.estebanmm.lab4.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import se.kth.estebanmm.lab4.model.SudokuUtilities;

public class SudokuView extends VBox {

    private MenuBar menuBar;
    private BorderPane borderPane;
    private GridView gridView;
    private FlowPane leftPanel;
    private Button checkButton;
    private Button hintButton;

    private int nextNumber;

    public SudokuView(){
        super();
        this.setFillWidth(true);
        generateMenu();
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));
        gridView = new GridView();
        generateLeftPanel();
        TilePane tilePane = gridView.getNumberPane();
        Label[][] numberTiles = gridView.getNumberTiles();
        for(int i=0; i< SudokuUtilities.GRID_SIZE; i++)
            for(int j=0; j< SudokuUtilities.GRID_SIZE; i++){

        }
        GridHandler gridHandler = new GridHandler();
        tilePane.addEventHandler(MouseEvent.MOUSE_CLICKED, gridHandler);
        borderPane.setCenter(tilePane);
        borderPane.setLeft(leftPanel);
        this.getChildren().addAll(menuBar, borderPane);

    }

    private FlowPane generateLeftPanel(){
        checkButton = new Button("Check");
        hintButton = new Button("Hint");
        HintHandler hintHandler = new HintHandler();
        hintButton.addEventHandler(ActionEvent.ACTION, hintHandler);
        leftPanel = new FlowPane(Orientation.VERTICAL);
        leftPanel.getChildren().addAll(checkButton, hintButton);
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setColumnHalignment(HPos.CENTER);
        leftPanel.setPadding(new Insets(5));
        leftPanel.setPrefHeight(200);
        leftPanel.setVgap(8);
        return leftPanel;
    }
    private MenuBar generateMenu(){
        Menu fileMenu = new Menu("File");
        MenuItem loadGame = new MenuItem("Load Game");
        MenuItem saveGame = new MenuItem("Save Game");
        MenuItem exitMenu = new MenuItem("Exit");
        fileMenu.getItems().addAll(loadGame, saveGame, exitMenu);

        Menu gameMenu = new Menu("Game");
        MenuItem resetGame = new MenuItem("Load Game");
        Menu chooseLevel = new Menu("Choose Level");
        MenuItem easy = new MenuItem("Easy");
        MenuItem medium = new MenuItem("Medium");
        MenuItem hard = new MenuItem("Hard");
        chooseLevel.getItems().addAll(easy, medium, hard);
        gameMenu.getItems().addAll(resetGame, chooseLevel);



        Menu helpMenu = new Menu("Help");

        menuBar = new MenuBar(fileMenu, gameMenu, helpMenu);
        return menuBar;
    }

    private class HintHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            nextNumber = 8;
            System.out.println("SIIIII!!!!");

        }
    }
    private class GridHandler implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent event) {
            System.out.println(event.getSource());
        }
    }
}
