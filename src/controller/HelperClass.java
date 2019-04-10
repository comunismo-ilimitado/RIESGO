package controller;

import java.util.List;
import model.CardTypes;
import model.Country;
import model.Player;

/**
 * Helper class to consolidate common required methods in startegies
 * @author bhargav
 */
public class HelperClass {

	/**
	 * get index of the country
	 * 
	 * @param country: country name
	 * @param countries: list of countries
	 * @return index
	 */
	public int getIndex(Country country, List<Country> countries) {
		for (int i = 0; i < countries.size(); i++) {
			if (country.getName().equals(countries.get(i).getName())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * get index of the country with least number of armies
	 * 
	 * @param countries: list of countries
	 * @return index
	 */
	public int getWeakestCountryIndex(List<Country> countries) {
		Country country = null;
		if (countries.size() > 0)
			country = countries.get(0);
		int index = 0;
		for (int i = 1; i < countries.size(); i++) {
			if (country.getNoOfArmies() > countries.get(i).getNoOfArmies()) {
				country = countries.get(i);
				index = i;
			}
		}
		return index;
	}

	/**
	 * Exchange cards based on Aggressive Strategy rules
	 * 
	 * @param player: player object
	 */
	public void exchangeCardsStartegy(Player player) {
		if (player.getPlayerCards().size() >= 3) {
			List<CardTypes> cards = player.getPlayerCards();
			while (cards.size() >= 3) {
				int counter = 0;
				if (cards.contains(CardTypes.Artillery) && cards.contains(CardTypes.Infantry)
						&& cards.contains(CardTypes.Cavalry)) {
					player.setCardExchangeValue(player.getCardExchangeValue() + 1);
					player.setPlayerTotalArmiesNotDeployed(
							player.getPlayerArmiesNotDeployed() + player.getCardExchangeValue() * 5);
					counter = counter + 1;
					cards.remove(CardTypes.Artillery);
					cards.remove(CardTypes.Cavalry);
					cards.remove(CardTypes.Infantry);
				}
				if (cards.contains(CardTypes.Artillery)) {
					cards.remove(CardTypes.Artillery);
					if (cards.contains(CardTypes.Artillery)) {
						cards.remove(CardTypes.Artillery);
						if (cards.contains(CardTypes.Artillery)) {
							cards.remove(CardTypes.Artillery);
							player.setCardExchangeValue(player.getCardExchangeValue() + 1);
							player.setPlayerTotalArmiesNotDeployed(
									player.getPlayerArmiesNotDeployed() + player.getCardExchangeValue() * 5);
							counter = counter + 1;
						} else {
							cards.add(CardTypes.Artillery);
							cards.add(CardTypes.Artillery);
						}
					} else {
						cards.add(CardTypes.Artillery);
					}
				}
				if (cards.contains(CardTypes.Cavalry)) {
					cards.remove(CardTypes.Cavalry);
					if (cards.contains(CardTypes.Cavalry)) {
						cards.remove(CardTypes.Cavalry);
						if (cards.contains(CardTypes.Cavalry)) {
							cards.remove(CardTypes.Cavalry);
							player.setCardExchangeValue(player.getCardExchangeValue() + 1);
							player.setPlayerTotalArmiesNotDeployed(
									player.getPlayerArmiesNotDeployed() + player.getCardExchangeValue() * 5);
							counter = counter + 1;
						} else {
							cards.add(CardTypes.Cavalry);
							cards.add(CardTypes.Cavalry);
						}
					} else {
						cards.add(CardTypes.Cavalry);
					}
				}
				if (cards.contains(CardTypes.Infantry)) {
					cards.remove(CardTypes.Infantry);
					if (cards.contains(CardTypes.Infantry)) {
						cards.remove(CardTypes.Infantry);
						if (cards.contains(CardTypes.Infantry)) {
							cards.remove(CardTypes.Infantry);
							player.setCardExchangeValue(player.getCardExchangeValue() + 1);
							player.setPlayerTotalArmiesNotDeployed(
									player.getPlayerArmiesNotDeployed() + player.getCardExchangeValue() * 5);
							counter = counter + 1;
						} else {
							cards.add(CardTypes.Infantry);
							cards.add(CardTypes.Infantry);
						}
					} else {
						cards.add(CardTypes.Infantry);
					}
				}
				player.setPlayerCards(cards);
				if ((cards.size() == 4 || cards.size() == 3) && counter == 0) {
					break;
				}
			}
		}
	}
}
