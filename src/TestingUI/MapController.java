package TestingUI;

import controller.controllers.AttackController;
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
import model.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MapController extends GameController implements Initializable {
    @FXML
    public Pane exitPane, errorPane, dicePane, countriesPane, countryLabels, diceChoosePane, fortificationPane;
    @FXML
    public Label errorLabel, currentPhase, currentPlayer, stats, showNumberDice, numberDice, fortificationNumber;
    @FXML
    private ImageView playerDice1, playerDice2, playerDice3, opponentDice1, opponentDice2;

    private boolean waitingAttackResponse = false;
    private boolean waitingFortifyResponse = false;
    private Image[] dice = new Image[6];
    private int lastError = -1;
    private int lastInfo = -1;
    private int lastAction = -1;
    private HashMap<String, ImageView> colorReference = new HashMap<>();
    private HashMap<String, Label> colorReferLabel = new HashMap<>();
    private HashMap<String, Country> colorCountry = new HashMap<>();

    /**
     * Loads the country images and their labels.
     */
    public void setCountries() {
        HashMap<String, Country> countries = getContainer().getClientController().getServerBoard().getCountries();

        File file = new File(getContainer().getMapsLocation() + "/" + getContainer().getClientController().getServerBoard().getMapName()
                + "/" + "BlackMap" + ".png");
        loadImage(file);
        file = new File(getContainer().getMapsLocation() + "/" + getContainer().getClientController().getServerBoard().getMapName()
                + "/" + "ColorMap" + ".png");
        loadImage(file);

        for (Country country : countries.values()) {
            colorCountry.put(country.getColor(), country);
            file = new File(getContainer().getMapsLocation() + "/" + getContainer().getClientController().getServerBoard().getMapName()
                    + "/" + country.getName() + ".png");

            ImageView imageView = loadImage(file);
            colorReference.put(country.getColor(), imageView);

            Label label = new Label(country.getName() + "\n 0 ");
            label.setAlignment(Pos.CENTER);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setLayoutX(country.getPosx());
            label.setLayoutY(country.getPosy());
            countryLabels.getChildren().add(label);
            colorReferLabel.put(country.getColor(), label);
        }
    }

    /**
     * @param file where the image is located
     * @return The loaded ImageView
     */
    private ImageView loadImage(File file) {
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setMouseTransparent(true);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        countriesPane.getChildren().add(imageView);
        return (imageView);
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

    @Override
    public void onLoad() {
        getContainer().getClientController().setMapController(this);
        setCountries();
        update();
    }

    public void update() {
        ClientController controller = getContainer().getClientController();
        //Gestion de errores
        {
            if (!controller.getServerBoard().getErrors().isEmpty()) {
                if (controller.getServerBoard().getErrors().size() != lastError) {
                    lastError = controller.getServerBoard().getErrors().size();
                    Board.ErrorMessage error = controller.getServerBoard().getErrors().get(lastError - 1);
                    if (error.getPlayer().getPlayerName().equals(controller.getPlayerConfiguration().getName())) {
                        System.out.println("Error: " + error.text);

                        if (getContainer().getBundle().containsKey(error.text))
                            error.text = getContainer().getBundle().getString(error.text);
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
                    currentPhase.setText(getContainer().getBundle().getString("faseTag") + "\n"
                            + getContainer().getClientController().getServerBoard().getCurrentPhase().replaceAll("Finish", ""));
                }
            });
        }

        // Actualizar Current player
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    currentPlayer.setText(getContainer().getBundle().getString("playerTag") + "\n"
                            + getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName());
                }
            });
        }

        // Actualizar Estadisticas
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stats.setText(getContainer().getBundle().getString("statsTag") + "\n Occupied countries: "
                            + getContainer().getClientController().getServerBoard().getCountriesPercentage() + " %\n Continents occupied: " +
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
                        colorReferLabel.get(country.getColor()).setText(country.getName() + "\n" + country.getNoOfArmies());
                    }
                });
            }
            //Países seleccionados para ataque
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Country attacker = getContainer().getClientController().getServerBoard().getAttackCountry1();
                    if (attacker != null) {
                        if (getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName()
                                .equals(getContainer().getClientController().getPlayerConfiguration().getName()))
                            setShadedColorCountry(attacker, Color.AQUA);
                    }
                    Country defender = getContainer().getClientController().getServerBoard().getAttackCountry2();
                    if (defender != null) {
                        if (getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName()
                                .equals(getContainer().getClientController().getPlayerConfiguration().getName()))
                            setShadedColorCountry(defender, Color.AQUA);
                        int attackDiceAvailable = AttackController.setNoOfDice(attacker, 'A');
                        if (attackDiceAvailable == 0) {
                            errorLabel.setText(getContainer().getBundle().getString("noAttackTag"));
                            errorPane.setVisible(true);
                        } else {

                            if (!waitingAttackResponse) {
                                showNumberDice.setText(getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName() + ": " +
                                        getContainer().getBundle().getString("howManyDiceTag"));
                                numberDice.setText("1");
                                diceChoosePane.setVisible(true);
                            }

                        }

                    }
                }
            });

            //Países seleccionados para fortify
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Country donor = getContainer().getClientController().getServerBoard().getSelectedCountry1();
                    if (donor != null) {
                        if (getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName()
                                .equals(getContainer().getClientController().getPlayerConfiguration().getName()))
                            setShadedColorCountry(donor, Color.AQUA);
                    }else{
                        waitingFortifyResponse = false;
                    }
                    Country beneficiary = getContainer().getClientController().getServerBoard().getSelectedCountry2();
                    if (beneficiary != null) {
                        if (getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName()
                                .equals(getContainer().getClientController().getPlayerConfiguration().getName()))
                            setShadedColorCountry(beneficiary, Color.AQUA);
                        if (!waitingFortifyResponse) {
                            fortificationPane.setVisible(true);
                            waitingFortifyResponse = false;
                        }
                    }
                }
            });

            {
                //Update Info
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (!controller.getServerBoard().getServerInfo().isEmpty()) {
                            if (controller.getServerBoard().getServerInfo().size() != lastInfo) {
                                lastInfo = controller.getServerBoard().getServerInfo().size();
                                String info = controller.getServerBoard().getServerInfo().get(lastInfo - 1);
                                if(info.contains("Attack Response")){
                                    waitingAttackResponse = false;
                                    String[] attackresponse = info.split(" ");
                                    String dice1 = attackresponse[2];
                                    String dice2 = attackresponse[3];
                                    String attackerCountryName = attackresponse[4];
                                    Country attackCountry = getContainer().getClientController().getServerBoard()
                                            .getCountries().get(attackerCountryName);
                                    String defenderCountryName = attackresponse[5];
                                    Country defenderCountry = getContainer().getClientController().getServerBoard()
                                            .getCountries().get(attackerCountryName);
                                    Integer[] dices1 = Arrays.stream(dice1
                                            .split(",")).map(Integer::parseInt).toArray(Integer[]::new);

                                    Integer[] dices2 = Arrays.stream(dice2
                                            .split(",")).map(Integer::parseInt).toArray(Integer[]::new);
                                    showDiceImages(dices1,dices2);
                                }
                            }
                        }
                    }
                });
            }

        }

        {
            if (lastAction != controller.getServerBoard().getActions().size()) {
                lastAction = controller.getServerBoard().getActions().size();
            }
        }
    }

    private void showDiceImages(Integer[] dices1, Integer[] dices2) {
        dicePane.setVisible(true);
        switch(dices1.length){
            case 1:{
                playerDice1.setImage(dice[dices1[0]-1]);
                playerDice2.setVisible(false);
                playerDice3.setVisible(false);
                break;
            }
            case 2:{
                playerDice1.setImage(dice[dices1[0]-1]);
                playerDice2.setImage(dice[dices1[1]-1]);
                playerDice3.setVisible(false);
                break;
            }
            case 3: {
                playerDice1.setImage(dice[dices1[0]-1]);
                playerDice2.setImage(dice[dices1[1]-1]);
                playerDice2.setImage(dice[dices1[2]-1]);
                break;
            }
        }
        switch(dices2.length){
            case 1:{
                opponentDice1.setImage(dice[dices2[0]-1]);
                opponentDice2.setVisible(false);
                break;
            }
            case 2:{
                opponentDice1.setImage(dice[dices2[0]-1]);
                opponentDice2.setImage(dice[dices2[1]-1]);
                break;
            }
        }
    }

    public double changeHue(Color color) {
        if (color.getHue() > 180.0) {
            return 360.0 - color.getHue() / 180.0;
        } else {
            return color.getHue() / 180.0;
        }
    }

    public void setColorOfCountry(Country country, Color color) {
        ImageView imageView = colorReference.get(country.getColor());
        ColorAdjust blackout = new ColorAdjust();
        blackout.setSaturation(0.6);
        blackout.setHue(changeHue(color));
        imageView.setEffect(blackout);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
    }

    public void setShadedColorCountry(Country country, Color color) {
        ImageView imageView = colorReference.get(country.getColor());
        Lighting colorSettings = new Lighting();
        colorSettings.setLight(new Light.Distant(45, 45, Color.AQUA));

        imageView.setEffect(colorSettings);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
    }

    @FXML
    private void removeArmiesButton() {
        int i = Integer.parseInt(numberDice.getText());
        if (i > 1) {
            i--;
            numberDice.setText(String.valueOf(i));
            diceChoosePane.setCache(true);
            diceChoosePane.setCacheHint(CacheHint.SPEED);
        }
    }

    @FXML
    private void addArmiesButton() {
        int i = Integer.parseInt(numberDice.getText());
        if (i < AttackController.setNoOfDice(getContainer().getClientController().getServerBoard().getAttackCountry1(), 'A')) {
            i++;
            numberDice.setText(String.valueOf(i));
        }
    }

    /**
     * Sets the number of attack and defence dice and executes the attack.
     */
    @FXML
    private void confirmDiceButton() {
        // Se obtienen los paises
        Country attacker = getContainer().getClientController().getServerBoard().getAttackCountry1();
        Country defender = getContainer().getClientController().getServerBoard().getAttackCountry2();
        // Se obtienen los dados que tiran cada uno
        int attackDice = Integer.parseInt(numberDice.getText());
        int defenceDice = AttackController.setNoOfDice(defender, 'D');
        // Se mandan los atos al servidor
        getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Dice " + attackDice + " A", attacker.getOwner()));
        getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Dice " + defenceDice + " D", defender.getOwner()));
        waitingAttackResponse = true;
        diceChoosePane.setVisible(false);
    }


    @FXML
    private void addFortificationButton() {
        int i = Integer.parseInt(fortificationNumber.getText());
        if (i < getContainer().getClientController().getServerBoard().getSelectedCountry1().getNoOfArmies() - 1) {
            i++;
            fortificationNumber.setText(String.valueOf(i));
        }
    }

    @FXML
    private void removeFortificationButton() {
        int i = Integer.parseInt(fortificationNumber.getText());
        if (i > 1) {
            i--;
            fortificationNumber.setText(String.valueOf(i));
        }
    }

    @FXML
    private void confirmFortificationButton() {
        int fortificationArmies = Integer.parseInt(fortificationNumber.getText());
        Player player = getContainer().getClientController().getServerBoard().getCurrentPlayer();
        getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Reinforce "+String.valueOf(fortificationArmies), player));
        fortificationPane.setVisible(false);
    }





    @FXML
    private void mapSelected(MouseEvent event) {
        ImageView country = (ImageView) event.getSource();
        Color color = country.getImage().getPixelReader().getColor((int) event.getX(), (int) event.getY());
        country = colorReference.get(color.toString());
        if (country == null) return;

        ClientUpdate.ClientAction action = new ClientUpdate.ClientAction();
        action.setActionCommand(colorCountry.get(color.toString()).getName() + " |5|");
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

        System.out.println("Intercambio de cartas");
    }

    @FXML
    private void back(MouseEvent event) {
        if (getContainer().getClientController() != null)
            getContainer().getClientController().exit();
        if (getContainer().getServerController() != null)
            getContainer().getServerController().exit();
        loadView("start-view.fxml");
    }

    /**
     * Saves the game and returns to menu.
     */
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

    /**
     * Saves the exit pane.
     */
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
    private void exit() {
        exitPane.setVisible(true);
    }

    @FXML
    private void saveGameAndExit() {
        if (getContainer().getClientController() != null)
            getContainer().getClientController().exit();
        if (getContainer().getServerController() != null)
            getContainer().getServerController().exit();
        getContainer().getServerController().saveGame();
        loadView("start-view.fxml");
    }

    /**
     * Returns to menu without saving the game.
     */
    @FXML
    private void exitWithoutSave() {
        if (getContainer().getClientController() != null)
            getContainer().getClientController().exit();
        if (getContainer().getServerController() != null)
            getContainer().getServerController().exit();
        loadView("start-view.fxml");
    }

    /**
     * Hides error panes.
     */
    @FXML
    private void returnGame() {
        exitPane.setVisible(false);
        errorPane.setVisible(false);
        dicePane.setVisible(false);
        diceChoosePane.setVisible(false);
        fortificationPane.setVisible(false);
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