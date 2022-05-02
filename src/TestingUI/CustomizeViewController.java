package TestingUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class CustomizeViewController implements Initializable {
    @FXML
    private ComboBox<String> comboJugadores;
    @FXML
    private ComboBox<String> comboMapas;
    @FXML
    private ComboBox<String> b21, b22, b31, b32, b33, b41, b42, b43, b44;

    //private List<ComboBox<String>> boxList = List.of(b21, b22, b31, b32, b33, b41, b42, b43, b44);
    @FXML
    private HBox two, three, four;

    private String[] players = {"2 jugadores", "3 jugadores", "4 jugadores"};
    private String[] maps = {"Mundo", "Europa"};
    private String[] strategies = {"Humano", "Aficionado", "Profesional", "Maestro"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboJugadores.getItems().addAll(players);
        comboMapas.getItems().addAll(maps);

        /*for (ComboBox<String> box : boxList) {
            box.getItems().addAll(strategies);
        }*/

        b21.getItems().addAll(strategies);
        b22.getItems().addAll(strategies);
        b31.getItems().addAll(strategies);
        b32.getItems().addAll(strategies);
        b33.getItems().addAll(strategies);
        b41.getItems().addAll(strategies);
        b42.getItems().addAll(strategies);
        b43.getItems().addAll(strategies);
        b44.getItems().addAll(strategies);

        comboJugadores.setOnAction(this::getItem);

        two.setVisible(false);
        three.setVisible(false);
        four.setVisible(false);

    }

    private void getItem(ActionEvent event) {
        ComboBox box = (ComboBox) event.getSource();
        Double res = box.getScene().getWidth();
        String player = comboJugadores.getValue();

        try {
            Parent root = null;
            if (player.equals("2 jugadores")) {
                two.setVisible(true);
                three.setVisible(false);
                four.setVisible(false);
            } else if (player.equals("3 jugadores")) {
                two.setVisible(false);
                three.setVisible(true);
                four.setVisible(false);
            } else if (player.equals("4 jugadores")) {
                two.setVisible(false);
                three.setVisible(false);
                four.setVisible(true);
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
            if (image.getScene().getHeight() == 720) {
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

    @FXML
    private void game(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("map-view.fxml"));;
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }
}


