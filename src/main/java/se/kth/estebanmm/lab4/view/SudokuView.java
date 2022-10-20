package se.kth.estebanmm.lab4.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import se.kth.estebanmm.lab4.model.Board;
import se.kth.estebanmm.lab4.model.SudokuUtilities;

import java.io.File;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class SudokuView extends VBox {
    private Board model;
    private MenuBar menuBar;
    private BorderPane borderPane;
    private final GridView gridView;
    private FlowPane leftPanel;
    private FlowPane rightPanel;
    private Button checkButton;
    private Button hintButton;
    private Button[] oneToNine;
    private final VBox containsGrid;
    private String nextStringNumber;

    public SudokuView(Board model){
        super();
        this.model = model;
        Controller controller = new Controller(model, this);
        generateMenu();
        nextStringNumber = "0";
        gridView = new GridView();
        containsGrid = new VBox(gridView.getNumberPane());
        containsGrid.fillWidthProperty();
        generateLeftPanel();
        generateRightPanel();
        borderPane = generateBorderPane(containsGrid, leftPanel, rightPanel);
        this.getChildren().addAll(menuBar, borderPane);
        addEventHandlers(controller);
        updateFromModel();

    }

    private BorderPane generateBorderPane(VBox containsGrid, FlowPane leftPanel, FlowPane rightPanel){
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));
        borderPane.setCenter(containsGrid);
        borderPane.setLeft(leftPanel);
        borderPane.setRight(rightPanel);
        return borderPane;
    }

    void updateFromModel(){
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if(model.getBoard()[i][j].isChangeable()) gridView.getNumberTiles()[i][j].setTextFill(Color.GRAY);
                else gridView.getNumberTiles()[i][j].setTextFill(Color.BLACK);
                if(model.getBoard()[i][j].getValue()==0){
                    gridView.getNumberTiles()[i][j].setText("");
                }
                else {
                    gridView.getNumberTiles()[i][j].setText(Integer.toString(model.getBoard()[i][j].getValue()));
                }
            }
        }
        if(model.isGameCompleted()) checkAlert();
    }

    private void checkAlert(){
        Alert finished = new Alert(Alert.AlertType.INFORMATION);
        if(model.isGameCompleted()){
            finished.setTitle("Sudoku Completed!!");
            if(model.checkIfCorrect()){
                finished.setContentText("Well done!!! \nYour solution is correct!");
            }
            else {
                finished.setContentText("Try again. Your solution was unfortunately wrong!");
            }
        }
        else{
            finished.setTitle("Checking solution...");
            if(model.checkIfCorrect()){
                finished.setContentText("So far your solution is right.\n Keep on with the good work!");
            }
            else {
                finished.setContentText("Theres something wrong with your solution\n Keep on trying.");
            }
        }
        finished.show();
    }

    private FlowPane generateLeftPanel(){
        checkButton = new Button("Check");
        hintButton = new Button("Hint");
        leftPanel = new FlowPane(Orientation.VERTICAL);
        leftPanel.getChildren().addAll(checkButton, hintButton);
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setColumnHalignment(HPos.CENTER);
        leftPanel.setPadding(new Insets(5));
        leftPanel.setPrefHeight(200);
        leftPanel.setVgap(8);
        return leftPanel;
    }

    private FlowPane generateRightPanel(){
        rightPanel = new FlowPane(Orientation.VERTICAL);
        oneToNine = new Button[(SudokuUtilities.GRID_SIZE)+1];
        for(int i=0; i < SudokuUtilities.GRID_SIZE; i++) {
            oneToNine[i] = new Button(Integer.toString(i+1));
        }
        oneToNine[9] = new Button("C");
        rightPanel.getChildren().addAll(oneToNine);
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setColumnHalignment(HPos.CENTER);
        rightPanel.setPadding(new Insets(5));
        rightPanel.setPrefHeight(200);
        rightPanel.setVgap(2);
        return rightPanel;
    }
    
    private MenuBar generateMenu(){
        Menu fileMenu = new Menu("File");
        MenuItem loadGame = new MenuItem("Load Game");
        MenuItem saveGame = new MenuItem("Save Game");
        MenuItem exitMenu = new MenuItem("Exit");
        fileMenu.getItems().addAll(loadGame, saveGame, exitMenu);

        Menu gameMenu = new Menu("Game");
        MenuItem newGame = new MenuItem("New game");
        Menu chooseLevelMenu = new Menu("Choose Level");
        MenuItem easy = new MenuItem("Easy");
        MenuItem medium = new MenuItem("Medium");
        MenuItem hard = new MenuItem("Hard");
        chooseLevelMenu.getItems().addAll(easy, medium, hard);
        gameMenu.getItems().addAll(newGame, chooseLevelMenu);

        Menu helpMenu = new Menu("Help");
        MenuItem rules = new MenuItem("Rules");
        MenuItem resetGame = new MenuItem("Reset game");
        MenuItem checkGame = new MenuItem("Check solution");
        helpMenu.getItems().addAll(rules,resetGame, checkGame);
        menuBar = new MenuBar(fileMenu, gameMenu, helpMenu);
        return menuBar;
    }


    String getNextStringNumber() {
        return nextStringNumber;
    }

    File chooseFileToSave(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save to file...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku", "*.sudoku"));
        File selectedFile = fileChooser.showSaveDialog(this.getScene().getWindow());
        return selectedFile;
    }

    File chooseFileToLoad(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open saved game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku", "*.sudoku"));
        File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
        return selectedFile;
    }

    private void addEventHandlers(Controller controller) {
        EventHandler<ActionEvent>[] oneToNineHandlers = new EventHandler[SudokuUtilities.GRID_SIZE + 1];
        for (int i = 0; i < SudokuUtilities.GRID_SIZE + 1; i++) {
            oneToNineHandlers[i] = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (event.getSource() instanceof Button) {
                        if (((Button) event.getSource()).getText() == "C") {
                            nextStringNumber = "0";
                        } else {
                            nextStringNumber = ((Button) event.getSource()).getText();
                        }
                    }
                }
            };
            oneToNine[i].addEventHandler(ActionEvent.ACTION, oneToNineHandlers[i]);
        }

        EventHandler<MouseEvent>[][] gridHandlers = new EventHandler[SudokuUtilities.GRID_SIZE][SudokuUtilities.GRID_SIZE];
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                int row = i;
                int col = j;
                gridHandlers[i][j] = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getSource() instanceof Label) {
                            controller.HandleMove(model.getBoard()[row][col], parseInt(getNextStringNumber()));
                        }
                    }
                };
                gridView.getNumberTiles()[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, gridHandlers[i][j]);
            }
        }

        EventHandler<ActionEvent> checkHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (actionEvent.getSource() instanceof Button || actionEvent.getSource() instanceof MenuItem) {
                    checkAlert();
                }
            }
        };
        this.checkButton.addEventHandler(ActionEvent.ACTION, checkHandler);
        menuBar.getMenus().get(2).getItems().get(2).addEventHandler(ActionEvent.ACTION, checkHandler);

        EventHandler<MouseEvent> hintButton = new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getSource() instanceof Button) {
                    controller.HandleHints();
                }
            }
        };
        this.hintButton.addEventHandler(MouseEvent.MOUSE_CLICKED, hintButton);

        EventHandler<ActionEvent> rulesHandler = new EventHandler<>() { // FIXA!
            @Override
            public void handle(ActionEvent actionEvent) {
                if (actionEvent.getSource() instanceof MenuItem) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Rules");
                    alert.setHeaderText("SUDOKU RULES");
                    alert.setContentText("Sudoku is a logic-based,[2][3] combinatorial[4] number-placement puzzle.\n" +
                            "In classic Sudoku, the objective is to fill a 9 × 9 grid \n" +
                            "with digits so that each column, each row, and each of the nine 3 × 3 \n" +
                            "subgrids that compose the grid (also called \"boxes\", \"blocks\", or \"regions\")\n" +
                            " contain all of the digits from 1 to 9. The puzzle setter provides a partially\n" +
                            " completed grid, which for a well-posed puzzle has a single solution. ");
                    alert.show();
                }
            }
        };
        menuBar.getMenus().get(2).getItems().get(0).addEventHandler(ActionEvent.ACTION, rulesHandler);

        EventHandler<ActionEvent> resetHandler = new EventHandler<>() { // FIXA!
            @Override
            public void handle(ActionEvent actionEvent) {
                if (actionEvent.getSource() instanceof MenuItem) {
                    controller.handleReset();
                }
            }
        };
        menuBar.getMenus().get(2).getItems().get(1).addEventHandler(ActionEvent.ACTION, resetHandler);

        EventHandler<ActionEvent> newGameHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (actionEvent.getSource() instanceof MenuItem) {
                    if (((MenuItem) actionEvent.getSource()).getText() == "Easy") {
                        controller.onInitNewGameRoundSelected(SudokuUtilities.SudokuLevel.EASY);
                    } else if (((MenuItem) actionEvent.getSource()).getText() == "Medium") {
                        controller.onInitNewGameRoundSelected(SudokuUtilities.SudokuLevel.MEDIUM);
                    } else if (((MenuItem) actionEvent.getSource()).getText() == "Hard") {
                        controller.onInitNewGameRoundSelected(SudokuUtilities.SudokuLevel.HARD);
                    } else controller.onInitNewGameRoundSelected(model.getLevel());
                }
            }

        };
        ((Menu) (menuBar.getMenus().get(1).getItems().get(1))).getItems().get(0).addEventHandler(ActionEvent.ACTION, newGameHandler);
        ((Menu) (menuBar.getMenus().get(1).getItems().get(1))).getItems().get(1).addEventHandler(ActionEvent.ACTION, newGameHandler);
        ((Menu) (menuBar.getMenus().get(1).getItems().get(1))).getItems().get(2).addEventHandler(ActionEvent.ACTION, newGameHandler);
        menuBar.getMenus().get(1).getItems().get(0).addEventHandler(ActionEvent.ACTION, newGameHandler);
        EventHandler<ActionEvent> saveHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (actionEvent.getSource() instanceof MenuItem) {
                    controller.handleSave(chooseFileToSave(), model);
                }
            }
        };
        menuBar.getMenus().get(0).getItems().get(1).addEventHandler(ActionEvent.ACTION, saveHandler);

        EventHandler<ActionEvent> loadHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (actionEvent.getSource() instanceof MenuItem) {
                    controller.handleLoad(chooseFileToLoad());
                }
            }
        };
        menuBar.getMenus().get(0).getItems().get(0).addEventHandler(ActionEvent.ACTION, loadHandler);

        EventHandler<ActionEvent> exitHandler = new EventHandler<>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                if(actionEvent.getSource() instanceof MenuItem){
                    exitAlert(controller);
                    controller.handleExit();
                }
            }
        };
        menuBar.getMenus().get(0).getItems().get(2).addEventHandler(ActionEvent.ACTION, exitHandler);

    }
    public void changeModel(Board newBoard){
        model = newBoard;
    }
    public void exitAlert(Controller controller){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Save changes");
        exitAlert.setContentText("Do you want to save your game before exiting?");
        Optional<ButtonType> result = exitAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.handleSave(chooseFileToSave(), model);
        }

    }

}