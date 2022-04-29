package TestingUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.graalvm.compiler.phases.common.NodeCounterPhase;

public class HelloController {
    @FXML
    private Label welcomeText;

    public void exitGame() {
        System.exit(0);
    }

    public void changeColorEnter2(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: #1555bf");
    }

    public void changeColorOut2(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: #6d99d5");
    }

    public void play(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("settings-view.fxml"));
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();

        } catch (Exception e) {
        }
    }

    public void back(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();

        } catch (Exception e) {
        }
    }

    public void settings(MouseEvent mouseEvent) {
        System.out.println("Ajustes");
    }
}