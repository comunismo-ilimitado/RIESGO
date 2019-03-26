package model;

import java.util.*;

/**
 * This class contains all the information about any particular country
 * 
 * @author Team 1
 * @version 1.0.0
 */
public class Country {

	/**
	 * country_id This contains unique id of the country of type integer
	 */
	private int country_id;

	/**
	 * continent which it belongs to
	 */
	private Continent continent;

	/**
	 * continent_id This contains unique id of the continent of type integer
	 */
	private int continent_id;

	/**
	 * owned_player This contains name of the player who owns this country of type
	 * Player
	 */
	private Player owned_player;

	/**
	 * name This is the name of the country of type String
	 */
	private String name;

	/**
	 * neighbors This is the list of the neighboring countries of this country
	 */
	private List<Country> neighbors;

	/**
	 * no_of_armies This contains the number of armies this country has
	 */
	private int no_of_armies;

	/**
	 * Get the id of the country
	 * 
	 * @return country_id Id of the country of type Integer
	 */
	public int getCountryId() {
		return country_id;
	}

	/**
	 * Set the id of the country
	 * 
	 * @param country_id Id of the country of type Integer
	 */
	public void setCountryId(int country_id) {
		this.country_id = country_id;
	}

	/**
	 * Gets continent ID
	 * 
	 * @return continent object
	 */
	public Continent getContinent() {
		return continent;
	}

	/**
	 * Set continent ID
	 * 
	 * @param continent : object
	 */
	public void setContinent(Continent continent) {
		this.continent = continent;
	}

	/**
	 * Get the id of the continent
	 * 
	 * @return continent_id Id of the continent of type Integer
	 */
	public int getContinentId() {
		return continent_id;
	}

	/**
	 * Set the id of the continent
	 * 
	 * @param continent_id Id of the continent of type Integer
	 */
	public void setContinentId(int continent_id) {
		this.continent_id = continent_id;
	}

	/**
	 * Get the owner of the country
	 * 
	 * @return owned_player It returns the object of the Player type
	 */
	public Player getOwner() {
		return owned_player;
	}

	/**
	 * Set the owner of the country
	 * 
	 * @param player It is the object of the Player type
	 */
	public void setPlayer(Player player) {
		this.owned_player = player;
	}

	/**
	 * Get the name of the country
	 * 
	 * @return name It is the name of the country of type String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the country
	 * 
	 * @param name It is the name of the country of type String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the list of the neighbor countries
	 * 
	 * @return neighbors It returns the list of the neighbor countries
	 */
	public List<Country> getNeighbors() {
		return neighbors;
	}

	/**
	 * Set the neighbors list of this country
	 * 
	 * @param neighbors It takes the list of the neighbor countries
	 */
	public void setNeighbors(List<Country> neighbors) {
		this.neighbors = neighbors;
	}

	/**
	 * This method add the neighbor countries to the list of neighbors
	 * 
	 * @param neighbor It takes the object of type Country
	 */
	public void addNeighbors(Country neighbor) {
		this.neighbors.add(neighbor);
	}

	/**
	 * This method is used to get the number of armies in this country
	 * 
	 * @return no_of_armies It is of type Integer
	 */
	public int getNoOfArmies() {
		return no_of_armies;
	}

	/**
	 * This method is used to set the number of armies in this country
	 * 
	 * @param no_of_armies It is of type Integer
	 */
	public void setNoOfArmies(int no_of_armies) {
		this.no_of_armies = no_of_armies;
	}

	/**
	 * This constructor initializes the data fields
	 * 
	 * @param name It takes the name of the country of type String
	 */
	public Country(String name) {
		this.no_of_armies = 0;
		this.neighbors = new ArrayList<>();
		this.name = name;
		this.owned_player = new Player(0);
	}

}
