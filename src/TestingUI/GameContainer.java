package TestingUI;

import com.sun.tools.javac.Main;
import controller.controllers.net.ClientController;

import controller.game.ServerController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class GameContainer {

    String mapsLocation = "Resources/TestingUI/Images/Map";
    Locale locale;
    ResourceBundle bundle;
    Stage stage;
    FXMLLoader fxmlLoader;

    ClientController clientController;
    ServerController serverController;


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

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }
}
