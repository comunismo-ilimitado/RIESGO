package ControllerTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.FortificationController;
import controller.ReadingFiles;
import model.Continent;
import model.Country;
import model.Player;

/**
 * Class to test Fortification Controller
 * 
 * @author Navjot kaur
 *
 */
public class FortificationTest {
	FortificationController fortification;
	Player player1, player2, player3;
	Country country1, country2, country3, country4, country5, country6, country7;
	Continent continent1, continent2;

	/**
	 * Method to create all objects
	 */
	@Before
	public void onStart() {
		fortification = new FortificationController();
		player1 = new Player(2);
		country1 = new Country("India");
		country2 = new Country("China");
		country3 = new Country("Pakistan");
		country4 = new Country("Bhutan");
		country5 = new Country("Iran");
		country6 = new Country("Canada");
		country7 = new Country("Egypt");
		continent1 = new Continent(4, "Asia");
		continent2 = new Continent(5, "Africa");

		country1.setContinentId(1);
		country1.setCountryId(11);
		country1.setName("India");

		country2.setContinentId(2);
		country2.setCountryId(21);
		country2.setName("China");

		country3.setContinentId(3);
		country3.setCountryId(31);
		country3.setName("Pakistan");

		country4.setContinentId(4);
		country4.setCountryId(41);
		country4.setName("Bhutan");

		country5.setContinentId(5);
		country5.setCountryId(51);
		country5.setName("Iran");

		country6.setContinentId(6);
		country6.setCountryId(61);
		country6.setName("Canada");

		country7.setContinentId(7);
		country7.setCountryId(71);
		country7.setName("Egypt");

		List<Country> n_list = new ArrayList<Country>();
		n_list.add(country2);
		n_list.add(country5);
		n_list.add(country6);
		n_list.add(country3);
		

		List<Country> n_list4 = new ArrayList<Country>();
		n_list4.add(country2);
		n_list4.add(country5);
		n_list4.add(country3);
		n_list4.add(country1);

		List<Country> n_list3 = new ArrayList<Country>();
		n_list3.add(country3);

		List<Country> n_list1 = new ArrayList<Country>();
		n_list1.add(country1);
		n_list1.add(country5);
		n_list1.add(country6);

		List<Country> n_list2 = new ArrayList<Country>();
		n_list2.add(country1);
		n_list2.add(country3);
		n_list2.add(country5);
		n_list2.add(country4);

		player1 = new Player(9);
		player1.setPlayerId(9);
		player1.setPlayerName("abc");
		player1.setPlayerArmies(7);
		player1.setTotalCountriesOccupied(n_list4);

		player2 = new Player(10);
		player2.setPlayerId(10);
		player2.setPlayerName("xyz");
		player2.setPlayerArmies(8);
		player2.setTotalCountriesOccupied(n_list1);

		country1.setNeighbors(n_list);
		country1.setNoOfArmies(1);
		country1.setPlayer(player1);
		country1.setContinent(continent1);

		country2.setNeighbors(n_list3);
		country2.setNoOfArmies(4);
		country2.setPlayer(player1);
		country2.setContinent(continent1);

		country3.setNeighbors(n_list);
		country3.setNoOfArmies(4);
		country3.setPlayer(player1);
		country3.setContinent(continent1);

		country6.setNeighbors(n_list);
		country6.setNoOfArmies(4);
		country6.setPlayer(player1);
		country6.setContinent(continent2);

		country5.setNeighbors(n_list3);
		country5.setNoOfArmies(4);
		country5.setPlayer(player2);
		country5.setContinent(continent2);

		country4.setNeighbors(n_list1);
		country4.setNoOfArmies(2);
		country4.setPlayer(player1);
		country4.setContinent(continent2);
		
		continent1.setContinentId(81);
		continent1.setName("Asia");
		continent1.setCountries(n_list);
		continent1.setControlValue(4);

		continent2.setContinentId(82);
		continent2.setName("Africa");
		continent2.setCountries(n_list2);
		continent2.setControlValue(5);

		ReadingFiles.ContinentNameObject = new HashMap<>();
		ReadingFiles.ContinentNameObject.put(continent1.getName(), continent1);
		ReadingFiles.ContinentNameObject.put(continent2.getName(), continent2);
	}

	/**
	 * Tests path between countries
	 */
	@Test
	public void testHasPathBFS() {
		assertEquals(true, fortification.hasPathBFS2(country1, country3));
	}

	/**
	 * Tests path between countries
	 */
	@Test
	public void test1HasPathBFS() {
		assertNotEquals(true, fortification.hasPathBFS2(country2, country4));
	}

	/**
	 * Tests can armies be moved or not
	 */
	@Test
	public void testMoveArmies() {
		assertEquals("less army", fortification.moveArmies(country1, country3, 2));
	}

	/**
	 * Tests can armies be moved or not
	 */
	@Test
	public void test1MoveArmies() {
		assertEquals("You can only move3", fortification.moveArmies(country2, country3, 5));
	}

	/**
	 * Tests can armies be moved or not
	 */
	@Test
	public void test2MoveArmies() {
		assertEquals("NO path", fortification.moveArmies(country2, country5, 3));
	}

	/**
	 * Tests can armies be moved or not
	 */
	@Test
	public void test3MoveArmies() {
		assertEquals("", fortification.moveArmies(country2, country3, 3));
	}
}
