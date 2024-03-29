package TestingUI;

import controller.controllers.AttackController;
import controller.controllers.net.ClientController;
import controller.net.Board;
import controller.net.ClientUpdate;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import model.CardTypes;
import model.Country;
import model.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


/**
 * This class is used for controlling the map-view.fxml
 */
public class MapController extends GameController implements Initializable {
    @FXML
    public Pane exitPane, errorPane, dicePane, countriesPane, countryLabels, diceChoosePane, fortificationPane,
            cardsPane;
    @FXML
    public Label errorLabel, currentPhase, currentPlayer, stats, armiesLeft, numberDice, fortificationNumber,
            attackResponseLabel;
    @FXML
    private ImageView playerDice1, playerDice2, playerDice3, opponentDice1, opponentDice2, fullMap;
    private int infantryCards = 0;
    private int cavalryCards = 0;
    private int artilleryCards = 0;
    @FXML
    private HBox cards, selectedCards;
    @FXML
    private CheckBox allOut;
    @FXML
    private Circle colorCircle;

    private boolean waitingAttackResponse = false;
    private boolean waitingFortifyResponse = false;
    private final Image[] dice = new Image[6];
    private final Image[] cardType = new Image[3];
    private int cardIndex, selectedCardIndex;
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
                + "/" + "ColorMap" + ".png");
        Image image = new Image(file.toURI().toString());
        fullMap.setImage(image);
        if(getContainer().getClientController().getServerBoard().getMapName().equals("Europe")){
            fullMap.setLayoutX(328);
        }

        file = new File(getContainer().getMapsLocation() + "/" + getContainer().getClientController().getServerBoard().getMapName()
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
     * Loads the image in the view
     *
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
        if(getContainer().getClientController().getServerBoard().getMapName().equals("Europe")){
            imageView.setLayoutX(328);
        }
        return (imageView);
    }

    /**
     * Initialize cards and dice images.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The ResourceBundle for the translator
     */
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

        file = new File("Resources/TestingUI/Images/Drawings/infantry.png");
        cardType[0] = new Image(file.toURI().toString());
        file = new File("Resources/TestingUI/Images/Drawings/cavalry.png");
        cardType[1] = new Image(file.toURI().toString());
        file = new File("Resources/TestingUI/Images/Drawings/artillery.png");
        cardType[2] = new Image(file.toURI().toString());
    }

    /**
     * Method executed when loading the view
     */
    @Override
    public void onLoad() {
        getContainer().getClientController().setMapController(this);
        setCountries();
        update();
    }

    /**
     * Method in which there are updates in thread for:
     * - Controlling errors
     * - Check winner
     * - Update phase, player, armies, stats, color from the
     *   countries and information about attacks, etc.
     * - Update selected countries for attack and fortify
     */
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

        //Comprobacion si ha ganado alguien
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(getContainer().getServerController().isFinishedGame()){
                        loadView("results-view.fxml");
                    }
                }
            });
        }

        //Actualizar fase
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    currentPhase.setText(getContainer().getBundle().getString("phaseTag") + "\n"
                            + getContainer().getClientController().getServerBoard().getCurrentPhase().replaceAll("Finish", ""));
                }
            });
        }

        // Actualizar Current player
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    java.awt.Color c = getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerColor();
                    colorCircle.setFill(Color.rgb(c.getRed(), c.getGreen(), c.getBlue()));
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

        // Actualizar las tropas disponibles para desplegar
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    armiesLeft.setText(Integer.toString(getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerArmiesNotDeployed()));
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
                    if(getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName()
                            .equals(getContainer().getClientController().getPlayerConfiguration().getName())) {

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
                                    numberDice.setText("1");
                                    diceChoosePane.setVisible(true);
                                }
                            }
                        }
                    }
                }
            });

            //Países seleccionados para fortify
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName()
                            .equals(getContainer().getClientController().getPlayerConfiguration().getName())) {

                        Country donor = getContainer().getClientController().getServerBoard().getSelectedCountry1();
                        if (donor != null) {
                            if (getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerName()
                                    .equals(getContainer().getClientController().getPlayerConfiguration().getName()))
                                setShadedColorCountry(donor, Color.AQUA);
                        } else {
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
                                    if(getContainer().getClientController().getPlayerConfiguration().getName().equals(attackresponse[6].replace(",", " "))){
                                        String dice1 = attackresponse[2];
                                        String dice2 = attackresponse[3];
                                        String attackerCountryName = attackresponse[4].replace(",", " ");
                                        Country attackCountry = getContainer().getClientController().getServerBoard()
                                                .getCountries().get(attackerCountryName);
                                        String defenderCountryName = attackresponse[5].replace(",", " ");
                                        Country defenderCountry = getContainer().getClientController().getServerBoard()
                                                .getCountries().get(attackerCountryName);
                                        Integer[] dices1 = Arrays.stream(dice1
                                                .split(",")).map(Integer::parseInt).toArray(Integer[]::new);

                                        Integer[] dices2 = Arrays.stream(dice2
                                                .split(",")).map(Integer::parseInt).toArray(Integer[]::new);
                                        // TODO translate text
                                        showDiceImages(dices1,dices2, "Successfully attacked country!");
                                    }
                                    if(getContainer().getClientController().getPlayerConfiguration().getName().equals(attackresponse[7].replace(",", " "))){
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
                                        // TODO translate text
                                        showDiceImages(dices1,dices2, "You have been attacked");
                                    }
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

    /**
     * Method for showing the corresponding dices Pane.
     * @param dices1
     * @param dices2
     * @param info
     */
    private void showDiceImages(Integer[] dices1, Integer[] dices2, String info) {
        attackResponseLabel.setText(info);
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

    /**
     * This method is used for changing color
     * @param color
     * @return
     */
    public double changeHue(Color color) {
        if (color.getHue() > 180.0) {
            return 360.0 - color.getHue() / 180.0;
        } else {
            return color.getHue() / 180.0;
        }
    }

    /**
     * This method changes the color of the country depending on the player.
     *
     * @param country
     * @param color
     */
    public void setColorOfCountry(Country country, Color color) {
        ImageView imageView = colorReference.get(country.getColor());
        ColorAdjust blackout = new ColorAdjust();
        blackout.setSaturation(color.getSaturation());
        blackout.setHue(changeHue(color));
        imageView.setEffect(blackout);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
    }

    /**
     * This method changes the color of the country when it is selected for attacking/fortify.
     *
     * @param country
     * @param color
     */
    public void setShadedColorCountry(Country country, Color color) {
        ImageView imageView = colorReference.get(country.getColor());
        Lighting colorSettings = new Lighting();
        colorSettings.setLight(new Light.Distant(45, 45, Color.AQUA));

        imageView.setEffect(colorSettings);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
    }

    /**
     * Removes one from the number of armies available to attack.
     * Indeed, it removes a throwable dice.
     */
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

    /**
     * Add one from the number of armies available to attack.
     * Indeed, it adds a throwable dice.
     */
    @FXML
    private void addArmiesButton() {
        int i = Integer.parseInt(numberDice.getText());
        if (i < AttackController.setNoOfDice(getContainer().getClientController().getServerBoard().getAttackCountry1(), 'A')) {
            i++;
            numberDice.setText(String.valueOf(i));
        }
    }

    /**
     * Sets the number of attack and defence throwable dices and executes the attack.
     */
    @FXML
    private void confirmDiceButton() {
        // Se obtienen los paises
        Country attacker = getContainer().getClientController().getServerBoard().getAttackCountry1();
        Country defender = getContainer().getClientController().getServerBoard().getAttackCountry2();
        if (allOut.isSelected()) {
            getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Dice -2 A", attacker.getOwner()));
        } else {
            // Se obtienen los dados que tiran cada uno
            int attackDice = Integer.parseInt(numberDice.getText());
            int defenceDice = AttackController.setNoOfDice(defender, 'D');
            // Se mandan los atos al servidor
            getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Dice " + attackDice + " A", attacker.getOwner()));
            getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Dice " + defenceDice + " D", defender.getOwner()));
        }
        waitingAttackResponse = true;
        diceChoosePane.setVisible(false);
    }

    /**
     * Adds one to the counter of armies selected to fortify.
     */
    @FXML
    private void addFortificationButton() {
        int i = Integer.parseInt(fortificationNumber.getText());
        if (i < getContainer().getClientController().getServerBoard().getSelectedCountry1().getNoOfArmies() - 1) {
            i++;
            fortificationNumber.setText(String.valueOf(i));
        }
    }

    /**
     * Removes one from the counter of armies selected to fortify.
     */
    @FXML
    private void removeFortificationButton() {
        int i = Integer.parseInt(fortificationNumber.getText());
        if (i > 1) {
            i--;
            fortificationNumber.setText(String.valueOf(i));
        }
    }

    /**
     * Sends the fortification action with the selected number of armies.
     */
    @FXML
    private void confirmFortificationButton() {
        int fortificationArmies = Integer.parseInt(fortificationNumber.getText());
        Player player = getContainer().getClientController().getServerBoard().getCurrentPlayer();
        getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Reinforce "+String.valueOf(fortificationArmies), player));
        fortificationPane.setVisible(false);
    }

    /**
     * Sends an action with the country selected by the player.
     */
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

    /**
     * Send the action to change to the next phase.
     */
    @FXML
    private void nextPhase() {
        ClientUpdate.ClientAction action = new ClientUpdate.ClientAction();
        action.setActionCommand(getContainer().getClientController().getServerBoard().getCurrentPhase());
        getContainer().getClientController().sendAction(action);
    }

    /**
     * Shows the cards panel.
     */
    @FXML
    private void exchangeCards() {
        cardsPane.setVisible(true);
        // Se Limpia la lista de cartas seleccionadas.
        ImageView selectedCard;
        for (int i = 0; i < 3; i++) {
            selectedCard = (ImageView) selectedCards.getChildren().get(i);
            selectedCard.setImage(null);
        }
        selectedCardIndex = 0;
        // Se pintan las cartas de la mano.
        cardIndex = 0;
        List<CardTypes> cardsList = getContainer().getClientController().getServerBoard().getCurrentPlayer().getPlayerCards();
        ImageView cardImage;
        infantryCards = 0;
        cavalryCards = 0;
        artilleryCards = 0;
        for (CardTypes card : cardsList) {
            cardImage = (ImageView) cards.getChildren().get(cardIndex);
            switch (card) {
                case Infantry:
                    cardImage.setImage(cardType[0]);
                    cardIndex++;
                    infantryCards++;
                    break;
                case Cavalry:
                    cardImage.setImage(cardType[1]);
                    cardIndex++;
                    cavalryCards++;
                    break;
                case Artillery:
                    cardImage.setImage(cardType[2]);
                    cardIndex++;
                    artilleryCards++;
                    break;
            }
        }
    }

    /**
     * Moves the clicked card to the selected cards section.
     */
    @FXML
    private void cardSelected(MouseEvent event) {
        // Se obtiene la imagen clicada
        ImageView cardImage = (ImageView) event.getSource();
        Image card = cardImage.getImage();

        // Se obtiene el espacio done hay que mover la carta
        ImageView selectedCardImage = (ImageView) selectedCards.getChildren().get(selectedCardIndex);
        // Si no se ha seleccionado una carta vacia, se mueve la carta al espacio que toque.
        if (cardImage.getImage() != null) {
            selectedCardImage.setImage(cardImage.getImage());
            selectedCardIndex++;

            // Se manda la señal de seleccion
            Player player = getContainer().getClientController().getServerBoard().getCurrentPlayer();

            if (cardType[0].equals(card)) {
                getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Infantry " + infantryCards, player));
                infantryCards--;
            } else if (cardType[1].equals(card)) {
                getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Cavalry " + cavalryCards, player));
                cavalryCards--;
            } else if (cardType[2].equals(card)) {
                getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Artillery " + artilleryCards, player));
                artilleryCards--;
            }

            cardImage.setImage(null);
        }
    }

    /**
     * Manda la señal para intercambiar cartas.
     */
    @FXML
    private void confirmCardSelection() {
        Player player = getContainer().getClientController().getServerBoard().getCurrentPlayer();
        getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Exchange Cards", player));
        exchangeCards();
    }

    /**
     * Method for going back to the start-view.
     */
    @FXML
    private void back() {
        if (getContainer().getClientController() != null)
            getContainer().getClientController().exit();
        if (getContainer().getServerController() != null)
            getContainer().getServerController().exit();
        loadView("start-view.fxml");
    }

    /**
     * Shows the exit Pane
     */
    @FXML
    private void exit() {
        exitPane.setVisible(true);
    }

    /**
     * Save the game and go back to the start-view.
     */
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
     * Returns to the start-view without saving the game.
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
     * Hides all panes when clicking outside them.
     */
    @FXML
    private void returnGame() {
        cancelProcesses();
        exitPane.setVisible(false);
        errorPane.setVisible(false);
        dicePane.setVisible(false);
        diceChoosePane.setVisible(false);
        fortificationPane.setVisible(false);
        cardsPane.setVisible(false);
    }

    /**
     * Cancel processes in the server while trying to returnGame in
     * the middle of an attack or fortification pane.
     */
    private void cancelProcesses() {
        if (diceChoosePane.isVisible()){
            Country attacker = getContainer().getClientController().getServerBoard().getAttackCountry1();
            getContainer().getClientController().sendAction(new ClientUpdate.ClientAction(attacker.getName(), null));
        }
        if(fortificationPane.isVisible()){
            Country attacker = getContainer().getClientController().getServerBoard().getSelectedCountry1();
            getContainer().getClientController().sendAction(new ClientUpdate.ClientAction(attacker.getName(), null));
        }
    }

}