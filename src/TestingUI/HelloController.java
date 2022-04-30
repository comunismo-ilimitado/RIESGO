package TestingUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class HelloController {
    @FXML
    private Label welcomeText;

    public void exitGame() {
        System.exit(0);
    }

    public void changeColorEnter2(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: #E19C1B");
    }

    public void changeColorOut2(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle("-fx-background-color: #FFFFFF");
    }

    public void play(MouseEvent event) {
        try {
            Parent root;
            Button button = (Button) event.getSource();
            if (button.getScene().getHeight() == 720) {
                 root = FXMLLoader.load(getClass().getResource("customize-view-small.fxml"));
            } else {
                 root = FXMLLoader.load(getClass().getResource("customize-view-big.fxml"));
            }
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();

        } catch (Exception e) {
        }
    }

    public void changeImageSettings(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("settings")) {
            File file = new File("Resources/TestingUI/Images/settingsLogoOn.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("exit")) {
            File file = new File("Resources/TestingUI/Images/exit2.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void returnImageSettings(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("settings")) {
            File file = new File("Resources/TestingUI/Images/settingsLogo.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if(imageView.getId().equals("exit")){
            File file = new File("Resources/TestingUI/Images/exit.jpg");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void settings(MouseEvent event) {
        try {
            Parent root;
            Scene scene;
            ImageView imageView = (ImageView) event.getSource();

            if (imageView.getScene().getHeight() == 720) {
                root = FXMLLoader.load(getClass().getResource("settings-view-small.fxml"));
                scene = new Scene(root, 1280, 720);
            } else {
                root = FXMLLoader.load(getClass().getResource("settings-view-big.fxml"));
                scene = new Scene(root, 1920, 1080);
            }
            Stage appStage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }

    public void credits(MouseEvent event) {
        try {
            Parent root;
            Scene scene;
            Button button = (Button) event.getSource();
            if (button.getScene().getHeight() == 720) {
                root = FXMLLoader.load(getClass().getResource("credits-view-small.fxml"));
                scene = new Scene(root, 1280, 720);
            } else {
                root = FXMLLoader.load(getClass().getResource("credits-view-big.fxml"));
                scene = new Scene(root, 1920, 1080);
            }
            Stage appStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }

    public void changeResolution1(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("settings-view-big.fxml"));
            Scene scene = new Scene(root, 1920, 1080);
            Stage appStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }

    public void changeResolution2(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("settings-view-small.fxml"));
            Scene scene = new Scene(root, 1280, 720);
            Stage appStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();
        } catch (Exception e) {
        }
    }

    public void howToPlay(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.hasbro.com/common/documents/dad2886d1c4311ddbd0b0800200c9a66/ADE84A6E50569047F504839559C5FEBF.pdf"));
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public void back(MouseEvent event) {
        try {
            Parent root;
            Button button = (Button) event.getSource();
            if (button.getScene().getHeight() == 720) {
                root = FXMLLoader.load(getClass().getResource("hello-view-small.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("hello-view-big.fxml"));
            }
            Scene scene = new Scene(root);
            Stage appStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.toFront();
            appStage.show();

        } catch (Exception e) {
        }
    }
}