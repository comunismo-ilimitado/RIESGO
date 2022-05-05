package TestingUI;

import controller.controllers.net.ClientController;
import controller.net.Board;
import controller.net.ClientUpdate;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Country;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class MapController extends GameController implements Initializable {
    @FXML
    public Pane exitPane, errorPane, dicePane, countriesPane, countryLabels;
    @FXML
    public Label errorLabel, currentPhase, currentPlayer, statics;
    @FXML
    private ImageView playerDice1, playerDice2, playerDice3, opponentDice1, opponentDice2, opponentDice3;
    private Image[] dice = new Image[6];

    private HashMap<String, ImageView> colorReference = new HashMap<>();
    private HashMap<String, Label> colorReferLabel = new HashMap<>();
    private HashMap<String, Country> colorCountry = new HashMap<>();

    public void setCountries() {
        HashMap<String, Country> countries = getContainer().getClientController().getServerBoard().getCountries();

        {
            File file = new File(getContainer().getMapsLocation() + "/" + getContainer().getServerController().getBoard().getMapName()
                    + "/" + "BlackMap" + ".png");
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setMouseTransparent(true);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            countriesPane.getChildren().add(imageView);
        }
        {
            File file = new File(getContainer().getMapsLocation() + "/" + getContainer().getServerController().getBoard().getMapName()
                    + "/" + "ColorMap" + ".png");
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setMouseTransparent(true);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            countriesPane.getChildren().add(imageView);
        }



        for(Country country : countries.values()) {
            colorCountry.put(country.getColor(), country);
            File file = new File(getContainer().getMapsLocation()+"/"+getContainer().getServerController().getBoard().getMapName()
                    +"/"+country.getName()+".png");
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setMouseTransparent(true);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(this::mapSelected);
            countriesPane.getChildren().add(imageView);
            colorReference.put(country.getColor(), imageView);

            Label label = new Label(country.getName()+"\n 0 ");
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setLayoutX(country.getPosx());
            label.setLayoutY(country.getPosy());
            countryLabels.getChildren().add(label);
            colorReferLabel.put(country.getColor(), label);
        }


    }

    @Override
    public void onLoad() {
        getContainer().getClientController().setMapController(this);
        setCountries();
        update();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    private int lastError = -1;
    private int lastInfo = -1;
    private int lastAction = -1;


    public void update(){
        ClientController controller = getContainer().getClientController();
        //Gestion de errores
        {
            if (!controller.getServerBoard().getErrors().isEmpty()) {
                if(controller.getServerBoard().getErrors().size() != lastError) {
                    lastError = controller.getServerBoard().getErrors().size();
                    Board.ErrorMessage error = controller.getServerBoard().getErrors().get(lastError-1);
                    if(error.getPlayer().getPlayerName().equals(controller.getPlayerConfiguration().getName())) {
                        System.out.println("Error: " + error.text);
                        String finalError = error.text;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                errorLabel.setText(finalError);
                                errorPane.setVisible(true);
                            }
                        });
                    }
                }
            }
        }

        //Actualizar fase
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    currentPhase.setText(getContainer().getBundle().getString("faseTag")+"\n"
                            +getContainer().getClientController().getServerBoard().getCurrentPhase());
                }
            });
        }


        // Actualizar Current player
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    currentPlayer.setText(getContainer().getBundle().getString("playerTag")+"\n"
                            +getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName());
                }
            });
        }

        // Actualizar Estadisticas
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    statics.setText(getContainer().getBundle().getString("statsTag")+"\n Occupied countries: "
                            +getContainer().getClientController().getServerBoard().getCountriesPercentage()+" %\n Continents occupied: "+
                            getContainer().getClientController().getServerBoard().getContinentsOccupied());
                }
            });
        }

        // Actualizar paises
        {
            HashMap<String, Country> countries = getContainer().getClientController().getServerBoard().getCountries();
            for (Country country : countries.values()) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        java.awt.Color c = country.getOwner().getPlayerColor();
                        setColorOfCountry(country, Color.rgb(c.getRed(), c.getGreen(), c.getBlue()));
                        colorReferLabel.get(country.getColor()).setText(country.getName()+"\n"+country.getNoOfArmies());
                    }
                });
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    boolean dices = false;
                    Country country = getContainer().getClientController().getServerBoard().getAttackCountry1();
                    if(country != null){
                        if(getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName()
                                .equals(getContainer().getClientController().getPlayerConfiguration().getName()))
                            setShadedColorCountry(country, Color.AQUA);
                        dices = true;
                    }

                    country = getContainer().getClientController().getServerBoard().getAttackCountry2();
                    if(country != null){
                        if(getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName()
                                .equals(getContainer().getClientController().getPlayerConfiguration().getName()))
                            setShadedColorCountry(country, Color.AQUA);
                        dices = true;
                    }
                }
            });

        }

        {
            if(lastAction != controller.getServerBoard().getActions().size()) {
                lastAction = controller.getServerBoard().getActions().size();
            }
        }
    }

    public double changeHue(Color color){
        if(color.getHue() > 180.0){
            return 360.0-color.getHue()/180.0;
        }else{
            return  color.getHue()/180.0;
        }
    }

    public void setColorOfCountry(Country country, Color color){
        ImageView imageView = colorReference.get(country.getColor());
        ColorAdjust blackout = new ColorAdjust();
        blackout.setSaturation(0.6);
        blackout.setHue(changeHue(color));
        imageView.setEffect(blackout);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
    }

    public void setShadedColorCountry(Country country, Color color){
        ImageView imageView = colorReference.get(country.getColor());
        Lighting colorSettings = new Lighting();
        colorSettings.setLight(new Light.Distant(45, 45, Color.AQUA));

        imageView.setEffect(colorSettings);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
    }

    @FXML
    private void countrySelected(MouseEvent event) {
        ImageView country = (ImageView) event.getSource();
        Lighting blackout = new Lighting();
        blackout.setLight(new Light.Distant(45, 45, Color.AQUA));

        Color color = country.getImage().getPixelReader().getColor((int) event.getX(), (int) event.getY());

        country.setEffect(blackout);
        country.setCache(true);
        country.setCacheHint(CacheHint.SPEED);
        setColorOfCountry(colorCountry.get(color.toString()), Color.AQUA);
    }
    @FXML
    private void mapSelected(MouseEvent event) {
        ImageView country = (ImageView) event.getSource();
        Color color = country.getImage().getPixelReader().getColor((int) event.getX(), (int) event.getY());
        country = colorReference.get(color.toString());
        if(country == null) return;

        ClientUpdate.ClientAction action = new ClientUpdate.ClientAction();
        action.setActionCommand(colorCountry.get(color.toString()).getName()+" |5|");
        getContainer().getClientController().sendAction(action);
    }
    @FXML
    private void nextPhase() {

        ClientUpdate.ClientAction action = new ClientUpdate.ClientAction();
        action.setActionCommand(getContainer().getClientController().getServerBoard().getCurrentPhase());
        getContainer().getClientController().sendAction(action);

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
        if(getContainer().getClientController() != null)
            getContainer().getClientController().exit();
        if(getContainer().getServerController() != null)
            getContainer().getServerController().exit();
        loadView("start-view.fxml");
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