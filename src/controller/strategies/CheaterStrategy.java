package controller.strategies;

import controller.controllers.AttackController;
import controller.editor.ReadingFiles;
import model.CardTypes;
import model.Country;
import model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Cheater Strategy
 *
 * @author Bhargav
 * @version 1.0
 */
public class CheaterStrategy extends Strategy {

    /**
     * Reinforcement phase based on Cheater Strategy rules
     *
     * @param player: player object
     */
    public void reinforce(Player player) {
        List<Country> countries = player.getMyCountries(player);
        List<CardTypes> cardTypes = new ArrayList<>();
        for (int i = 0; i < countries.size(); i++) {
            countries.get(i).setNoOfArmies(countries.get(i).getNoOfArmies() * 2);
            player.getMyCountries(player).get(i).setNoOfArmies(countries.get(i).getNoOfArmies());
            ReadingFiles.getCountryNameObject().get(countries.get(i).getName())
                    .setNoOfArmies(countries.get(i).getNoOfArmies());
        }
        player.setPlayerTotalArmiesNotDeployed(0);
        player.setPlayerCards(cardTypes);
    }

    /**
     * Attack phase based on Cheater Strategy rules
     *
     * @param player: player object
     */
    public void attack(Player player) {
        AttackController aC = new AttackController();
        List<Country> mycountries = player.getMyCountries(player);
        for (Country mycountry : mycountries) {
            List<Country> neighbors = aC.getMyNeighboursForAttack(mycountry);
            for (Country neighbor : neighbors) {
                neighbor.setPlayer(player);
                ReadingFiles.getCountryNameObject().get(neighbor.getName()).setPlayer(player);
            }
        }
    }

    /**
     * Fortification phase based on Cheater Strategy rules
     *
     * @param player: player object
     */
    public void fortify(Player player) {
        AttackController aC = new AttackController();
        List<Country> mycountries = player.getMyCountries(player);
        for (int i = 0; i < mycountries.size(); i++) {
            if (aC.getMyNeighboursForAttack(mycountries.get(i)).size() > 0) {
                mycountries.get(i).setNoOfArmies(mycountries.get(i).getNoOfArmies() * 2);
                player.getMyCountries(player).get(i).setNoOfArmies(mycountries.get(i).getNoOfArmies());

                ReadingFiles.getCountryNameObject().get(mycountries.get(i).getName())
                        .setNoOfArmies(mycountries.get(i).getNoOfArmies());
            }
        }
    }

}
