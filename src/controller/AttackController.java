package controller;

import java.util.*;
import model.CardTypes;
import model.Country;
import model.Player;

import java.io.*;

/**
 * AttackController has all the methods needed in attack phase of the game
 * 
 * @author Team 1
 * @version 1.0.0
 */
public class AttackController {
	public List<Integer> attackerDiceRoll;
	public List<Integer> defenderDiceRoll;
	public List<Integer> attackerDiceRollOutput=new ArrayList<>();
	public List<Integer> defenderDiceRollOutput=new ArrayList<>();

	//
	public boolean canAttack(Player player) {
		List<Country> list = getMyCountries(player);
		int counter = 0;
		int breaker = 0;
		for (int i = 0; i < list.size(); i++) {
			Country temp = list.get(i);
			if (temp.getNeighbors().size() == 0) {
				counter++;

			} else if (temp.getNoOfArmies() < 2) {
				counter++;

			} else {
				List<Country> templist = temp.getNeighbors();
				for (int j = 0; j < templist.size(); j++) {
					if (templist.get(j).getOwner().getPlayerId() == temp.getOwner().getPlayerId()) {
						continue;
					} else {
						breaker = -1;
						break;
					}
				}
				counter++;
			}
			if(breaker==-1) {
				break;
			}
		}
		if (breaker == -1) {
			return true;
		}
		if (counter == list.size()) {
			return false;
		}
		return false;
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
			if (entry.getValue().getOwner().getPlayerId()==player.getPlayerId()) {
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
			if (neighbors.get(i).getOwner().getPlayerId()==country.getOwner().getPlayerId()) {
				temp.add(neighbors.get(i));
			}
//			if (neighbors.get(i).getNoOfArmies() < 2)
//				temp.add(neighbors.get(i));
		}
		/*
		 * for(int i=0;i<temp.size();i++) {
		 * neighbors.remove(neighbors.indexOf(temp.get(i))); }
		 * 
		 * 
		 */ neighbors.removeAll(temp);
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
		country.setPlayer(player);
		ReadingFiles.CountryNameObject.get(country.getName()).setPlayer(player);
		return;
	}

//	/**
//	 * Places an army in the country that the player has just won
//	 * 
//	 * @param country: Country object must be passed to update the number of armies
//	 *        to 1
//	 */
//	public void placeArmies(Country country) {
//		country.setNoOfArmies(1);
//	}

//	AttackController attackController = new AttackController();

	public void endReinforcementsPhaseButton(Player player) {
	}

	public void endAttackPhaseButton(Player player) {
	}

	public String attackButton(Country attacker, Country defender, int attackerDice, int defenderDice) {
		if (attacker.getNoOfArmies() >= 2 && defender.getNoOfArmies() >= 1) {
//			int attArmies = attacker.getNoOfArmies();
//			int defArmies = defender.getNoOfArmies();

			// String answer = "";
// 			int attackerDice = setNoOfDice(attacker, 'A');
// 			/*
// 			 * display the number of defender dice
// 			 */ int defenderDice = setNoOfDice(defender, 'D');
			attackerDiceRoll = new ArrayList<Integer>();
			defenderDiceRoll = new ArrayList<Integer>();

			/*
			 * display the int list values as the results from dice roll
			 */for (int i = 0; i < attackerDice; i++) {
				attackerDiceRoll.add(rollDice());
			}
			for (int i = 0; i < defenderDice; i++) {
				defenderDiceRoll.add(rollDice());
			}
			attackerDiceRollOutput.addAll(attackerDiceRoll);
			defenderDiceRollOutput.addAll(defenderDiceRoll);
			System.out.println(attackerDiceRoll.toString()+" "+defenderDiceRoll.toString());
			while (attackerDiceRoll.size() != 0 && defenderDiceRoll.size() != 0) {
				int attackerMax = getMaxValue(attackerDiceRoll);
				int defenderMax = getMaxValue(defenderDiceRoll);
				if (attackerMax <= defenderMax) {
					updateArmies(attacker);
				} else {
					updateArmies(defender);
				}
				


				attackerDiceRoll.remove(attackerDiceRoll.indexOf(attackerMax));
				defenderDiceRoll.remove(defenderDiceRoll.indexOf(defenderMax));
				if (attackerDice == 1)
					break;
				else
					continue;
			}
			if (defender.getNoOfArmies() == 0) {
				List<Country> newListOfCountriesAtt = ReadingFiles.playerId.get(attacker.getOwner().getPlayerId())
						.getTotalCountriesOccupied();
				newListOfCountriesAtt.add(defender);
				ReadingFiles.playerId.get(attacker.getOwner().getPlayerId())
						.setTotalCountriesOccupied(newListOfCountriesAtt);
				attacker.getOwner().setTotalCountriesOccupied(newListOfCountriesAtt);
				List<Country> newListOfCountriesDef = ReadingFiles.playerId.get(defender.getOwner().getPlayerId())
						.getTotalCountriesOccupied();
				newListOfCountriesDef.remove(defender);
				ReadingFiles.playerId.get(defender.getOwner().getPlayerId())
						.setTotalCountriesOccupied(newListOfCountriesDef);
				defender.getOwner().setTotalCountriesOccupied(newListOfCountriesDef);
				updateOwner(defender, attacker.getOwner());
				defender.setNoOfArmies(attackerDice);
				// code for drawing a card randomly
				int cardnumber = (int) (Math.random() * 3 + 1);
				List<CardTypes> newsetofcards = new ArrayList<CardTypes>();
				if (cardnumber == 1) {
					Player attplayer = ReadingFiles.playerId.get(attacker.getOwner().getPlayerId());
					newsetofcards = attplayer.getPlayerCards();
					newsetofcards.add(CardTypes.Artillery);
					attacker.getOwner().setPlayerCards(newsetofcards);
					ReadingFiles.playerId.get(attacker.getOwner().getPlayerId()).setPlayerCards(newsetofcards);

				} else if (cardnumber == 2) {
					Player attplayer = ReadingFiles.playerId.get(attacker.getOwner().getPlayerId());
					newsetofcards = attplayer.getPlayerCards();
					newsetofcards.add(CardTypes.Cavalry);
					attacker.getOwner().setPlayerCards(newsetofcards);
					ReadingFiles.playerId.get(attacker.getOwner().getPlayerId()).setPlayerCards(newsetofcards);
				} else if (cardnumber == 3) {
					Player attplayer = ReadingFiles.playerId.get(attacker.getOwner().getPlayerId());
					newsetofcards = attplayer.getPlayerCards();
					newsetofcards.add(CardTypes.Infantry);
					attacker.getOwner().setPlayerCards(newsetofcards);
					ReadingFiles.playerId.get(attacker.getOwner().getPlayerId()).setPlayerCards(newsetofcards);
				}
				// answer = answer + "You Won and you occupied this country.";
			}
			if (getMyCountries(defender.getOwner()).size() == 0) {
				// add code to give cards to attacker
				Player def = ReadingFiles.playerId.get(defender.getOwner().getPlayerId());
				Player att = ReadingFiles.playerId.get(attacker.getOwner().getPlayerId());
				List<CardTypes> defcards = def.getPlayerCards();
				List<CardTypes> attcards = att.getPlayerCards();
				attcards.addAll(defcards);
				attacker.getOwner().setPlayerCards(attcards);
				ReadingFiles.playerId.get(attacker.getOwner().getPlayerId()).setPlayerCards(attcards);
				ReadingFiles.playerId.remove(defender.getOwner().getPlayerId());
				ReadingFiles.players.remove(ReadingFiles.players.indexOf(defender.getOwner().getPlayerId()));
			}
//			int armiesLostByAttacker = 0;
//			int armiesLostByDefender = 0;
//			if (attacker.getNoOfArmies() < attArmies) {
//				armiesLostByAttacker = attArmies - attacker.getNoOfArmies();
//			}
//			if (defender.getNoOfArmies() < defArmies) {
//				armiesLostByDefender = defArmies - defender.getNoOfArmies();
//			}
//			answer = answer + "Armies lost by attacker:" + armiesLostByAttacker + "Armies lost by defender:"
//					+ armiesLostByDefender;
			return "";
		} else {
			if (attacker.getNoOfArmies() <= 1)
				return "Your country must have more than one army";
			else if (defender.getNoOfArmies() <= 1)
				return "Please a country with more than one army to attack";
			else
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
