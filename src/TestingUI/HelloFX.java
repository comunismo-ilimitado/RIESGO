package TestingUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloFX.class.getResource("hello-view-small.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("RIESGO");
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.minWidthProperty().bind(scene.heightProperty().multiply(2));
        stage.minHeightProperty().bind(scene.widthProperty().divide(2));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}