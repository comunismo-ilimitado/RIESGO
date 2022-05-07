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

public class MapController extends GameController implements Initializable {
    @FXML
    public Pane exitPane, errorPane, dicePane, countriesPane, countryLabels, diceChoosePane, fortificationPane,
            cardsPane;
    @FXML
    public Label errorLabel, currentPhase, currentPlayer, stats, armiesLeft, numberDice, fortificationNumber,
            attackResponseLabel;
    @FXML
    private ImageView playerDice1, playerDice2, playerDice3, opponentDice1, opponentDice2, card1, card2, card3, card4,
            card5, cardSelected1, cardSelected2, cardSelected3;
    private ImageView[] cardsImages = {card1, card2, card3, card4, card5};
    private ImageView[] selectedCardsImages = {cardSelected1, cardSelected2, cardSelected3};
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

        file = new File("Resources/TestingUI/Images/Drawings/infantry.png");
        cardType[0] = new Image(file.toURI().toString());
        file = new File("Resources/TestingUI/Images/Drawings/cavalry.png");
        cardType[1] = new Image(file.toURI().toString());
        file = new File("Resources/TestingUI/Images/Drawings/artillery.png");
        cardType[2] = new Image(file.toURI().toString());
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
        blackout.setSaturation(color.getSaturation());
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

    /**
     * Removes one from the number of dice to attack.
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
     * Ads one to the number of dice to attack.
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
     * Sets the number of attack and defence dice and executes the attack.
     */
    @FXML
    //TODO configurar la accion de hacer all out correctamente
    private void confirmDiceButton() {
        // Se obtienen los paises
        Country attacker = getContainer().getClientController().getServerBoard().getAttackCountry1();
        Country defender = getContainer().getClientController().getServerBoard().getAttackCountry2();
        if (allOut.isSelected()) {
            getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Dice -2 A", attacker.getOwner()));
            getContainer().getClientController().sendAction(new ClientUpdate.ClientAction("Dice -1 D", defender.getOwner()));
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

    /**
     * Sends the fortification action with the selected number of dice.
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

    @FXML
    private void nextPhase() {
        ClientUpdate.ClientAction action = new ClientUpdate.ClientAction();
        action.setActionCommand(getContainer().getClientController().getServerBoard().getCurrentPhase());
        getContainer().getClientController().sendAction(action);
    }

    /**
     * Shows the number of cards of each type a player owns.
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
        for (CardTypes card : cardsList) {
            cardImage = (ImageView) cards.getChildren().get(cardIndex);
            switch (card) {
                case Infantry:
                    cardImage.setImage(cardType[0]);
                    cardIndex++;
                    break;
                case Cavalry:
                    cardImage.setImage(cardType[1]);
                    cardIndex++;
                    break;
                case Artillery:
                    cardImage.setImage(cardType[2]);
                    cardIndex++;
                    break;
            }
        }
    }

    @FXML
    private void cardSelected(MouseEvent event) {
        ImageView cardImage = (ImageView) event.getSource();
        ImageView selectedCardImage = (ImageView) selectedCards.getChildren().get(selectedCardIndex);
        if (cardImage.getImage() != null) {
            selectedCardImage.setImage(cardImage.getImage());
            selectedCardIndex++;
        }
        cardImage.setImage(null);
    }
    @FXML
    //TODO Implementar la accion de mandar las tres cartas seleccioandas
    private void confirmCardSelection() {

    }

    @FXML
    private void back() {
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
        cancelProcesses();
        exitPane.setVisible(false);
        errorPane.setVisible(false);
        dicePane.setVisible(false);
        diceChoosePane.setVisible(false);
        fortificationPane.setVisible(false);
        cardsPane.setVisible(false);
    }

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