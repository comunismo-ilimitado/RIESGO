package ControllerTests;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.MapValidation;
import model.Continent;
import model.Country;


/**
 * This class tests the map validations
 * 
 * @author navjot and neerajpreet
 *
 */
public class MapValidationTest {
	MapValidation map;
	Country country1, country2, country3, country4, country5,country6,country7;
	List<Country> n_list, n_list1, n_list2, n_list3;
	Continent continent1, continent2, continent3;
	HashMap<String, Country> countries;

	/**
	 * This method create objects of countries and assign values to methods This
	 * will be called before every test case
	 */
	@Before
	public void onStart() {
		countries = new HashMap<>();
		HashMap<String, Continent> continents = new HashMap<>();

		country1 = new Country("India");
		country2 = new Country("China");
		country3 = new Country("Pakistan");
		country4 = new Country("Bhutan");
		country5 = new Country("Iran");
		continent1 = new Continent(4, "Asia");
		continent2 = new Continent(5, "Africa");
		continent3 = new Continent(6, "North America");

		n_list = new ArrayList<Country>();
		n_list.add(country2);
		n_list.add(country3);
		n_list.add(country4);

		n_list1 = new ArrayList<Country>();
		n_list1.add(country5);

		n_list2 = new ArrayList<Country>();
		n_list2.add(country5);
		n_list2.add(country1);

		n_list3 = new ArrayList<Country>();
		n_list3.add(country1);
		n_list3.add(country2);
		n_list3.add(country3);
		n_list3.add(country4);

		country1.setContinentId(1);
		country1.setCountryId(11);
		country1.setName("India");
		country1.setNeighbors(n_list);

		country2.setContinentId(2);
		country2.setCountryId(21);
		country2.setName("China");
		country2.setNeighbors(n_list1);

		country3.setContinentId(3);
		country3.setCountryId(31);
		country3.setName("Pakistan");
		country3.setNeighbors(n_list2);

		country4.setContinentId(4);
		country4.setCountryId(41);
		country4.setName("Bhutan");
		country4.setNeighbors(n_list3);

		country5.setContinentId(5);
		country5.setCountryId(51);
		country5.setName("Iran");
		country5.setNeighbors(n_list);

		continent1.setName("Asia");
		continent1.setCountries(n_list);
		continent1.setControlValue(4);

		continent2.setName("Africa");
		continent2.setCountries(n_list2);
		continent2.setControlValue(5);

		continent3.setName("North America");
		continent3.setCountries(n_list3);

		countries.put("India", country1);
		countries.put("China", country2);
		countries.put("Pakistan", country3);
		countries.put("Bhutan", country4);
		countries.put("Iran", country5);

		continents.put("Asia", continent1);
		continents.put("Africa", continent1);

		map = new MapValidation(countries, continents);
	}

	/**
	 * This test case will pass if one country is neighbor of another country and
	 * vice versa
	 */
	@Test
	public void testBidirectionalCheck() {
		map.BiDirectionalCheck();
		assertEquals(true, country2.getNeighbors().contains(country1));
	}

	/**
	 * This test case will pass if any of the continent does not contain any country
	 * or null
	 */
	@Test
	public void testNoContinentIsUsed() {
		continent3.setCountries(null);
		map.NoContinentIsUnused();
		assertEquals(true, map.getString().contains("EVERY CONTINENT IS NOT USED"));
	}

	/**
	 * This test case will pass if country itself is not its neighbor
	 */
	@Test
	public void testNotItsOwnNeighbour() {
		map.NotItsOwnNeighbour();
		assertEquals(true, map.getString2().contains("Neighbour Of itself Removed"));
	}

	/**
	 * This test case will pass if one country should not be in more than one
	 * continent
	 */
	@Test
	public void testContinentHaveSameCountry() {
		map.ContinentHaveSameCountry();
		assertEquals(true, map.getString().contains("MULTIPLE CONTINENTS HAVE SAME COUNTRIES"));
	}
	
	/**
	*Tests graph connectivity of the map
	*
	*/
	@Test
	public void testGraphConnectivity()
	{
		map.GraphConnectivity();
		assertEquals(false,map.getString().contains("Error Connectivity Graph"));
	}
	
	/**
	*Tests graph connectivity of the map
	*
	*/
	@Test
	public void testGraphConnectivity1()
	{
		country6 = new Country("Canada");
		country7 = new Country("USA");
		countries.put("Canada",country6);
		countries.put("USA",country7);
		map.GraphConnectivity();
		assertEquals(true,map.getString().contains("Error Connectivity Graph"));
	}
	
	/**
	*Tests continents are connected or not
	*
	*/
	@Test
	public void testContinentConnectivity()
	{
		map.getString().clear();
		map.ContinentConectivity();
		assertEquals(true,map.getString().contains("Error Connectivity Graph"));
	}
	
	/**
	*Tests country does not have any neighbor
	*
	*/
	@Test
	public void testEmptyNeighbours()
	{
		map.EmptyNeighbours();
		assertEquals(false,map.getString().contains("No Neighbours - Unresolver"));
	}

}
