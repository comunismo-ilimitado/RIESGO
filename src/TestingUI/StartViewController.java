package TestingUI;

import com.sun.tools.javac.Main;
import controller.controllers.net.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private CheckBox music;

    /**
     * Close the program
     */
    @FXML
    private void exitGame() {
        System.exit(0);
    }

    /**
     * Shows the onlinePane for selection of
     * type of game (host it or introduce IP)
     */
    @FXML
    private void play() {
        onlinePane.setVisible(true);
    }

    /**
     * This method is called when Local Game button is clicked,
     * so that the game is started (customize-view is called) and
     * the IP is set as "localhost"
     * @param event
     */
    @FXML
    private void localButtonAction(MouseEvent event){
        onlinePane.setVisible(false);
        // For local play, it sets a special IP
        getContainer().setIp("localHost");
        loadView("customize-view.fxml");
    }

    /**
     * This method is called when Online Game button is clicked,
     * so that the next Pane is now visible.
     */
    @FXML
    private void onlineButtonAction(){
        onlinePane.setVisible(false);
        rolPane.setVisible(true);
    }

    /**
     * This method is called when Host Game button is clicked,
     * so that the game is started (customize-view is called) and
     * the IP is set as "localhost".
     */
    @FXML
    private void hostButtonAction(){
        rolPane.setVisible(false);
        // For host play, it sets a special IP
        getContainer().setIp("localHost");
        loadView("customize-view.fxml");
    }

    /**
     * Shows the Pane where the IP textfield is shown
     */
    @FXML
    private void guestButtonAction(){
        rolPane.setVisible(false);
        ipPane.setVisible(true);
    }

    /**
     * This method is called when the confirm button is clicked.
     * The IP textfield is read and starts the connection with the server.
     * Then, the game is started, loading the map-view (the hosting player
     * should have finish customizing the game).
     */
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

    /**
     * Method for closing Panes when clicking outside while being in a view.
     */
    @FXML
    private void returnGame() {
        onlinePane.setVisible(false);
        rolPane.setVisible(false);
        ipPane.setVisible(false);
    }
    /**
     * Method for changing the image while mouse is on it (view effect)
     * @param mouseEvent
     * @throws FileNotFoundException
     */
    @FXML
    private void imageOut(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("settings")) {
            File file = new File("Resources/TestingUI/Images/settingsPressed.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("exit")) {
            File file = new File("Resources/TestingUI/Images/exit.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("play")) {
            File file = new File("Resources/TestingUI/Images/nextPressed.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/backPressed.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }
    /**
     * Method for changing the image when the mouse is out of it (view effect)
     * @param mouseEvent
     * @throws FileNotFoundException
     */
    @FXML
    private void imageIn(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("settings")) {
            File file = new File("Resources/TestingUI/Images/settings.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("exit")) {
            File file = new File("Resources/TestingUI/Images/exitPressed.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("play")) {
            File file = new File("Resources/TestingUI/Images/next.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/back.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    /**
     * Loads the settings screen.
     */
    @FXML
    private void settings() {
        loadView("settings-view.fxml");
    }

    /**
     * Loads the credits screen.
     */
    @FXML
    private void credits() {
        loadView("credits-view.fxml");
    }

    /**
     * Open the web of the original instructions of Risk.
     */
    @FXML
    private void howToPlay() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.hasbro.com/common/documents/dad2886d1c4311ddbd0b0800200c9a66/ADE84A6E50569047F504839559C5FEBF.pdf"));
        } catch (URISyntaxException | IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Method for going back to the start-view.
     */
    @FXML
    private void back() {
        loadView("start-view.fxml");
    }

    /**
     * Change the language to English
     * and reload the view.
     */
    @FXML
    private void setEnglish() {
        getContainer().setLocale(new Locale("en","UK"));
        loadView("settings-view.fxml");

    }

    /**
     * Change the language to Spanish
     * and reload the view.
     */
    @FXML
    private void setSpanish() {
        getContainer().setLocale(new Locale("es","ES"));
        loadView("settings-view.fxml");
    }

    /**
     * Start playing music when the box is ticked.
     */
    @FXML
    private void toggleSound() {
        if (music.isSelected()) {
            getContainer().playMusic();
        } else {
            getContainer().stopMusic();
        }
    }

}