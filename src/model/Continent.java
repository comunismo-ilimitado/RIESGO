package model;

import java.util.*;

/**
 * Represents a continent
 * 
 * @author bhargav
 * @version 1.0
 */
public class Continent {

	/**
	 * this is the default constructor of continent
	 * 
	 * @param control_value: unique value given to each continent
	 * @param name: name of the continent
	 */
	public Continent(int control_value, String name) {
		this.continent_id = continent_id;
		this.countries = new ArrayList<>();
		this.name = name;
		this.control_value = control_value;
	}

	/**
	 * Continent ID
	 */
	private int continent_id;

	/**
	 * Continent name
	 */
	private String name;

	/**
	 * Continent's Control Value
	 */
	private int control_value;

	/**
	 * countries that are in this continent
	 */
	private List<Country> countries;

	/**
	 * Gets continent's ID
	 * 
	 * @return continent_id id of the continent
	 */
	public int getcontinent_id() {
		return continent_id;
	}

	/**
	 * Set Country ID
	 * 
	 * @param continent_id id of the continent
	 */
	public void setContinentId(int continent_id) {
		this.continent_id = continent_id;
	}

	/**
	 * Gets Continent name
	 * 
	 * @return name of the continent
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets Continent name
	 * 
	 * @param name of the continent
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets continent's countries
	 * 
	 * @return countries list of countries in the continent
	 */
	public List<Country> getCountries() {
		return countries;
	}

	/**
	 * Sets continent's countries
	 * 
	 * @param countries list of countries in the continent
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public void addCountrie(Country Countrie) {
		this.countries.add(Countrie);
	}

	/**
	 * Gets continent's control value
	 * 
	 * @return control_value unique value given to each continent
	 */
	public int getControlValue() {
		return control_value;
	}

	/**
	 * Set Country's control value
	 * 
	 * @param control_value unique value given to each continent
	 */
	public void setControlValue(int control_value) {
		this.control_value = control_value;
	}
}
