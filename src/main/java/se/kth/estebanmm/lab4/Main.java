package se.kth.estebanmm.lab4;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.kth.estebanmm.lab4.view.GridView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridView gridView = new GridView();
        gridView.getNumberPane();

        VBox root = new VBox();
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        Menu menu = new Menu();
        primaryStage.show();

//        Button button = new Button();
//        button.setText("Say 'Hello World'");
//
//        button.setOnAction(new ButtonHandler());
//
//        StackPane root = new StackPane();
//        root.getChildren().add(button);
//
//        Scene scene = new Scene(root, 300, 250);
//        primaryStage.setScene(scene);
//
//        primaryStage.setTitle("Hello JavaFX!");
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("Hello JavaFX World!");
        }
    }
}