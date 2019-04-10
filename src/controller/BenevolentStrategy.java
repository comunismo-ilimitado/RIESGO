package controller;

import java.util.*;
import model.*;

/**
 * This class implements the Benevolent Strategy
 * 
 * @author Bhargav
 * @version 1.0
 *
 */
public class BenevolentStrategy implements IStrategy {

	HelperClass helper = new HelperClass();

	/**
	 * Reinforcement phase based on Benevolent Strategy rules
	 * 
	 * @param player: player object
	 * 
	 */
	public void reinforce(Player player) {
		player.calcArmiesByControlValue(player);
		List<Country> countries = player.getMyCountries(player);
		Country country = null;
		if (countries.size() > helper.getWeakestCountryIndex(countries))
			country = countries.get(helper.getWeakestCountryIndex(countries));
		country.setNoOfArmies(country.getNoOfArmies() + player.getPlayerArmiesNotDeployed());
		player.getMyCountries(player).get(helper.getWeakestCountryIndex(countries))
				.setNoOfArmies(country.getNoOfArmies());
		player.setPlayerTotalArmiesNotDeployed(0);

		ReadingFiles.CountryNameObject.get(country.getName()).setNoOfArmies(country.getNoOfArmies());
	}

	/**
	 * Attack phase based on Benevolent Strategy rules
	 * 
	 * @param player: player object
	 * 
	 */
	public void attack(Player player) {
		return;
	}

	/**
	 * fortification phase based on Benevolent Strategy rules
	 * 
	 * @param player: player object
	 * 
	 */
	public void fortify(Player player) {
		FortificationController fC = new FortificationController();
		List<Country> countries = player.getMyCountries(player);
		Country weakcountry = countries.get(helper.getWeakestCountryIndex(countries));
		countries.remove(weakcountry);
		List<Country> canfortifycountries = new ArrayList<>();
		while (countries.size() > 0) {
			for (int i = 0; i < countries.size(); i++) {
				if (countries.get(i).getNoOfArmies() > 1 && fC.hasPathBFS2(weakcountry, countries.get(i))) {
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
					weakcountry.setNoOfArmies(weakcountry.getNoOfArmies() + strongestcountry.getNoOfArmies() - 1);
					strongestcountry.setNoOfArmies(1);
				}
				int index = helper.getIndex(weakcountry, player.getMyCountries(player));
				player.getMyCountries(player).get(index).setNoOfArmies(weakcountry.getNoOfArmies());
				index = helper.getIndex(strongestcountry, player.getMyCountries(player));
				player.getMyCountries(player).get(index).setNoOfArmies(strongestcountry.getNoOfArmies());
				ReadingFiles.CountryNameObject.get(weakcountry.getName()).setNoOfArmies(weakcountry.getNoOfArmies());
				ReadingFiles.CountryNameObject.get(strongestcountry.getName())
						.setNoOfArmies(strongestcountry.getNoOfArmies());

				break;
			} else {
				weakcountry = countries.get(helper.getWeakestCountryIndex(countries));
				countries.remove(weakcountry);
				canfortifycountries.clear();
			}
		}
	}
}
