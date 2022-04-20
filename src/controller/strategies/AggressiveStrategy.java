package controller.strategies;

import controller.controllers.AttackController;
import controller.controllers.FortificationController;
import controller.editor.ReadingFiles;
import model.Country;
import model.Player;

import java.util.List;

/**
 * This class implements the Aggressive Strategy
 *
 * @author Bhargav
 * @version 1.0
 */
public class AggressiveStrategy extends Strategy {

    /**
     * Reinforcement phase based on Aggressive Strategy rules
     *
     * @param player: player object
     */
    public void reinforce(Player player) {
        exchangeCardsStrategy(player);
        player.calcArmiesByControlValue();
        List<Country> countries = player.getMyCountries(player);

        //if (player.getMyCountries(player).size() > getStrongestCountry(countries))
         Country  strongestcountry = player.getMyCountries(player).get(getStrongestCountry(countries));
        strongestcountry.setNoOfArmies(strongestcountry.getNoOfArmies() + player.getPlayerArmiesNotDeployed()); // nºarmies = nºarmies + all reinforcements
        player.getMyCountries(player).get(getStrongestCountry(countries))
                .setNoOfArmies(strongestcountry.getNoOfArmies());
        ReadingFiles.getCountryNameObject().get(strongestcountry.getName()).setNoOfArmies(strongestcountry.getNoOfArmies());

        player.setPlayerTotalArmiesNotDeployed(0);
    }

    /**
     * Attack phase based on Aggressive Strategy rules
     *
     * @param player: player object
     */
    public void attack(Player player) {
        AttackController aC = new AttackController();
        List<Country> countries = player.getMyCountries(player);
        Country strongestcountry = player.getMyCountries(player).get(getStrongestCountry(countries));
        countries.remove(strongestcountry);
        List<Country> attackable = aC.getMyNeighboursForAttack(strongestcountry);
        while (strongestcountry.getNoOfArmies() > 1 && attackable.size() > 0) {
            Country defender = attackable.get(getWeakestCountryIndex(attackable));
            aC.attackButton(strongestcountry, defender, 0, 0, true);
            AttackController.setCard(true);
            strongestcountry = ReadingFiles.getCountryNameObject().get(strongestcountry.getName());
            attackable = aC.getMyNeighboursForAttack(strongestcountry);
        }
        AttackController.setCard(false);
    }

    /**
     * Fortify phase based on Aggressive Strategy rules
     *
     * @param player: player object
     */
    public void fortify(Player player) {
        FortificationController fC = new FortificationController();
        List<Country> countries = player.getMyCountries(player);
        Country strongestcountry = countries.get(getStrongestCountry(countries));
        player.setTotalCountriesOccupied(countries);
        countries.remove(strongestcountry);
        while (countries.size() > 0) {
            Country fotifyingcountry = countries.get(getStrongestCountry(countries));
            countries.remove(fotifyingcountry);
            if (fotifyingcountry.getNoOfArmies() > 1 && fC.hasPathBFS2(fotifyingcountry, strongestcountry)) {
                strongestcountry.setNoOfArmies(strongestcountry.getNoOfArmies() + fotifyingcountry.getNoOfArmies() - 1);
                fotifyingcountry.setNoOfArmies(1);
                ReadingFiles.getCountryNameObject().get(fotifyingcountry.getName())
                        .setNoOfArmies(fotifyingcountry.getNoOfArmies());
                ReadingFiles.getCountryNameObject().get(strongestcountry.getName())
                        .setNoOfArmies(strongestcountry.getNoOfArmies());

                break;
            }
        }
    }

    /**
     * Get index of the country with maximum number of armies
     *
     * @param countries: list of counties
     * @return index
     */
    public int getStrongestCountry(List<Country> countries) {
        Country strongestcountry = null;
        if (countries.size() > 0)
            strongestcountry = countries.get(0);
        int index = 0;
        for (int i = 1; i < countries.size(); i++) {
            if (countries.get(i).getNoOfArmies() > strongestcountry.getNoOfArmies()) {
                strongestcountry = countries.get(i);
                index = i;
            }
        }
        return index;
    }
}
