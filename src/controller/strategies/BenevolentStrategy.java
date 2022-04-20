package controller.strategies;

import controller.controllers.FortificationController;
import controller.editor.ReadingFiles;
import model.Country;
import model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Benevolent Strategy
 *
 * @author Bhargav
 * @version 1.0
 */
public class BenevolentStrategy extends Strategy {


    /**
     * Reinforcement phase based on Benevolent Strategy rules
     *
     * @param player: player object
     */
    public void reinforce(Player player) {
        player.calcArmiesByControlValue();
        List<Country> countries = player.getMyCountries(player);
        //if (countries.size() > getWeakestCountryIndex(countries))
        Country   country = countries.get(getWeakestCountryIndex(countries));
        int i = player.getPlayerArmiesNotDeployed(); // este valor siempre es 0 por lo que no
        country.setNoOfArmies(country.getNoOfArmies() + player.getPlayerArmiesNotDeployed());
        player.getMyCountries(player).get(getWeakestCountryIndex(countries))
                .setNoOfArmies(country.getNoOfArmies());
        player.setPlayerTotalArmiesNotDeployed(0);

        ReadingFiles.getCountryNameObject().get(country.getName()).setNoOfArmies(country.getNoOfArmies());
    }

    /**
     * Attack phase based on Benevolent Strategy rules
     *
     * @param player: player object
     */
    public void attack(Player player) {

    }

    /**
     * fortification phase based on Benevolent Strategy rules
     *
     * @param player: player object
     */
    public void fortify(Player player) {
        FortificationController fC = new FortificationController();
        List<Country> countries = player.getMyCountries(player);
        Country weakcountry = countries.get(getWeakestCountryIndex(countries));
        countries.remove(weakcountry);
        int k ;
        List<Country> canfortifycountries = new ArrayList<>();
        while (countries.size() > 0) {
           k = weakcountry.getNoOfArmies(); //k<=1
            for (int i = 0; i < countries.size(); i++) {
                if (countries.get(i).getNoOfArmies() > k && fC.hasPathBFS2(weakcountry, countries.get(i))) {// paises con mismo numero de tropas no se pasan tropas.
                    canfortifycountries.add(countries.get(i));
                }
            }
            if (canfortifycountries.size() > 0) {
                Country strongestcountry = canfortifycountries.get(0);
                for (int i = 1; i < canfortifycountries.size(); i++) {
                    if (canfortifycountries.get(i).getNoOfArmies() > strongestcountry.getNoOfArmies()) {
                        strongestcountry = canfortifycountries.get(i);
                    }
                }
                if (strongestcountry != null) {
                    int j = strongestcountry.getNoOfArmies()/2; // se repaten la mitad de las tropas al país débil
                    weakcountry.setNoOfArmies(weakcountry.getNoOfArmies() +  j);
                    strongestcountry.setNoOfArmies(strongestcountry.getNoOfArmies() - j );
                }
                int index = getCountryIndex(weakcountry, player.getMyCountries(player));
                player.getMyCountries(player).get(index).setNoOfArmies(weakcountry.getNoOfArmies());
                index = getCountryIndex(strongestcountry, player.getMyCountries(player));
                player.getMyCountries(player).get(index).setNoOfArmies(strongestcountry.getNoOfArmies());
                ReadingFiles.getCountryNameObject().get(weakcountry.getName()).setNoOfArmies(weakcountry.getNoOfArmies());
                ReadingFiles.getCountryNameObject().get(strongestcountry.getName())
                        .setNoOfArmies(strongestcountry.getNoOfArmies());

                break;
            } else {
                weakcountry = countries.get(getWeakestCountryIndex(countries));
                countries.remove(weakcountry);
                canfortifycountries.clear();
            }
        }
    }

    /**
     * Gets the index of the indicated country in a country list
     *
     * @param country:   country name
     * @param countries: list of countries
     * @return index
     */
    public int getCountryIndex(Country country, List<Country> countries) {
        for (int i = 0; i < countries.size(); i++) {
            if (country.getName().equals(countries.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }
}
