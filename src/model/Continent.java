package model;

import java.util.*;

/**
 * Continent model class that represents continent
 * 
 * @author bhargav
 * @version 1.0
 */
public class Continent {

	/**
	 * Constructor to instantiate continent class
	 * 
	 * @param control_value
	 * @param name
	 */
	public Continent(int control_value, String name) {
		this.continent_id = continent_id;
		this.countries = new ArrayList<>();
		this.name = name;
		this.control_value = control_value;
	}

	/**
	 * Represents Continent ID
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
	 * @return continent ID
	 */
	public int getcontinent_id() {
		return continent_id;
	}

	/**
	 * Set Country ID
	 * 
	 * @param continent_id
	 */
	public void setContinentId(int continent_id) {
		this.continent_id = continent_id;
	}

	/**
	 * Gets Continent name
	 * 
	 * @return continent name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets Continent name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets continent's countries
	 * 
	 * @return list of countries
	 */
	public List<Country> getCountries() {
		return countries;
	}

	/**
	 * Sets continent's countries
	 * 
	 * @param countries
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	/**
	 * adds countries to the continent
	 * 
	 * @param Countrie: country object
	 */
	public void addCountrie(Country Countrie) {
		this.countries.add(Countrie);
	}

	/**
	 * Gets continent's ID
	 * 
	 * @return control value of the continent
	 */
	public int getControlValue() {
		return control_value;
	}

	/**
	 * Set Country ID
	 * 
	 * @param control_value
	 */
	public void setControlValue(int control_value) {
		this.control_value = control_value;
	}
}
