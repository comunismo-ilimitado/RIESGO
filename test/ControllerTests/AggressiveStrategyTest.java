package ControllerTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.AggressiveStratery;
import controller.ReadingFiles;
import model.CardTypes;
import model.Continent;
import model.Country;
import model.Player;

/**
 * This class tests the Aggressive Strategy
 * 
 * @author navjot
 * @version 1.0
 *
 */
public class AggressiveStrategyTest {
	AggressiveStratery ast;
	Player player1, player2, player3;
	Country country1, country2, country3, country4, country5, country6, country7;
	Continent continent1, continent2;
	HashMap<String, Country> temp;
	HashMap<Integer, Player> temp1;
	HashMap<String, Continent> temp3;
	List<Country> n_list, n_list1;
	List<CardTypes> list1, list2, list3, list4;

	/**
	 * Method called before each test
	 */
	@Before
	public void onStart() {
		ast = new AggressiveStratery();
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

		n_list = new ArrayList<Country>();
		n_list.add(country2);
		n_list.add(country5);
		n_list.add(country6);

		List<Country> n_list4 = new ArrayList<Country>();
		n_list4.add(country2);
		n_list4.add(country5);
		n_list4.add(country3);
		n_list4.add(country1);

		List<Country> n_list3 = new ArrayList<Country>();
		n_list3.add(country3);

		n_list1 = new ArrayList<Country>();
		n_list1.add(country1);
		n_list1.add(country5);
		n_list1.add(country6);

		List<Country> n_list2 = new ArrayList<Country>();
		n_list2.add(country1);
		n_list2.add(country3);
		n_list2.add(country5);

		List<CardTypes> listp1 = new ArrayList<>();
		listp1.add(CardTypes.Infantry);
		listp1.add(CardTypes.Cavalry);
		listp1.add(CardTypes.Cavalry);

		List<CardTypes> listp2 = new ArrayList<>();
		listp2.add(CardTypes.Artillery);
		listp2.add(CardTypes.Cavalry);
		listp2.add(CardTypes.Artillery);
		listp2.add(CardTypes.Infantry);
		listp2.add(CardTypes.Infantry);
		listp2.add(CardTypes.Cavalry);

		list1 = new ArrayList<>();
		list1.add(CardTypes.Artillery);
		list1.add(CardTypes.Cavalry);
		list1.add(CardTypes.Infantry);

		list2 = new ArrayList<>();
		list2.add(CardTypes.Artillery);
		list2.add(CardTypes.Artillery);
		list2.add(CardTypes.Artillery);

		list3 = new ArrayList<>();
		list3.add(CardTypes.Artillery);
		list3.add(CardTypes.Cavalry);
		list3.add(CardTypes.Artillery);

		list4 = new ArrayList<>();
		list4.add(CardTypes.Artillery);
		list4.add(CardTypes.Cavalry);

		player1 = new Player(9);
		player1.setPlayerId(9);
		player1.setPlayerName("Navjot");
		player1.setPlayerColor(new Color(255, 255, 0));
		player1.setPlayerTotalArmiesNotDeployed(4);
		player1.setContinentsOccupied(null);
		player1.setPlayerArmies(7);
		player1.setTotalCountriesOccupied(n_list);
		player1.setPlayerCards(listp1);

		player2 = new Player(10);
		player2.setPlayerId(10);
		player2.setPlayerName("Neeraj");
		player2.setPlayerColor(new Color(0 - 191 - 255));
		player2.setPlayerTotalArmiesNotDeployed(0);
		player2.setContinentsOccupied(null);
		player2.setPlayerArmies(8);
		player2.setTotalCountriesOccupied(n_list3);
		player2.setPlayerCards(listp2);

		country1.setNeighbors(n_list);
		country1.setNoOfArmies(1);
		country1.setPlayer(player2);

		country2.setNeighbors(n_list3);
		country2.setNoOfArmies(4);
		country2.setPlayer(player1);

		country3.setNeighbors(n_list);
		country3.setNoOfArmies(6);
		country3.setPlayer(player1);

		country6.setNeighbors(n_list);
		country6.setNoOfArmies(1);
		country6.setPlayer(player2);

		country5.setNeighbors(n_list3);
		country5.setNoOfArmies(5);
		country5.setPlayer(player2);

		country4.setNeighbors(n_list1);
		country4.setNoOfArmies(2);
		country4.setPlayer(player1);

		continent1.setContinentId(81);
		continent1.setName("Asia");
		continent1.setCountries(n_list);
		continent1.setControlValue(4);

		continent2.setContinentId(82);
		continent2.setName("Africa");
		continent2.setCountries(n_list2);
		continent2.setControlValue(5);

		ReadingFiles.CountryNameObject = new HashMap<>();
		ReadingFiles.ContinentNameObject = new HashMap<>();
		ReadingFiles.playerId = new HashMap<>();
		temp = ReadingFiles.CountryNameObject;
		temp1 = ReadingFiles.playerId;
		temp3 = ReadingFiles.ContinentNameObject;
		ReadingFiles.ContinentNameObject.clear();
		ReadingFiles.playerId.clear();
		ReadingFiles.CountryNameObject.clear();
		ReadingFiles.ContinentNameObject.put(continent1.getName(), continent1);
		ReadingFiles.ContinentNameObject.put(continent2.getName(), continent2);
		ReadingFiles.CountryNameObject.put(country1.getName(), country1);
		ReadingFiles.CountryNameObject.put(country2.getName(), country2);
		ReadingFiles.CountryNameObject.put(country3.getName(), country3);
		ReadingFiles.CountryNameObject.put(country4.getName(), country4);
		ReadingFiles.CountryNameObject.put(country5.getName(), country5);
		ReadingFiles.CountryNameObject.put(country6.getName(), country6);
		ReadingFiles.playerId.put(player1.getPlayerId(), player1);
		ReadingFiles.playerId.put(player2.getPlayerId(), player2);
	}

	/**
	 * Method called after each test
	 */
	@After
	public void atEnd() {
		ReadingFiles.CountryNameObject.clear();
		ReadingFiles.playerId.clear();
		ReadingFiles.CountryNameObject = temp;
		ReadingFiles.playerId = temp1;

	}

	/**
	 * Method test the player with maximum number of armies
	 */
	@Test
	public void testStrongestPlayer() {
		Country temp = n_list.get(ast.getStrongestCountry(n_list));
		assertEquals(country5, temp);

	}

	/**
	 * Method tests the reinforcement phase based on aggressive strategy rules
	 */
	@Test
	public void testReinforce() {
		List<Country> countries = player1.getMyCountries(player1);
		int n = countries.get(ast.getStrongestCountry(countries)).getNoOfArmies()
				+ player1.getPlayerArmiesNotDeployed();
		ast.reinforce(player1);
		assertEquals(n, countries.get(ast.getStrongestCountry(countries)).getNoOfArmies());
	}

	/**
	 * Method tests the attack phase based on aggressive strategy rules
	 */
	@Test
	public void testAttack() {
		List<Country> countries = player1.getMyCountries(player1);
		Country c = countries.get(ast.getStrongestCountry(countries));
		ast.attack(player1);
		int a = c.getNoOfArmies();
		List<Country> an = player1.getMyNeighborsForAttack(c);
		if (a > 1) {
			assertEquals(an.size(), 0);
		} else {
			assertEquals(a, 1);
		}
	}

	/**
	 * Method tests the fortification phase based on aggressive strategy rules
	 */
	@Test
	public void testFortify() {
		List<Country> countries = player1.getMyCountries(player1);
		Country c = countries.get(ast.getStrongestCountry(countries));
		int armies_before_fortify = c.getNoOfArmies();
		ast.fortify(player1);
		assertEquals(true, c.getNoOfArmies() > armies_before_fortify);
	}
}
