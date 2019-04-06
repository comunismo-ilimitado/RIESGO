package controller;

import java.util.*;
import model.*;

public class RandomStrategy implements IStrategy {
	public void reinforce(Player player) {
		exchangeCardsStartegy(player);
		player.calcArmiesByControlValue(player);
		List<Country> countries = player.getMyCountries(player);
		int index = (int) (Math.random() * countries.size());
		Country country = countries.get(index);
		country.setNoOfArmies(country.getNoOfArmies() + player.getPlayerArmiesNotDeployed());
		player.setPlayerTotalArmiesNotDeployed(0);
		player.getMyCountries(player).get(index).setNoOfArmies(country.getNoOfArmies());
		ReadingFiles.CountryNameObject.put(country.getName(), country);
	}

	public void attack(Player player) {
		AttackController aC = new AttackController();
		List<Country> countries = player.getMyCountries(player);
		int index = (int) (Math.random() * countries.size());
		Country attacker = countries.get(index);
		List<Country> attackable = aC.getMyNeighborsForAttack(attacker);
		if (attackable.size() > 0) {
			int noofattacks = (int) (Math.random() * 10 + 1);
			index = (int) (Math.random() * attackable.size());
			Country defender = attackable.get(index);
			while (noofattacks > 0 && attacker.getNoOfArmies() > 1
					&& defender.getOwner().getPlayerId() != attacker.getOwner().getPlayerId()) {
				int attackerdiceroll = aC.setNoOfDice(attacker, 'A');
				int defenderdiceroll = aC.setNoOfDice(defender, 'D');
				attackerdiceroll = (int) (Math.random() * attackerdiceroll + 1);
				defenderdiceroll = (int) (Math.random() * defenderdiceroll + 1);
				aC.attackButton(attacker, defender, attackerdiceroll, defenderdiceroll, false);
				noofattacks--;
				ReadingFiles.CountryNameObject.put(attacker.getName(),attacker);
				ReadingFiles.CountryNameObject.put(defender.getName(),defender);
				AttackController.card = true;
			}
		}
		AttackController.card = false;
	}
	
	public void fortify(Player player) {
		FortificationController fC = new FortificationController();
		List<Country> countries = player.getMyCountries(player);
		int index = (int) (Math.random() * countries.size());
		Country country = countries.get(index);
		countries.remove(index);
		while(countries.size()>0) {
			index = (int) (Math.random() * countries.size());
			Country fortifying = countries.get(index);
			countries.remove(index);
			if(fortifying.getNoOfArmies()>1 && fC.hasPathBFS2(country, fortifying)) {
				country.setNoOfArmies(country.getNoOfArmies()+fortifying.getNoOfArmies()-1);
				fortifying.setNoOfArmies(1);
				ReadingFiles.CountryNameObject.put(country.getName(),country);
				ReadingFiles.CountryNameObject.put(fortifying.getName(),fortifying);
				break;
			}
		}
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
