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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomizeViewController implements Initializable {

    private ResourceBundle resources;
    @FXML
    private ComboBox<String> comboJugadores, comboMapas;
    @FXML
    private Label errorLabel;
    @FXML
    private Pane errorPane;
    @FXML
    private ComboBox<String> b21, b22, b31, b32, b33, b41, b42, b43, b44;

    //private List<ComboBox<String>> boxList = List.of(b21, b22, b31, b32, b33, b41, b42, b43, b44);
    @FXML
    private HBox two, three, four;

    private int numberOfPlayers;
    private String[] players = new String[3];
    private String[] maps = {"Mundo", "Europa"};
    private String[] strategies = {"Humano", "Aficionado", "Profesional", "Maestro"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        for (int i = 0; i < players.length; i++) {
            players[i] = i + 2 + " " + resources.getString("players");
        }
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
        String player = comboJugadores.getValue();
        if (Pattern.compile("2.*").matcher(player).matches()) {
            two.setVisible(true);
            three.setVisible(false);
            four.setVisible(false);
            numberOfPlayers = 2;
        } else if (Pattern.compile("3.*").matcher(player).matches()) {
            two.setVisible(false);
            three.setVisible(true);
            four.setVisible(false);
            numberOfPlayers = 3;
        } else if (Pattern.compile("4.*").matcher(player).matches()) {
            two.setVisible(false);
            three.setVisible(false);
            four.setVisible(true);
            numberOfPlayers = 4;
        }
    }

    @FXML
    private void imageIn(MouseEvent mouseEvent) throws FileNotFoundException {
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

    @FXML
    private void imageOut(MouseEvent mouseEvent) throws FileNotFoundException {
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

    @FXML
    private void back(MouseEvent event) {
        try{
            Parent root;
            ImageView image = (ImageView) event.getSource();
            ResourceBundle bundle =  ResourceBundle.getBundle("riesgoBundle", MainUI.locale);
            root = FXMLLoader.load(getClass().getResource("start-view.fxml"),bundle);
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
            ResourceBundle bundle =  ResourceBundle.getBundle("riesgoBundle", MainUI.locale);
            Parent root = FXMLLoader.load(getClass().getResource("map-view.fxml"),bundle);;
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }

    @FXML
    private void returnGame() {
        errorPane.setVisible(false);
    }
}


