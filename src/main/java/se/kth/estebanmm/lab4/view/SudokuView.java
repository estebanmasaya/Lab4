package se.kth.estebanmm.lab4.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import se.kth.estebanmm.lab4.model.Board;
import se.kth.estebanmm.lab4.model.Square;
import se.kth.estebanmm.lab4.model.SudokuUtilities;

public class SudokuView extends VBox {
    private Board model;
    private MenuBar menuBar;
    private BorderPane borderPane;
    private GridView gridView;
    private FlowPane leftPanel;
    private FlowPane rightPanel;
    private Button checkButton;
    private Button hintButton;

    private Button helpButton;

    private Button[] oneToNine;

    private VBox containsGrid;
    private String nextStringNumber;
    private SudokuUtilities.SudokuLevel level;
    private Label clickedLabel;

    public SudokuView(Board model){
        super();
        this.model = model;
        Controller controller = new Controller(model, this);
        generateMenu();
        nextStringNumber = "0";
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));
        gridView = new GridView();
        containsGrid = new VBox(gridView.getNumberPane());
        containsGrid.fillWidthProperty();
        generateLeftPanel();
        generateRightPanel();
        borderPane.setCenter(containsGrid);
        borderPane.setLeft(leftPanel);
        borderPane.setRight(rightPanel);
        this.getChildren().addAll(menuBar, borderPane);
        addEventHandlers(controller);
        updateFromModel();

    }

    public void updateFromModel(){
        System.out.println(model.toMatrix());
        for (int i = 0; i < SudokuUtilities.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuUtilities.GRID_SIZE; j++) {
                if(model.getBoard()[i][j].isChangeable()) gridView.getNumberTiles()[i][j].setTextFill(Color.DARKGRAY);
                if(model.getBoard()[i][j].getValue()==0){
                    gridView.getNumberTiles()[i][j].setText("");
                }
                else {
                    gridView.getNumberTiles()[i][j].setText(Integer.toString(model.getBoard()[i][j].getValue()));
                }
            }
        }
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
        Menu chooseLevel = new Menu("Choose Level");
        MenuItem easy = new MenuItem("Easy");
        MenuItem medium = new MenuItem("Medium");
        MenuItem hard = new MenuItem("Hard");
        chooseLevel.getItems().addAll(easy, medium, hard);
        gameMenu.getItems().addAll(newGame, chooseLevel);



        Menu helpMenu = new Menu("Help");
        MenuItem rules = new MenuItem("Rules");
        MenuItem resetGame = new MenuItem("Reset game");
        helpMenu.getItems().addAll(rules,resetGame);

        menuBar = new MenuBar(fileMenu, gameMenu, helpMenu);
        return menuBar;
    }


    public String getNextStringNumber() {
        return nextStringNumber;
    }

    public Label getClickedLabel() {
        return clickedLabel;
    }

    private void addEventHandlers(Controller controller) {
        EventHandler<ActionEvent>[] oneToNineHandlers = new EventHandler[SudokuUtilities.GRID_SIZE + 1];
        for (int i = 0; i < SudokuUtilities.GRID_SIZE + 1; i++) {
            oneToNineHandlers[i] = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (event.getSource() instanceof Button) {
                        if(((Button) event.getSource()).getText()=="C"){
                            nextStringNumber="0";
                        }
                        else{
                            nextStringNumber = ((Button) event.getSource()).getText();
                            System.out.println(nextStringNumber);
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
                            controller.HandleMove(model.getBoard()[row][col]);
                        }
                    }
                };
                gridView.getNumberTiles()[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, gridHandlers[i][j]);
            }
        }

        EventHandler<MouseEvent> checkButton = new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getSource() instanceof Button) {
                    if (controller.HandleCheck()){
                        System.out.println("It is correct!"); //Här kanske vi kan reseta spelet?
                    }
                }
            }
        };
        this.checkButton.addEventHandler(MouseEvent.MOUSE_CLICKED, checkButton);

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
                    alert.setTitle("Rules!");
                    alert.setContentText("This is the rules: ");
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
                    MenuItem level = (MenuItem) actionEvent.getSource();
                    SudokuUtilities.SudokuLevel chooseLevel = SudokuUtilities.SudokuLevel.EASY;
                    if (level == menuBar.getMenus().get(1))
                        chooseLevel = SudokuUtilities.SudokuLevel.EASY;
                    }
                    //controller.onInitNewGameRoundSelected(chooseLevel);
                }
            };
        //menuBar.getMenus().get(1).getItems().get(0).addEventHandler(ActionEvent.ACTION, newGameHandler);


    }




}
