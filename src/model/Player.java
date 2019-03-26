package model;
import java.awt.Color;
import controller.*;
import java.util.*;

//Represents a player in the game
public class Player {

		ReinforcementController reinforcementcontroller = new  ReinforcementController();
		FortificationController fortificationcontroller = new FortificationController();
		AttackController attackcontroller = new AttackController();

		public int getTotalCountries(Player player) {
			return attackcontroller.getTotalCountries(player);
		}
		public List<Country> getMyCountries(Player player){
			return reinforcementcontroller.getMyCountries(player);
		}

		public boolean hasMoreCards(Player player) {
			return reinforcementcontroller.hasMoreCards(player);
		}

		public String exchangeCards(List<CardTypes> list, Player player) {
			return reinforcementcontroller.exchangeCards(list, player);
		}

		public String addarmies(Country country) {
			return reinforcementcontroller.addarmies(country);
		}

		public void calculateReinforcementArmies(Player player) {
			reinforcementcontroller.calculateReinforcementArmies(player);
		}

		public List<Continent> playerOwnsContinent(Player player) {
			return reinforcementcontroller.playerOwnsContinent(player);
		}

		public int calcArmiesByControlValue(Player player) {
			return reinforcementcontroller.calcArmiesByControlValue(player);
		}

		public void updateValue(Player player, Country country) {
			reinforcementcontroller.updateValue(player, country);
		}

		public String endReinforcementCheck(Player player) {
			return reinforcementcontroller.endReinforcementCheck(player);
		}


		public boolean hasPathBFS2(Country source, Country destination) {
			return fortificationcontroller.hasPathBFS2(source, destination);
		}

		public String moveArmies(Country sourcecountry, Country destinationcountry, int noofarmiestobemoved) {
			return fortificationcontroller.moveArmies(sourcecountry, destinationcountry, noofarmiestobemoved);
		}

		public boolean canAttack(Player player) {
			return attackcontroller.canAttack(player);
		}

		public List<Country> getMyNeighborsForAttack(Country country) {
			return attackcontroller.getMyNeighborsForAttack(country);
		}

		public void updateArmies(Country country) {
			attackcontroller.updateArmies(country);
		}

		public int setNoOfDice(Country country, char ad) {
			return attackcontroller.setNoOfDice(country, ad);
		}

		public int rollDice() {
			return attackcontroller.rollDice();
		}

		public void updateOwner(Country country, Player player) {
			attackcontroller.updateOwner(country, player);
		}

		public String attackButton(Country attacker, Country defender, int attackerDice, int defenderDice, boolean allOut) {
			return attackcontroller.attackButton(attacker, defender, attackerDice, defenderDice, allOut);
		}

		public int getMaxValue(List<Integer> list) {
			return attackcontroller.getMaxValue(list);
		}
		//Player name
		private String name;

		//Player ID
		private int player_id;

		//Total number of armies a player has
		private int total_armies;

		//Armies that are not assigned to any country
		private int total_armies_not_deployed;

		//Total number of armies a player owns
		private List<Country> total_countries_occupied;


		private List<Country> countries_occupied;

		//Number of Continents occupied
		private List<Continent> continents_occupied;

		//The cards that a player holds
		private List<CardTypes> cards;

		//Color to represent player owned countries
		private Color my_color;

		private int card_exchange_counter;

//		//Player cards
//		private List<String> cards = new ArrayList<String>();

		//Default Constructor
		public Player(int player_id) {
			this.player_id = player_id;
			this.total_countries_occupied = new ArrayList<>();
			this.countries_occupied = new ArrayList<>();
			this.cards = new ArrayList<>();
			card_exchange_counter=0;
		}

		//Gets player_id
		public int getPlayerId() {
			return player_id;
		}

		//Sets player_id
		public void setPlayerId(int player_id) {
			this.player_id = player_id;
		}


		//Gets Player name
		public String getPlayerName() {
			return name;
		}

		//Sets Player name
		public void setPlayerName(String name) {
			this.name = name;
		}

		//Gets Player Armies
		public int getPlayerArmies() {
			return total_armies;
		}

		//Sets Player Armies
		public void setPlayerArmies(int total_armies) {
			this.total_armies = total_armies;
		}

		//Gets Player Armies that are not deployed
		public int getPlayerArmiesNotDeployed() {
			return total_armies_not_deployed;
		}

		//Sets Player Armies that are not deployed
		public void setPlayerTotalArmiesNotDeployed(int total_armies_not_deployed) {
			this.total_armies_not_deployed = total_armies_not_deployed;
		}

		//Gets Countries Occupied
		public List<Country> getTotalCountriesOccupied() {
			return total_countries_occupied;
		}

		//Sets Countries Occupied
		public void setTotalCountriesOccupied(List<Country> total_countries_occupied) {
			this.total_countries_occupied = total_countries_occupied;
		}
		public void addCountriesOccupied(Country countriesOccupied) {
			this.total_countries_occupied.add(countriesOccupied);
		}


		//Gets Continents Occupied
		public List<Continent> getContinentsOccupied() {
			return continents_occupied;
		}
		//Sets Continents Occupied
		public void setContinentsOccupied(List<Continent> continents_occupied) {
			this.continents_occupied = continents_occupied;
		}
		//Gets Player cards
		public List<CardTypes> getPlayerCards() {
			return cards;
		}

		//Sets Player cards
		public void setPlayerCards(List<CardTypes> Playercards) {
			this.cards = Playercards;
		}

		//Gets player color
		public Color getPlayerColor() {
			return my_color;
		}

		//Sets player color
		public void setPlayerColor(Color color) {
			this.my_color = color;
		}

		//
		public int getCardExchangeValue() {
			return card_exchange_counter;
		}
		//
		public void setCardExchangeValue(int value) {
			this.card_exchange_counter=value;
		}
}
