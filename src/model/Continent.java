package model;
import java.util.*;


//Represents a continent
public class Continent {
	
	public Continent(int control_value,String name) {
		this.continent_id =continent_id;
		this.countries = new ArrayList<>();
		this.name=name;
		this.control_value = control_value;
	}

	
	//Continent ID
	private int continent_id;
	
	//Continent name
	private String name;
	
	//Continent's Control Value
	private int control_value;
	
	//countries that are in this continent
	private List<Country> countries;

	//Gets continent's ID
	public int getcontinent_id() {
		return continent_id;
	}
	
	//Set Country ID
	public void setContinentId(int continent_id) {
		this.continent_id=continent_id;
	}
	
	//Gets Continent name
	public String getName() {
		return name;
	}
		
	//Sets Continent name
	public void setName(String name) {
		this.name=name;
	}
	
	//Gets continent's countries
	public List<Country> getCountries() {
		return countries;
	}
	//Sets continent's countries
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	public void addCountrie(Country Countrie) {
		this.countries.add(Countrie);
	}
	//Gets continent's ID
	public int getControlValue() {
		return control_value;
	}
		
	//Set Country ID
	public void setControlValue(int control_value) {
		this.control_value=control_value;
	}
}
