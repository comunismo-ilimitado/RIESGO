package ControllerTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.ReadingFiles;
import controller.ReinforcementController;
import model.CardTypes;
import model.Continent;
import model.Country;
import model.Player;

/**
 * This class tests reinforcement controller
 * 
 * @author Navjot kaur
 * @version 1.0
 */
public class ReinforcementTest {
	controller.ReinforcementController reinforcement;
	Player player1, player2;
	Country country1, country2, country3, country4, country5, country6, country7;
	Continent continent1, continent2;
	HashMap<String, Continent> temp;
	List<CardTypes> list1,list2,list3,list4;

	/**
	 * creates all objects necessary
	 */
	@Before
	public void onStart() {
		reinforcement = new ReinforcementController();
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
		country2.setPlayer(player1);

		country3.setContinentId(3);
		country3.setCountryId(31);
		country3.setName("Pakistan");
		country3.setPlayer(player1);

		country4.setContinentId(4);
		country4.setCountryId(41);
		country4.setName("Bhutan");
		country4.setPlayer(player1);

		country5.setContinentId(5);
		country5.setCountryId(51);
		country5.setName("Iran");
		country5.setContinent(continent2);
		

		country6.setContinentId(6);
		country6.setCountryId(61);
		country6.setName("Canada");
		country6.setContinent(continent2);
		
		country7.setContinentId(7);
		country7.setCountryId(71);
		country7.setName("Egypt");
		country7.setContinent(continent2);
		

		List<Country> n_list = new ArrayList<Country>();
		n_list.add(country1);
		n_list.add(country2);
		n_list.add(country3);
		n_list.add(country4);

		List<Country> n_list1 = new ArrayList<Country>();
		n_list1.add(country5);
		n_list1.add(country6);

		List<Country> n_list2 = new ArrayList<Country>();
		n_list2.add(country5);
		n_list2.add(country6);
		n_list2.add(country7);

		List<Country> n_list3 = new ArrayList<Country>();
		n_list3.add(country1);
		n_list3.add(country2);
		n_list3.add(country3);
		n_list3.add(country4);
		
		//list of cards a player has
		List<CardTypes> listp1 = new ArrayList<>();
		listp1.add(CardTypes.Infantry);
		listp1.add(CardTypes.Cavalry);
		listp1.add(CardTypes.Artillery);
		
		List<CardTypes> listp2 = new ArrayList<>();
		listp2.add(CardTypes.Artillery);
		listp2.add(CardTypes.Cavalry);
		listp2.add(CardTypes.Artillery);
		listp2.add(CardTypes.Infantry);
		listp2.add(CardTypes.Infantry);
		listp2.add(CardTypes.Cavalry);
		
		//temp list of cards
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
		country1.setNoOfArmies(4);
		country1.setPlayer(player1);
		country1.setContinent(continent1);

		country2.setPlayer(player1);
		country2.setContinent(continent1);
		country3.setPlayer(player1);
		country3.setContinent(continent1);

		country4.setNeighbors(n_list1);
		country4.setNoOfArmies(2);
		country4.setPlayer(player1);
		country4.setContinent(continent1);
		
		country6.setPlayer(player2);
		country7.setPlayer(player2);
		country5.setPlayer(player2);

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
		temp = ReadingFiles.ContinentNameObject;
		ReadingFiles.ContinentNameObject.clear();
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
	 * Clears all static variables
	 */
	@After
	public void atEnd() {
		ReadingFiles.ContinentNameObject.clear();
		ReadingFiles.ContinentNameObject = temp;

	}

	/**
	 * Tests calculation of armies
	 */
	@Test
	public void testCalculateReinforcementArmies() {
		reinforcement.calculateReinforcementArmies(player1);
		assertEquals(11, player1.getPlayerArmiesNotDeployed());

	}

	/**
	 * Tests calculation of armies
	 */
	@Test
	public void test1CalculateReinforcementArmies() {
		reinforcement.calculateReinforcementArmies(player2);
		assertNotEquals(4, player2.getPlayerArmiesNotDeployed());

	}

	/**
	 * Tests updating armies accordingly
	 */
	@Test
	public void testUpdateValue() {
		reinforcement.updateValue(player1, country4);
		assertEquals(3, country4.getNoOfArmies());
		assertEquals(3, player1.getPlayerArmiesNotDeployed());
	}

	/**
	 * Tests get countries method
	 */
	@Test
	public void testGetMyCountries() {
		assertEquals(4, reinforcement.getMyCountries(player1).size());
	}

	/**
	 * Tests add armies method
	 */
	@Test
	public void testAddArmies() {
		assertEquals("", reinforcement.addarmies(country1));
	}

	/**
	*Tests add armies method
	*
	*/
	@Test
	public void test1AddArmies() {
		assertEquals("NO ARMIES LEFT, PLEASE CLICK FINISH REINFORCEMENT", reinforcement.addarmies(country6));
	}

	/**
	 * Tests End Reinforcement method
	 */
	@Test
	public void testEndReinforcementCheck() {
		assertEquals("Please deploy all your armies before proceeding to attack",
				reinforcement.endReinforcementCheck(player2));

	}

	/**
	 * Tests End Reinforcement method
	 */
	@Test
	public void test1EndReinforcementCheck() {
		assertEquals(null, reinforcement.endReinforcementCheck(player1));

	}
	
	/**
	*Tests Excahnge of cards
	*
	*/
	@Test
	public void testExchangeCards()
	{
		assertEquals("",reinforcement.exchangeCards(list1, player1));
	}
	
	/**
	*Tests Excahnge of cards
	*
	*/
	@Test
	public void testExchangeCards1()
	{
		assertEquals("",reinforcement.exchangeCards(list2, player1));
	}
	
	/**
	*Tests Excahnge of cards
	*
	*/
	@Test
	public void testExchangeCards2()
	{
		assertEquals("Cannot exchange these cards for armies and select atleast 3 cards",reinforcement.exchangeCards(list3, player1));
	}
	
	
	/**
	*Tests Excahnge of cards
	*
	*/
	@Test
	public void testExchangeCards3()
	{
		assertEquals("Select 3 cards of the same type or all unique",reinforcement.exchangeCards(list4, player1));
	}
	
	/**
	*Tests if player has more cards or not
	*
	*/
	@Test
	public void testHasMoreCards()
	{
		assertTrue(reinforcement.hasMoreCards(player2));
	}
	
	/**
	*Tests if player has more cards or not
	*
	*/
	@Test
	public void testHasMoreCards1()
	{
		assertFalse(reinforcement.hasMoreCards(player1));
	}
	


}
