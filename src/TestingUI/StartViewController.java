package TestingUI;

import com.sun.tools.javac.Main;
import controller.controllers.net.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;/**/
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;

public class StartViewController extends GameController{

    @FXML
    private Pane onlinePane, ipPane, rolPane;
    @FXML
    private TextField ipField, nameField;

    @FXML
    private void exitGame() {
        System.exit(0);
    }
    @FXML
    private void changeColorEnter2(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: #E19C1B");
    }
    @FXML
    private void changeColorOut2(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: #FFFFFF");
    }
    @FXML
    private void play() {
        onlinePane.setVisible(true);
    }

    @FXML
    private void localButtonAction(MouseEvent event){
        onlinePane.setVisible(false);
        // For local play, it sets a special IP
        getContainer().setIp("localHost");
        loadView("customize-view.fxml");
    }
    @FXML
    private void onlineButtonAction(){
        onlinePane.setVisible(false);
        rolPane.setVisible(true);
    }
    @FXML
    private void hostButtonAction(){
        rolPane.setVisible(false);
        // For host play, it sets a special IP
        getContainer().setIp("localHost");
        loadView("customize-view.fxml");
    }
    @FXML
    private void guestButtonAction(){
        rolPane.setVisible(false);
        ipPane.setVisible(true);
    }
    @FXML
    private void sendButtonAction(){
        getContainer().setIp(ipField.getCharacters().toString());

        getContainer().setClientController(new ClientController());

        getContainer().getClientController().getPlayerConfiguration().setName(nameField.getText());
        getContainer().getClientController().getClient().connect(getContainer().getIp());
        getContainer().getClientController().getClient().start();

        System.out.println("Esperando respuesta del server...");

        while (getContainer().getClientController().getServerBoard() == null) {}

        System.out.println("Respuesta recibida!");

        getContainer().getClientController().updatePlayer();

        loadView("map-view.fxml");

        loadView("map.fxml");
    }
    @FXML
    private void returnGame() {
        onlinePane.setVisible(false);
        rolPane.setVisible(false);
        ipPane.setVisible(false);
    }
    @FXML
    private void imageOut(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("settings")) {
            File file = new File("Resources/TestingUI/Images/settingsLogoOn.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("exit")) {
            File file = new File("Resources/TestingUI/Images/exit.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("play")) {
            File file = new File("Resources/TestingUI/Images/play2.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/Flecha_atras2.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }
    @FXML
    private void imageIn(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("settings")) {
            File file = new File("Resources/TestingUI/Images/settingsLogo.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("exit")) {
            File file = new File("Resources/TestingUI/Images/exit2.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("play")) {
            File file = new File("Resources/TestingUI/Images/play.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/Flecha_atras1.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    private void settings(MouseEvent event) {
        loadView("settings-view.fxml");
    }
    @FXML
    private void credits(MouseEvent event) {
        loadView("credits-view.fxml");
    }
    @FXML
    private void howToPlay() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.hasbro.com/common/documents/dad2886d1c4311ddbd0b0800200c9a66/ADE84A6E50569047F504839559C5FEBF.pdf"));
        } catch (URISyntaxException | IOException ex) {
            System.out.println(ex);
        }
    }
    @FXML
    private void back(MouseEvent event) {
        loadView("start-view.fxml");
    }
    @FXML
    private void setEnglish() {
        getContainer().setLocale(new Locale("en","UK"));
        loadView("settings-view.fxml");

    }
    @FXML
    private void setSpanish() {
        getContainer().setLocale(new Locale("es","ES"));
        loadView("settings-view.fxml");
    }
    @FXML
    private void soundOn(){
        getContainer().playMusic();
    }
    @FXML
    private void soundOff(){
        getContainer().stopMusic();
    }
}