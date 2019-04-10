package controller;

import java.util.*;
import model.*;

/**
 * This class implements the Cheater Strategy
 * 
 * @author Bhargav
 * @version 1.0
 *
 */
public class CheaterStrategy implements IStrategy {

	HelperClass helper = new HelperClass();

	/**
	 * Reinforcement phase based on Cheater Strategy rules
	 * 
	 * @param player: player object
	 * 
	 */
	public void reinforce(Player player) {
		List<Country> countries = player.getMyCountries(player);
		List<CardTypes> cardTypes = new ArrayList<CardTypes>();
		for (int i = 0; i < countries.size(); i++) {
			countries.get(i).setNoOfArmies(countries.get(i).getNoOfArmies() * 2);
			player.getMyCountries(player).get(i).setNoOfArmies(countries.get(i).getNoOfArmies());
			ReadingFiles.CountryNameObject.get(countries.get(i).getName())
					.setNoOfArmies(countries.get(i).getNoOfArmies());
		}
		player.setPlayerTotalArmiesNotDeployed(0);
		player.setPlayerCards(cardTypes);
	}

	/**
	 * Attack phase based on Cheater Strategy rules
	 * 
	 * @param player: player object
	 * 
	 */
	public void attack(Player player) {
		AttackController aC = new AttackController();
		List<Country> mycountries = player.getMyCountries(player);
		for (int i = 0; i < mycountries.size(); i++) {
			List<Country> neighbors = aC.getMyNeighborsForAttack(mycountries.get(i));
			for (int j = 0; j < neighbors.size(); j++) {
				int index = helper.getIndex(neighbors.get(j), mycountries.get(i).getNeighbors());
				neighbors.get(j).setPlayer(player);
				ReadingFiles.CountryNameObject.get(neighbors.get(j).getName()).setPlayer(player);
			}
		}
	}

	/**
	 * fortification phase based on Cheater Strategy rules
	 * 
	 * @param player: player object
	 * 
	 */
	public void fortify(Player player) {
		AttackController aC = new AttackController();
		List<Country> mycountries = player.getMyCountries(player);
		for (int i = 0; i < mycountries.size(); i++) {
			if (aC.getMyNeighborsForAttack(mycountries.get(i)).size() > 0) {
				mycountries.get(i).setNoOfArmies(mycountries.get(i).getNoOfArmies() * 2);
				player.getMyCountries(player).get(i).setNoOfArmies(mycountries.get(i).getNoOfArmies());

				ReadingFiles.CountryNameObject.get(mycountries.get(i).getName())
						.setNoOfArmies(mycountries.get(i).getNoOfArmies());
			}
		}
	}

}
