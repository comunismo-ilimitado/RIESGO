package controller.controllers;

import controller.editor.ReadingFiles;
import controller.game.MainController;
import model.CardTypes;
import model.Country;
import model.Player;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * This controller class has all the methods needs for the reinforcement phase
 * of the game
 *
 * @author neerajpreet
 * @version 1.1
 */
public class ReinforcementController {

    /**
     * Checks if player has more than 5 cards
     *
     * @param player: Player object must be given to fetch number of cards
     * @return true if player has more than 5 cards else false
     */
    public boolean hasMoreCards(Player player) {
        return player.getPlayerCards().size() >= 5;
    }

    /**
     * Number of times player exchanges the cards
     *
     * @param list:   list of cards
     * @param player: player object must be given to fetch the player cards
     * @return message how player can exchange cards
     */
    public String exchangeCards(List<CardTypes> list, Player player) {
        List<CardTypes> playercards = player.getPlayerCards();
        HashSet<CardTypes> temp = new HashSet<>(list);
        if (list.size() == 3) {
            if ((temp.size() == 1 && (temp.contains(CardTypes.Artillery) || temp.contains(CardTypes.Cavalry)
                    || temp.contains(CardTypes.Infantry)))
                    || (temp.size() == 3 && temp.contains(CardTypes.Artillery) && temp.contains(CardTypes.Cavalry)
                    && temp.contains(CardTypes.Infantry))) {
                if (temp.size() == 1) {
                    CardTypes card = temp.iterator().next();
                    playercards.remove(card);
                    playercards.remove(card);
                    playercards.remove(card);
                    player.setPlayerCards(playercards);
                } else if (temp.size() == 3) {
                    playercards.remove(CardTypes.Artillery);
                    playercards.remove(CardTypes.Cavalry);
                    playercards.remove(CardTypes.Infantry);
                    player.setPlayerCards(playercards);
                }
                player.setCardExchangeValue(player.getCardExchangeValue() + 1);
                int number = player.getCardExchangeValue();
                player.setPlayerTotalArmiesNotDeployed(player.getPlayerArmiesNotDeployed() + number * 5);
                return "";
            } else
                return "Cannot exchange these cards for armies and select atleast 3 cards";
        } else
            return "Select 3 cards of the same type or all unique";
    }

    /**
     * Checks the number of armies that are not deployed
     *
     * @param country source country of the player
     * @return some value of type string
     */
    public String addArmies(Country country) {
        int index = country.getOwner().getPlayerId();
        Player player = ReadingFiles.getPlayerId().get(index);
        if (player.getPlayerArmiesNotDeployed() == 0) {
            return "NO ARMIES LEFT, PLEASE CLICK FINISH REINFORCEMENT";
        } else {
            updateValue(player, country);
            return "";
        }
    }

    /**
     * Calculates the number of armies each player gets to reinforce
     *
     * @param player: player object for which the armies are calculated
     */
    public void calculateReinforcementArmies(Player player) {
        int totalPlayerCountries = 0;
        for (Map.Entry<String, Country> entry : ReadingFiles.getCountryNameObject().entrySet()) {
            if (entry.getValue().getOwner().getPlayerId() == (player.getPlayerId())) {
                totalPlayerCountries++;
            }
        }
        float totalReinforcementArmies;
        totalReinforcementArmies = (float) totalPlayerCountries / 3;
        int armies = 0;
        if (totalReinforcementArmies < 3.0) {
            armies = armies + 3;
        } else {
            armies = armies + (int) totalReinforcementArmies;
        }
        armies = armies + player.calcArmiesByControlValue();
        player.setPlayerTotalArmiesNotDeployed(player.getPlayerArmiesNotDeployed() + armies);
    }


    /**
     * Updates the number of armies player owns and armies not deployed
     *
     * @param player:  player object that updates
     * @param country: country object where armies updated
     */
    public void updateValue(Player player, Country country) {
        country.setNoOfArmies(country.getNoOfArmies() + 1);
        int x = ReadingFiles.getContinentNameObject().get(country.getContinent().getName()).getCountries().indexOf(country);
        ReadingFiles.getContinentNameObject().get(country.getContinent().getName()).getCountries().get(x)
                .setNoOfArmies(country.getNoOfArmies());
        player.setPlayerTotalArmiesNotDeployed(player.getPlayerArmiesNotDeployed() - 1);
    }

    /**
     * Checks whether player has deployed all his armies or not
     *
     * @param player: player object
     */
    public String endReinforcementCheck(Player player) {
        if (player.getPlayerArmiesNotDeployed() == 0) {
            return "Please deploy all your armies before proceeding to attack";
        } else
            return null;
    }

    /**
     * This method checks the validation of the reinforcement phase
     *
     * @param mainController
     */
    public void reinforcementPhase(MainController mainController) {
        mainController.textarea("Currently in Reinforcement Mode");
        mainController.buttonCards(true);
        mainController.changed();
        mainController.getFrame().ActivateAll();
        mainController.OnlyNeeded(mainController.playerObjet(mainController.getCurrentPlayer()).getTotalCountriesOccupied());
        mainController.getReinforcementController().calculateReinforcementArmies(mainController.playerObjet(mainController.getCurrentPlayer()));
        mainController.getFrame().error("Its Player:- " + (mainController.getCurrentPlayer() + 1) + " Turn");
    }

    /**
     * This method prepares the game for attack phase
     *
     * @param mainController
     */
    public void finishReinforcement(MainController mainController) {
        mainController.getCardTypesList().clear();
        mainController.buttonCards(false);
        mainController.setCurrentPhase("Finish Attack");
        mainController.getFrame().nextAction.setText("Finish Attack");
        mainController.changed();
        mainController.setAttackCountry1(null);
        mainController.setAttackCountry2(null);
        mainController.getCardTypesList().clear();
        mainController.getFrame().jLabeCardl.setText(mainController.getCardTypesList().toString());
        mainController.getCardTypesList().clear();
        mainController.getFrame().jLabeCardl.setText(mainController.getCardTypesList().toString());

    }
}
