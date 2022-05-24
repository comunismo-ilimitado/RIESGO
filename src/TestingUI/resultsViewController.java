package TestingUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileNotFoundException;

public class resultsViewController extends GameController{
    @FXML
    Label winner;
    @FXML
    Button button;

    public void showWinner(){
        winner.setVisible(true);
        button.setVisible(false);
        int winner1 = (int) getContainer().getServerController().getBoard().getPlayers().keySet().toArray()[0] + 1;
        winner.setText("Player "+ String.valueOf(Integer.valueOf(winner1)));
    }

    /**
     * Method for changing the image while mouse is on it (view effect)
     * @param mouseEvent
     * @throws FileNotFoundException
     */
    @FXML
    private void imageIn(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/backPressed.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("adelante")) {
            File file = new File("Resources/TestingUI/Images/nextPressed.png");
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
    private void imageOut(MouseEvent mouseEvent) throws FileNotFoundException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getId().equals("atras")) {
            File file = new File("Resources/TestingUI/Images/back.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else if (imageView.getId().equals("adelante")) {
            File file = new File("Resources/TestingUI/Images/next.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void exit(){
        if (getContainer().getServerController() != null)
            getContainer().getServerController().getServer().end();
        loadView("start-view.fxml");
    }

}
