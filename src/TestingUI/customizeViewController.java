package TestingUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class customizeViewController implements Initializable {
    @FXML
    private ComboBox<String> comboJugadores;
    @FXML
    private ComboBox<String> comboMapas;
    @FXML
    private ComboBox<String> combo1jugador;
    @FXML
    private ComboBox<String> combo2jugador;

    private String[] players = {"2 jugadores", "3 jugadores", "4 jugadores"};
    private String[] maps = {"Mundo", "Europa"};
    private String[] strategies = {"Humano", "Aficionado", "Profesional", "Maestro"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboJugadores.getItems().addAll(players);
        comboMapas.getItems().addAll(maps);
        comboJugadores.setOnAction(this::getItem);
        try {
            combo1jugador.getItems().addAll(strategies);
            combo2jugador.getItems().addAll(strategies);
        }catch(Exception e){

        }
    }

    private void getItem(ActionEvent event) {
        String player = comboJugadores.getValue();
        try {
            Parent root = null;
            if (player.equals("2 jugadores")) {
                root = FXMLLoader.load(getClass().getResource("customize-view-small-2players.fxml"));
            } else if (player.equals("3 jugadores")) {
                root = FXMLLoader.load(getClass().getResource("customize-view-small-3players.fxml"));
            } else if (player.equals("4 jugadores")) {
                System.out.println("a");
            }
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((ComboBox) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }

    public void imageIn(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/Flecha_atras2.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("adelante")) {
            File file = new File("Resources/TestingUI/Images/Flecha_adelante2.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void imageOut(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/Flecha_atras1.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("adelante")) {
            File file = new File("Resources/TestingUI/Images/Flecha_adelante1.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void back(MouseEvent event) {
        try{
            Parent root;
            ImageView image = (ImageView) event.getSource();
            if (image.getScene().getWidth() == 720) {
                root = FXMLLoader.load(getClass().getResource("hello-view-small.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("hello-view-big.fxml"));
            }
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();

        } catch (Exception e) {
        }
    }

    public void game(MouseEvent event) {
        System.out.println("Juego");
    }
}


