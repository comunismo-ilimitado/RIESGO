package model;
import java.awt.Color;
import java.util.*;

//Represents a player in the game
public class Player {
	
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
