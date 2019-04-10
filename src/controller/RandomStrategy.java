package controller;

import java.util.*;
import model.*;

/**
 * This class implements the Random Strategy
 * 
 * @author Bhargav
 * @version 1.0
 *
 */
public class RandomStrategy implements IStrategy {

	HelperClass helper = new HelperClass();

	/**
	 * Reinforcement phase based on Random Strategy rules
	 * 
	 * @param player: player object
	 * 
	 */
	public void reinforce(Player player) {
		helper.exchangeCardsStartegy(player);
		player.calcArmiesByControlValue(player);
		List<Country> countries = player.getMyCountries(player);
		int index = (int) (Math.random() * countries.size());
		Country country = countries.get(index);
		country.setNoOfArmies(country.getNoOfArmies() + player.getPlayerArmiesNotDeployed());
		player.setPlayerTotalArmiesNotDeployed(0);
		player.getMyCountries(player).get(index).setNoOfArmies(country.getNoOfArmies());
		ReadingFiles.CountryNameObject.get(country.getName()).setNoOfArmies(country.getNoOfArmies());
	}

	/**
	 * Attack phase based on Random Strategy rules
	 * 
	 * @param player: player object
	 * 
	 */
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
				AttackController.card = true;
			}
		}
		AttackController.card = false;
	}

	/**
	 * Fortification phase based on Random Strategy rules
	 * 
	 * @param player: player object
	 * 
	 */
	public void fortify(Player player) {
		FortificationController fC = new FortificationController();
		List<Country> countries = player.getMyCountries(player);
		int index = (int) (Math.random() * countries.size());
		Country country = countries.get(index);
		countries.remove(index);
		while (countries.size() > 0) {
			index = (int) (Math.random() * countries.size());
			Country fortifying = countries.get(index);
			countries.remove(index);
			if (fortifying.getNoOfArmies() > 1 && fC.hasPathBFS2(country, fortifying)) {
				country.setNoOfArmies(country.getNoOfArmies() + fortifying.getNoOfArmies() - 1);
				fortifying.setNoOfArmies(1);
				ReadingFiles.CountryNameObject.get(country.getName()).setNoOfArmies(country.getNoOfArmies());
				ReadingFiles.CountryNameObject.get(fortifying.getName()).setNoOfArmies(fortifying.getNoOfArmies());
				break;
			}
		}
	}
}
