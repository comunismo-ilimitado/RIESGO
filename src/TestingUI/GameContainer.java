package TestingUI;

import com.sun.tools.javac.Main;
import controller.controllers.net.ClientController;
import javax.sound.sampled.*;
import controller.game.ServerController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This is a Parent Class made for including some
 *  important methods for all the views-controller.
 */
public class GameContainer {

    private String mapsLocation = "Resources/TestingUI/Images/Map";
    private String ip;
    private Locale locale;
    private ResourceBundle bundle;
    private Stage stage;
    private FXMLLoader fxmlLoader;
    private Clip music;
    private ClientController clientController;
    private ServerController serverController;


    /**
     * Getters and setters
     */

    public void setMapsLocation(String mapsLocation) {
        this.mapsLocation = mapsLocation;
    }

    public String getMapsLocation() {
        return mapsLocation;
    }

    public void setFxmlLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public ServerController getServerController() {
        return serverController;
    }
    public void setMusic(){
    try {
          File file = new File("Resources/musica.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
             this.music = AudioSystem.getClip();
            this.music.open(audioInputStream);
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Error al cargar el sonido.");
        }

    }
    public void playMusic(){
        this.music.start();
    }
    public void stopMusic(){
        this.music.stop();
    }
    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
}
