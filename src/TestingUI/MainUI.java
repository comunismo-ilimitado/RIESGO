package TestingUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainUI extends Application {

    GameContainer container;

    @Override
    public void start(Stage stage) throws IOException {
        Locale locale = new Locale("en","UK");
        ResourceBundle bundle =  ResourceBundle.getBundle("riesgoBundle", locale);
        FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource("start-view.fxml"), bundle);
        container = new GameContainer();
        container.setLocale(locale);
        container.setBundle(bundle);
        container.setStage(stage);
        container.setFxmlLoader(fxmlLoader);
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        ((GameController)fxmlLoader.getController()).setContainer(container);
        stage.setTitle("RIESGO");
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setScene(scene);
        File file = new File("Resources/TestingUI/Images/riesgoTitle.png");
        stage.getIcons().add(new Image(file.toURI().toString()));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}