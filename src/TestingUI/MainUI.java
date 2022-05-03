package TestingUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainUI extends Application {

    static Locale locale;

    @Override
    public void start(Stage stage) throws IOException {
        locale = new Locale("en","UK");
        ResourceBundle bundle =  ResourceBundle.getBundle("riesgoBundle", locale);
        FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("RIESGO");
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}