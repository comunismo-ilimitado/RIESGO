package controller;

import java.util.*;
import model.*;

public class AggressiveStratery implements IStrategy {

	public void reinforce(Player player) {
		exchangeCardsStartegy(player);
		player.calcArmiesByControlValue(player);
		List<Country> countries = player.getMyCountries(player);
		Country strongestcountry=null;
		if(player.getMyCountries(player).size()>getStrongestCountry(countries))
		 strongestcountry = player.getMyCountries(player).get(getStrongestCountry(countries));
		strongestcountry.setNoOfArmies(strongestcountry.getNoOfArmies() + player.getPlayerArmiesNotDeployed());
		player.getMyCountries(player).get(getStrongestCountry(countries))
				.setNoOfArmies(strongestcountry.getNoOfArmies());
		ReadingFiles.CountryNameObject.get(strongestcountry.getName()).setNoOfArmies(strongestcountry.getNoOfArmies());
		//ReadingFiles.CountryNameObject.put(strongestcountry.getName(), strongestcountry);
		player.setPlayerTotalArmiesNotDeployed(0);
	}

	public void attack(Player player) {
		AttackController aC = new AttackController();
		List<Country> countries = player.getMyCountries(player);
		Country strongestcountry = player.getMyCountries(player).get(getStrongestCountry(countries));
		countries.remove(strongestcountry);
		List<Country> attackable = aC.getMyNeighborsForAttack(strongestcountry);
		while (strongestcountry.getNoOfArmies() > 1 && attackable.size() > 0) {
			Country defender = attackable.get(getWeakestCountryIndex(attackable));
			aC.attackButton(strongestcountry, defender, 0, 0, true);
//			countries.add(strongestcountry);
//			player.setTotalCountriesOccupied(countries);
//			countries.remove(strongestcountry);
//			ReadingFiles.CountryNameObject.put(strongestcountry.getName(), strongestcountry);
//			ReadingFiles.CountryNameObject.put(defender.getName(), defender);
			AttackController.card = true;
			strongestcountry=ReadingFiles.CountryNameObject.get(strongestcountry.getName());
			attackable = aC.getMyNeighborsForAttack(strongestcountry);
		}
		AttackController.card = false;
	}

	public void fortify(Player player) {
		FortificationController fC = new FortificationController();
		List<Country> countries = player.getMyCountries(player);
		Country strongestcountry = countries.get(getStrongestCountry(countries));
		countries.remove(strongestcountry);
		while (countries.size() > 0) {
			Country fotifyingcountry = countries.get(getStrongestCountry(countries));
			countries.remove(fotifyingcountry);
			if (fotifyingcountry.getNoOfArmies() > 1 && fC.hasPathBFS2(fotifyingcountry, strongestcountry)) {
				strongestcountry.setNoOfArmies(strongestcountry.getNoOfArmies() + fotifyingcountry.getNoOfArmies() - 1);
				fotifyingcountry.setNoOfArmies(1);
				countries.add(strongestcountry);
				countries.add(fotifyingcountry);
				player.setTotalCountriesOccupied(countries);
				countries.remove(strongestcountry);
				countries.remove(fotifyingcountry);
//				ReadingFiles.CountryNameObject.put(strongestcountry.getName(),strongestcountry);
//				ReadingFiles.CountryNameObject.put(fotifyingcountry.getName(),fotifyingcountry);
				ReadingFiles.CountryNameObject.get(fotifyingcountry.getName()).setNoOfArmies(fotifyingcountry.getNoOfArmies());
				ReadingFiles.CountryNameObject.get(strongestcountry.getName()).setNoOfArmies(strongestcountry.getNoOfArmies());
				
				break;
			}
		}
	}

	public int getWeakestCountryIndex(List<Country> countries) {
		Country country = countries.get(0);
		int index = 0;
		for (int i = 1; i < countries.size(); i++) {
			if (country.getNoOfArmies() > countries.get(i).getNoOfArmies()) {
				country = countries.get(i);
				index = i;
			}
		}
		return index;
	}

	public int getStrongestCountry(List<Country> countries) {
		Country strongestcountry = null;
		if(countries.size()>0)
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
