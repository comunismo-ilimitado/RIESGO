package controller;

import java.util.*;
import model.CardTypes;

import model.Continent;
import model.Country;
import model.Player;

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
		if (player.getPlayerCards().size() >= 5)
			return true;
		else
			return false;
	}

	/**
	 * number of times player exchanges the cards
	 * 
	 * @param list: list of cards
	 * @param player: player object must be given to fetch the player cards
	 * @return message how player can exchange cards
	 */
	public String exchangeCards(List<CardTypes> list, Player player) {
		HashSet<CardTypes> temp = new HashSet<>();
		List<CardTypes> playercards = player.getPlayerCards();
		temp.addAll(list);
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
	 * this method checks for the number of armies that are not deployed
	 *
	 * @param country source country of the player
	 * @return some value of type string
	 */
	public String addarmies(Country country) {
		int index = country.getOwner().getPlayerId();
		Player player = ReadingFiles.playerId.get(index);
		if (player.getPlayerArmiesNotDeployed() == 0) {
			return "NO ARMIES LEFT, PLEASE CLICK FINISH REINFORCEMENT";
		} else {
			updateValue(player, country);
			return "";
		}
	}

	/**
	 * Gets a list of countries that the player owns
	 *
	 * @param player: Player object must be given to fetch the countries
	 * @return List of countries owned by the player
	 */
	public List<Country> getMyCountries(Player player) {
		List<Country> countries = new ArrayList<Country>();
		for (Map.Entry<String, Country> entry : ReadingFiles.CountryNameObject.entrySet()) {
			if (entry.getValue().getOwner().getPlayerId() == (player.getPlayerId())) {
				countries.add(entry.getValue());
			} else
				continue;
		}
		return countries;
	}

	/**
	 * this method calculates the number of armies each player gets to reinforce
	 *
	 * @param player: player object for which the armies are calculated
	 */

	public void calculateReinforcementArmies(Player player) {
		int totalcountriesofplayer = 0;
		for (Map.Entry<String, Country> entry : ReadingFiles.CountryNameObject.entrySet()) {
			if (entry.getValue().getOwner().getPlayerId() == (player.getPlayerId())) {
				totalcountriesofplayer++;
			} else
				continue;
		}
		float totalarmiestoreinforce;
		totalarmiestoreinforce = (float) totalcountriesofplayer / 3;
		int armies = 0;
		if (totalarmiestoreinforce < 3.0) {
			armies = armies + 3;
		} else {
			armies = armies + (int) totalarmiestoreinforce;
		}
		armies = armies + calcArmiesByControlValue(player);
		player.setPlayerTotalArmiesNotDeployed(player.getPlayerArmiesNotDeployed() + armies);
	}

	/**
	 * this method checks whether the player owns the whole continent or not
	 *
	 * @param player: player object for which it will check
	 * @return continents list of continents
	 */

	public List<Continent> playerOwnsContinent(Player player) {
		List<Continent> continents = new ArrayList<Continent>();
		for (Map.Entry<String, Continent> entry : ReadingFiles.ContinentNameObject.entrySet()) {
			List<Country> temp = entry.getValue().getCountries();
			int counter = 0;
 			for (int i = 0; i < entry.getValue().getCountries().size(); i++) {
				if (entry.getValue().getCountries().get(i).getOwner().getPlayerId() == (player.getPlayerId()))
					counter++;
				else
					continue;
			}
			if (temp.size() == counter)
				continents.add(entry.getValue());
			else
				continue;
		}
		return continents;
	}

	/**
	 * this method calculates the number of armies according to the control value
	 *
	 * @param player: player object for which it calculates
	 * @return armies
	 */
	public int calcArmiesByControlValue(Player player) {
		List<Continent> continents = playerOwnsContinent(player);
		int armies = 0;
		for (int i = 0; i < continents.size(); i++) {
			armies = armies + continents.get(i).getControlValue();
		}
		return armies;
	}

	/**
	 * this method updates the number of armies player owns and armies not deployed
	 *
	 * @param player: player object that updates
	 * @param country: country object where armies updated
	 */
	public void updateValue(Player player, Country country) {
		country.setNoOfArmies(country.getNoOfArmies() + 1);
		int x = ReadingFiles.ContinentNameObject.get(country.getContinent().getName()).getCountries().indexOf(country);
		ReadingFiles.ContinentNameObject.get(country.getContinent().getName()).getCountries().get(x)
				.setNoOfArmies(country.getNoOfArmies());
		player.setPlayerTotalArmiesNotDeployed(player.getPlayerArmiesNotDeployed() - 1);
	}

	/**
	 * this method checks whether player has deployed all his armies or not
	 *
	 * @param player: player object
	 */

	public String endReinforcementCheck(Player player) {
		if (player.getPlayerArmiesNotDeployed() == 0) {
			return "Please deploy all your armies before proceeding to attack";
		} else
			return null;
	}
}
