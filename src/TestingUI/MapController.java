package TestingUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class MapController implements Initializable {
    @FXML
    private Pane exitPane, errorPane, dicePane;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView playerDice1, playerDice2, playerDice3, opponentDice1, opponentDice2, opponentDice3;
    private Image[] dice = new Image[6];
    @FXML
    private ImageView alaska, alberta, argentina, brazil, britain, centralAmerica, china, congo, eastAfrica,
            easternAustralia, easternUS, egypt, greenland, iceland, india, indonesia, irkutsk, japan, kamchatka,
            kazakhstan, madagascar, middleEast, mongolia, northAfrica, northernEurope, northWestTerritory, ontario,
            papuaNewGuinea, peru, quebec, scandinavia, siam, siberia, southAfrica, southernEurope, ukraine, ural,
            venezuela, westernAustralia, westernEurope, westernUS, yakutsk;
    private HashMap<String, ImageView> colorReference = new HashMap<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colorReference.put("0x0d0d0dff", alaska);
        colorReference.put("0x1a1a1aff", northWestTerritory);
        colorReference.put("0x262626ff", greenland);
        colorReference.put("0x333333ff", alberta);
        colorReference.put("0x404040ff", ontario);
        colorReference.put("0x4d4d4dff", quebec);
        colorReference.put("0x595959ff", westernUS);
        colorReference.put("0x666666ff", easternUS);
        colorReference.put("0x737373ff", centralAmerica);
        colorReference.put("0x808080ff", venezuela);
        colorReference.put("0x8c8c8cff", peru);
        colorReference.put("0x999999ff", brazil);
        colorReference.put("0xa6a6a6ff", argentina);
        colorReference.put("0xb3b3b3ff", iceland);
        colorReference.put("0xbfbfbfff", scandinavia);
        colorReference.put("0xccccccff", ukraine);
        colorReference.put("0xd9d9d9ff", britain);
        colorReference.put("0xe6e6e6ff", northernEurope);
        colorReference.put("0xf2f2f2ff", westernEurope);
        colorReference.put("0xffffffff", southernEurope);
        colorReference.put("0xe680e6ff", northAfrica);
        colorReference.put("0xf280f2ff", egypt);
        colorReference.put("0xff80ffff", eastAfrica);
        colorReference.put("0x0d0d80ff", congo);
        colorReference.put("0x1a1a80ff", southAfrica);
        colorReference.put("0x262680ff", madagascar);
        colorReference.put("0x0d800dff", ural);
        colorReference.put("0x1a801aff", siberia);
        colorReference.put("0x268026ff", yakutsk);
        colorReference.put("0x338033ff", kamchatka);
        colorReference.put("0x408040ff", irkutsk);
        colorReference.put("0x4d804dff", kazakhstan);
        colorReference.put("0x598059ff", china);
        colorReference.put("0x668066ff", mongolia);
        colorReference.put("0x738073ff", japan);
        colorReference.put("0x8c808cff", middleEast);
        colorReference.put("0x998099ff", india);
        colorReference.put("0xa680a6ff", siam);
        colorReference.put("0xb380b3ff", indonesia);
        colorReference.put("0xbf80bfff", papuaNewGuinea);
        colorReference.put("0xcc80ccff", westernAustralia);
        colorReference.put("0xd980d9ff", easternAustralia);

        File file = new File("Resources/TestingUI/Images/Dice/one.png");
        dice[0] = new Image(file.toURI().toString());
        file = new File("Resources/TestingUI/Images/Dice/two.png");
        dice[1] = new Image(file.toURI().toString());
        file = new File("Resources/TestingUI/Images/Dice/three.png");
        dice[2] = new Image(file.toURI().toString());
        file = new File("Resources/TestingUI/Images/Dice/four.png");
        dice[3] = new Image(file.toURI().toString());
        file = new File("Resources/TestingUI/Images/Dice/five.png");
        dice[4] = new Image(file.toURI().toString());
        file = new File("Resources/TestingUI/Images/Dice/six.png");
        dice[5] = new Image(file.toURI().toString());
    }

    @FXML
    private void countrySelected(MouseEvent event) {
        System.out.println("country");
        ImageView country = (ImageView) event.getSource();
        Lighting blackout = new Lighting();
        blackout.setLight(new Light.Distant(45, 45, Color.AQUA));

        Color color = country.getImage().getPixelReader().getColor((int) event.getX(), (int) event.getY());
        System.out.println(color.toString());

        country.setEffect(blackout);
        country.setCache(true);
        country.setCacheHint(CacheHint.SPEED);
        System.out.println(country.getId());
    }
    @FXML
    private void mapSelected(MouseEvent event) {
        ImageView country = (ImageView) event.getSource();
        Color color = country.getImage().getPixelReader().getColor((int) event.getX(), (int) event.getY());
        country = colorReference.get(color.toString());
        Lighting colorSettings = new Lighting();
        colorSettings.setLight(new Light.Distant(45, 45, Color.AQUA));
        country.setEffect(colorSettings);
        country.setCache(true);
        country.setCacheHint(CacheHint.SPEED);
    }
    @FXML
    private void nextPhase() {
        errorPane.setVisible(true);
        errorLabel.setText("Hace falta desplegar todas las tropas");
        System.out.println("Siguiente fase");
    }
    @FXML
    private void exchangeCards() {
        dicePane.setVisible(true);

        File file = new File("Resources/TestingUI/Images/Dice/one.png");
        Image dice1 = new Image(file.toURI().toString());

        playerDice1.setImage(dice[(int) Math.random() % 6 + 1]);
        playerDice2.setImage(dice[(int) Math.random() % 6 + 1]);
        playerDice3.setVisible(false);
        opponentDice1.setImage(dice[(int) Math.random() % 6 + 1]);
        opponentDice2.setImage(dice[(int) Math.random() % 6 + 1]);
        opponentDice3.setImage(dice[(int) Math.random() % 6 + 1]);

        System.out.println("Intercambio de cartas");
    }

    @FXML
    private void back(MouseEvent event) {
        loadView(event, "start-view.fxml");
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
    private void yesButton() {
         System.exit(0);
    }
    @FXML
    private void noButton() {
        System.exit(0);
    }
    @FXML
    private void returnGame() {
        exitPane.setVisible(false);
        errorPane.setVisible(false);
        dicePane.setVisible(false);
    }
}