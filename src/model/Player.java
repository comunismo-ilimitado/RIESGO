package model;

import java.awt.Color;
import controller.*;
import java.util.*;

/**
 * Represents a player in the game
 * 
 * @author bhargav
 * @version 1.1
 *
 */
public class Player {

	ReinforcementController reinforcementcontroller = new ReinforcementController();
	FortificationController fortificationcontroller = new FortificationController();
	AttackController attackcontroller = new AttackController();
	public AggressiveStratery aggressiveStratergy=new AggressiveStratery();
	public BenevolentStrategy benevolentStrategy=new BenevolentStrategy();
	public CheaterStrategy cheaterStrategy=new CheaterStrategy();
	public RandomStrategy randomStrategy=new RandomStrategy();

	/**
	 * Gets list of total number of countries
	 * 
	 * @param player: Player object must be given to fetch the countries
	 */
	public int getTotalCountries(Player player) {
		return attackcontroller.getTotalCountries(player);
	}

	/**
	 * Gets a list of countries that the player owns
	 *
	 * @param player: Player object must be given to fetch the countries
	 * @return List of countries owned by the player
	 */
	public List<Country> getMyCountries(Player player) {
		return reinforcementcontroller.getMyCountries(player);
	}

	/**
	 * Checks if player has more than 5 cards
	 * 
	 * @param player: Player object must be given to fetch number of cards
	 * @return true if player has more than 5 cards else false
	 */
	public boolean hasMoreCards(Player player) {
		return reinforcementcontroller.hasMoreCards(player);
	}

	/**
	 * number of times player exchanges the cards
	 * 
	 * @param list: list of cards
	 * @param player: player object must be given to fetch the player cards
	 * @return message how player can exchange cards
	 */
	public String exchangeCards(List<CardTypes> list, Player player) {
		return reinforcementcontroller.exchangeCards(list, player);
	}

	public void ClearArmies() {
		total_countries_occupied.clear();
		countries_occupied.clear();
	}
	/**
	 * this method checks for the number of armies that are not deployed
	 *
	 * @param country source country of the player
	 * @return some value of type string
	 */
	public String addarmies(Country country) {
		return reinforcementcontroller.addarmies(country);
	}

	/**
	 * this method calculates the number of armies each player gets to reinforce
	 *
	 * @param player: player object for which the armies are calculated
	 */
	public void calculateReinforcementArmies(Player player) {
		reinforcementcontroller.calculateReinforcementArmies(player);
	}

	/**
	 * this method checks whether the player owns the whole continent or not
	 *
	 * @param player: player object for which it will check
	 * @return continents list of continents
	 */

	public List<Continent> playerOwnsContinent(Player player) {
		return reinforcementcontroller.playerOwnsContinent(player);
	}

	/**
	 * this method calculates the number of armies according to the control value
	 *
	 * @param player: player object for which it calculates
	 * @return armies
	 */
	public int calcArmiesByControlValue(Player player) {
		return reinforcementcontroller.calcArmiesByControlValue(player);

	}

	/**
	 * this method updates the number of armies player owns and armies not deployed
	 *
	 * @param player: player object that updates
	 * @param country: country object where armies updated
	 */
	public void updateValue(Player player, Country country) {
		reinforcementcontroller.updateValue(player, country);
	}

	/**
	 * this method checks whether player has deployed all his armies or not
	 *
	 * @param player: player object
	 */
	public String endReinforcementCheck(Player player) {
		return reinforcementcontroller.endReinforcementCheck(player);
	}

	/**
	 * Method to find if there a path between two countries or not
	 * 
	 * @param source: The country which armies are being moved from
	 * @param destination: The country which armies are being moved to
	 * @return Returns true if there is a path to move the armies between countries
	 */
	public boolean hasPathBFS2(Country source, Country destination) {
		return fortificationcontroller.hasPathBFS2(source, destination);
	}

	/**
	 * Method to validate and move armies between two countries owned by the same
	 * player
	 * 
	 * @param sourcecountry: The country which armies are being moved from
	 * @param destinationcountry: The country which armies are being moved to
	 * @param noofarmiestobemoved: Armies asked by player to move
	 * @return: Returns a string if there is any error or a null string if
	 *          validations succeed
	 */
	public String moveArmies(Country sourcecountry, Country destinationcountry, int noofarmiestobemoved) {
		return fortificationcontroller.moveArmies(sourcecountry, destinationcountry, noofarmiestobemoved);
	}

	/**
	 * checks player can attack or not
	 * 
	 * @param player: player object
	 * @return true if player can attack else false
	 */
	public boolean canAttack(Player player) {
		return attackcontroller.canAttack(player);
	}

	/**
	 * Gets the neighbors of a given country excluding the countries with the same
	 * owner as the given country and any countries with only one army
	 *
	 * @param country: Country object must be passed to fetch its neighbors
	 * @return Returns List of countries which are neighbors of the given country
	 */
	public List<Country> getMyNeighborsForAttack(Country country) {
		return attackcontroller.getMyNeighborsForAttack(country);
	}

	/**
	 * Decrements armies of the given country which lost the dice roll in attack
	 * phase
	 *
	 * @param country: Country object must be passed so its armies will be updated
	 */
	public void updateArmies(Country country) {
		attackcontroller.updateArmies(country);
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
		return attackcontroller.setNoOfDice(country, ad);
	}

	/**
	 * To get a random number between 1 and 6 which simulates a dice roll
	 *
	 * @return Returns an integer which is the dice roll value
	 */
	public int rollDice() {
		return attackcontroller.rollDice();
	}

	/**
	 * Updates the owner of a country if the attacker destroys all armies of
	 * opponent
	 *
	 * @param country: Country object to update owner information
	 * @param player: Player object to update the owner in the given country
	 */
	public void updateOwner(Country country, Player player) {
		attackcontroller.updateOwner(country, player);
	}

	/**
	 * Attack simulator
	 * 
	 * @param attacker: player who attacks
	 * @param defender: player who's country being attacked
	 * @param attackerDice: dice of attacker
	 * @param defenderDice: dice of defender
	 * @param allOut:if it's all out attack or not
	 * @return string which player has won
	 */
	public String attackButton(Country attacker, Country defender, int attackerDice, int defenderDice, boolean allOut) {
		return attackcontroller.attackButton(attacker, defender, attackerDice, defenderDice, allOut);
	}

	/**
	 * Gets maximum value of the dice
	 * 
	 * @param list: list of dice roll numbers
	 * @return maximum value from list
	 */
	public int getMaxValue(List<Integer> list) {
		return attackcontroller.getMaxValue(list);
	}

	public void setStratergy(String strategy) {
		this.strategy = strategy;
	}
	public String getStatergy() {
		return strategy;
	} 

	private String strategy = "Human"; 

	/**
	 * Player name
	 */
	private String name;

	/**
	 * Player ID
	 */
	private int player_id;

	/**
	 * Total number of armies a player has
	 */
	private int total_armies;

	/**
	 * Armies that are not assigned to any country
	 */
	private int total_armies_not_deployed;

	/**
	 * Total number of countries a player owns
	 */
	private List<Country> total_countries_occupied;

	private List<Country> countries_occupied;

	/**
	 * Number of Continents occupied
	 */
	private List<Continent> continents_occupied;

	/**
	 * The cards that a player holds
	 */
	private List<CardTypes> cards;

	/**
	 * Color to represent player owned countries
	 */
	private Color my_color;
	/**
	 * how many times player exchange the cards
	 */
	private int card_exchange_counter;

	/**
	 * Default Constructor
	 */
	public Player(int player_id) {
		this.player_id = player_id;
		this.total_countries_occupied = new ArrayList<>();
		this.countries_occupied = new ArrayList<>();
		this.cards = new ArrayList<>();
		card_exchange_counter = 0;
	}

	/**
	 * Gets player_id
	 */
	public int getPlayerId() {
		return player_id;
	}

	/**
	 * Sets player_id
	 */
	public void setPlayerId(int player_id) {
		this.player_id = player_id;
	}

	/**
	 * Gets Player name
	 */
	public String getPlayerName() {
		return name;
	}

	/**
	 * Sets Player name
	 */
	public void setPlayerName(String name) {
		this.name = name;
	}

	/**
	 * Gets Player Armies
	 */
	public int getPlayerArmies() {
		return total_armies;
	}

	/**
	 * Sets Player Armies
	 */
	public void setPlayerArmies(int total_armies) {
		this.total_armies = total_armies;
	}

	/**
	 * Gets Player Armies that are not deployed
	 */
	public int getPlayerArmiesNotDeployed() {
		return total_armies_not_deployed;
	}

	/**
	 * Sets Player Armies that are not deployed
	 */
	public void setPlayerTotalArmiesNotDeployed(int total_armies_not_deployed) {
		this.total_armies_not_deployed = total_armies_not_deployed;
	}

	/**
	 * Gets Countries Occupied
	 */
	public List<Country> getTotalCountriesOccupied() {
		return getMyCountries(this);
	}

	/**
	 * Sets Countries Occupied
	 */
	public void setTotalCountriesOccupied(List<Country> total_countries_occupied) {
		this.total_countries_occupied = total_countries_occupied;
	}

	public void addCountriesOccupied(Country countriesOccupied) {
		this.total_countries_occupied.add(countriesOccupied);
	}

	/**
	 * Gets Continents Occupied
	 */
	public List<Continent> getContinentsOccupied() {
		return continents_occupied;
	}

	/**
	 * Sets Continents Occupied
	 */
	public void setContinentsOccupied(List<Continent> continents_occupied) {
		this.continents_occupied = continents_occupied;
	}

	/**
	 * Gets Player cards
	 */
	public List<CardTypes> getPlayerCards() {
		return cards;
	}

	/**
	 * Sets Player cards
	 */
	public void setPlayerCards(List<CardTypes> Playercards) {
		this.cards = Playercards;
	}

	/**
	 * Gets player color
	 */
	public Color getPlayerColor() {
		return my_color;
	}

	/**
	 * Sets player color
	 */
	public void setPlayerColor(Color color) {
		this.my_color = color;
	}

	/**
	 * Gets count how many times player exchange the cards
	 * 
	 * @return integer value
	 */
	public int getCardExchangeValue() {
		return card_exchange_counter;
	}

	/**
	 * Sets count how many times player exchange the cards
	 * 
	 * @param value
	 */
	public void setCardExchangeValue(int value) {
		this.card_exchange_counter = value;
	}
}
