import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("OSViewer.fxml"));
        primaryStage.setTitle("OS");
        primaryStage.setScene(new Scene(root, 1300, 900));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
