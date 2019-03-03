package controller;

import java.util.*;
import model.Country;
import model.Player;

import java.io.*;

//When the player clicks attack phase call getAllMyCountries method and using the list of countries display all his countries
//When the player selects his country that he wants to do the attack with, show its neighbours by 
//calling getMyNeighbors, that are not his own
//If the list is empty show popup asking to choose another country and go to step-1 again
//when the player clicks the country that he wants to attack, call getNoOfDice method for both countries
//Then call rolldice method every time till the no.of armies becomes zero
//No.of armies will be updated for each country after every rolldice method call
//Write a check before calling rolldice(not before getNoOfDice method because after starting 
//attack you cannot change no.of dice) method if the no.of armies is zero or not
//If the player clicks continue attack button go to step-1 and continue
/**
 * AttackController has all the methods needed in attack phase of the game
 * 
 * @author Team 1
 * @version 1.0.0
 */
public class AttackController {
	public List<Integer> attackerDiceRollFinal=new ArrayList<Integer>();
	public List<Integer>defenderDiceRollFinal=new ArrayList<Integer>();
	
	public List<Integer> attackerDiceRoll;
	public List<Integer> defenderDiceRoll;

//	/**
//	 * Returns the winner of each dice roll as a string, either "Attacker" or "Defender"
//	 * @param attackerDice
//	 * @param defenderDice
//	 * @return
//	 */
//	public String attack(int attackerDice, int defenderDice) {
//		int attackerSum=rollDice(attackerDice);
//		int defenderSum=rollDice(defenderDice);
//		if(attackerSum<=defenderSum)
//			return "Defender";
//		else
//			return "Attacker";
//	}
	/**
	 * Gets a list of countries that the player owns
	 * 
	 * @param player: Player object must be given to fetch the countries
	 * @return List of countries owned by the player
	 */
	public List<Country> getMyCountries(Player player) {
		List<Country> countries = new ArrayList<Country>();
		for (Map.Entry<String, Country> entry : ReadingFiles.CountryNameObject.entrySet()) {
			if (entry.getValue().getOwner().equals(player) && entry.getValue().getNoOfArmies() > 1) {
				countries.add(entry.getValue());
			} else
				continue;
		}
		return countries;
	}

	/**
	 * Gets the neighbors of a given country excluding the countries with the same
	 * owner as the given country and any countries with only one army
	 * 
	 * @param country: Country object must be passed to fetch its neighbors
	 * @return Returns List of countries which are neighbors of the given country
	 */
	public List<Country> getMyNeighborsForAttack(Country country) {
		List<Country> neighbors = country.getNeighbors();
		// int total = neighbors.size();
		List<Country> temp = new ArrayList<Country>();
		for (int i = 0; i < neighbors.size(); i++) {
			if (neighbors.get(i).getOwner().equals(country.getOwner())) {
<<<<<<< HEAD
				temp.add(neighbors.get(i));}
		/*	if (neighbors.get(i).getNoOfArmies() < 2)
				temp.add(neighbors.get(i));
		*/}
/*		for(int i=0;i<temp.size();i++) {
			neighbors.remove(neighbors.indexOf(temp.get(i)));
		}
		
		
*/		neighbors.removeAll(temp);
=======
				temp.add(neighbors.get(i));
			}
			if (neighbors.get(i).getNoOfArmies() < 2)
				temp.add(neighbors.get(i));
		}
		/*
		 * for(int i=0;i<temp.size();i++) {
		 * neighbors.remove(neighbors.indexOf(temp.get(i))); }
		 * 
		 * 
		 */ neighbors.removeAll(temp);
>>>>>>> c99fa4c4f6fafe24b0a73a53e67fcf8e78f2b53c
		return neighbors;
	}

	/**
	 * Decrements armies of the given country which lost the dice roll in attack
	 * phase
	 * 
	 * @param country: Country object must be passed so its armies will be updated
	 */
	public void updateArmies(Country country) {
		country.setNoOfArmies(country.getNoOfArmies() - 1);
	}

	/**
	 * Updates the owner of attacked country if the attacker wins
	 * 
	 * @param player: Player object must be passed to get the new owner information
	 *        to be updated in the country
	 * @param country: Country object must be passed to update the owner
	 */
	public void updateOwner(Player player, Country country) {
		country.setPlayer(player);
	}

	/**
	 * Gets number of dice for the attack and defense
	 * 
	 * @param country: Country object must be passed to get number of armies to set
	 *        dice
	 * @param ad: To know its the attacker or defender to set dice based on that
	 * @return Number of Dice to be assigned
	 */
	public int setNoOfDice(Country country, char ad) {
		if (ad == 'A') {
			if (country.getNoOfArmies() == 2)
				return 1;
			else if (country.getNoOfArmies() == 3)
				return 2;
			else
				return 3;
		} else if (ad == 'D') {
			if (country.getNoOfArmies() == 1)
				return 1;
			else
				return 2;
		} else
			return 0;
	}

	/**
	 * To get a random number between 1 and 6 which simulates a dice roll
	 * 
	 * @return Returns an integer which is the dice roll value
	 */
	public int rollDice() {
		int number = (int) (Math.random() * 6 + 1);
		return number;
	}

//	/**
//	 * Check if the country has any armies to attack before rolling the dice
//	 * @param country
//	 * @return
//	 */
//	public boolean checkArmies(Country country){
//		if(country.getNoOfArmies()==0)
//			return true;
//		return false;
//	}
//	/**
//	 * Gets player based on Owner Name from Country
//	 * @param PlayerName
//	 * @return
//	 */
//	public Player getPlayerByName(String PlayerName){
//		return ReadingFiles.playerObject.get(PlayerName);
//	}
	/**
	 * Updates the owner of a country if the attacker destroys all armies of
	 * opponent
	 * 
	 * @param country: Country object to update owner information
	 * @param player: Player object to update the owner in the given country
	 */
	public void updateOwner(Country country, Player player) {
		ReadingFiles.CountryNameObject.get(country.getName()).setPlayer(player);
		return;
	}

	/**
	 * Places an army in the country that the player has just won
	 * 
	 * @param country: Country object must be passed to update the number of armies
	 *        to 1
	 */
	public void placeArmies(Country country) {
		country.setNoOfArmies(1);
	}

	AttackController attackController = new AttackController();

	public void endReinforcementsPhaseButton(Player player) {
	}

	public void endAttackPhaseButton(Player player) {
	}

	public String attackButton(Country attacker, Country defender) {
<<<<<<< HEAD
		
		if(attacker.getNoOfArmies()>=2 && defender.getNoOfArmies()>=1)
		{
			int attArmies=attacker.getNoOfArmies();
			int defArmies=defender.getNoOfArmies();
			
			String answer = "";
			int attackerDice=setNoOfDice(attacker, "A");
/*			display the number of defender dice
*/			int defenderDice=setNoOfDice(defender, "D");
			 List<Integer> attackerDiceRoll = new ArrayList<Integer>();
			 List<Integer> defenderDiceRoll = new ArrayList<Integer>();
			/*display the int list values as the results from dice roll
			*/for(int i=0;i<attackerDice;i++) {
				attackerDiceRoll.add(rollDice());
=======
		if (attacker.getNoOfArmies() >= 2 && defender.getNoOfArmies() >= 2) {
			int attArmies = attacker.getNoOfArmies();
			int defArmies = defender.getNoOfArmies();

			String answer = "";
			int attackerDice = attackController.setNoOfDice(attacker, 'A');
			/*
			 * display the number of defender dice
			 */ int defenderDice = attackController.setNoOfDice(defender, 'D');
			attackerDiceRoll = new ArrayList<Integer>();
			defenderDiceRoll = new ArrayList<Integer>();
			/*
			 * display the int list values as the results from dice roll
			 */for (int i = 0; i < attackerDice; i++) {
				attackerDiceRoll.add(attackController.rollDice());
>>>>>>> c99fa4c4f6fafe24b0a73a53e67fcf8e78f2b53c
			}
			for (int i = 0; i < defenderDice; i++) {
				defenderDiceRoll.add(attackController.rollDice());
			}
<<<<<<< HEAD
			attackerDiceRollFinal.clear();
			defenderDiceRollFinal.clear();
			attackerDiceRollFinal.addAll(attackerDiceRoll);
			defenderDiceRollFinal.addAll(defenderDiceRoll);
			while(attackerDiceRoll.size()!=0&& defenderDiceRoll.size()!=0) {
				int attackerMax=getMaxValue(attackerDiceRoll);
				int defenderMax=getMaxValue(defenderDiceRoll);
				if(attackerMax<=defenderMax) {
					updateArmies(attacker);
				}
				else {
					updateArmies(defender);
=======
			while (attackerDiceRoll.size() != 0 && defenderDiceRoll.size() != 0) {
				int attackerMax = getMaxValue(attackerDiceRoll);
				int defenderMax = getMaxValue(defenderDiceRoll);
				if (attackerMax <= defenderMax) {
					attackController.updateArmies(attacker);
				} else {
					attackController.updateArmies(defender);
>>>>>>> c99fa4c4f6fafe24b0a73a53e67fcf8e78f2b53c
				}
				attackerDiceRoll.remove(attackerDiceRoll.indexOf(attackerMax));
				defenderDiceRoll.remove(defenderDiceRoll.indexOf(defenderMax));
				if (attackerDice == 1)
					break;
				else
					continue;
			}
			if (defender.getNoOfArmies() == 0) {
				List<Country> newListOfCountriesAtt = attacker.getOwner().getTotalCountriesOccupied();
				newListOfCountriesAtt.add(defender);
				attacker.getOwner().setTotalCountriesOccupied(newListOfCountriesAtt);
				List<Country> newListOfCountriesDef = defender.getOwner().getTotalCountriesOccupied();
				newListOfCountriesDef.remove(defender);
				defender.getOwner().setTotalCountriesOccupied(newListOfCountriesDef);
<<<<<<< HEAD
				updateOwner(defender, attacker.getOwner());
				defender.setNoOfArmies(attackerDice);
				attacker.setNoOfArmies(attacker.getNoOfArmies()-attackerDice);
				//answer = answer+ "You Won and you occupied this country.";
				answer = answer+ "You Won and you occupied this country.";
=======
				attackController.updateOwner(defender, attacker.getOwner());
				defender.setNoOfArmies(1);
				answer = answer + "You Won and you occupied this country.";
>>>>>>> c99fa4c4f6fafe24b0a73a53e67fcf8e78f2b53c
			}
			if (attackController.getMyCountries(defender.getOwner()).size() == 0) {
			}
			int armiesLostByAttacker = 0;
			int armiesLostByDefender = 0;
			if (attacker.getNoOfArmies() < attArmies) {
				armiesLostByAttacker = attArmies - attacker.getNoOfArmies();
			}
			if (defender.getNoOfArmies() < defArmies) {
				armiesLostByDefender = defArmies - defender.getNoOfArmies();
			}
			answer = answer + "Armies lost by attacker:" + armiesLostByAttacker + "Armies lost by defender:"
					+ armiesLostByDefender;
			return "";
<<<<<<< HEAD
		}
		else
		{
			if(attacker.getNoOfArmies()<=1)
			return "Your country must have more than one army";
		/*	else if(defender.getNoOfArmies()<=1)
=======
		} else {
			if (attacker.getNoOfArmies() <= 1)
				return "Your country must have more than one army";
			else if (defender.getNoOfArmies() <= 1)
>>>>>>> c99fa4c4f6fafe24b0a73a53e67fcf8e78f2b53c
				return "Please a country with more than one army to attack";
		*/	else
				return "Wrong input";
		}
	}

	public int getMaxValue(List<Integer> list) {
		int max = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) > max)
				max = list.get(i);
			else
				continue;
		}
		return max;
	}

}
