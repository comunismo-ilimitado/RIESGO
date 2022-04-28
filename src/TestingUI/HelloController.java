package TestingUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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

    public void play(MouseEvent mouseEvent) {
        System.out.println("Juego");
    }

    public void settings(MouseEvent mouseEvent) {
        System.out.println("Ajustes");
    }
}