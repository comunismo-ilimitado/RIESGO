package ControllerTests;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.AttackController;
import controller.ReadingFiles;
import model.Continent;
import model.Country;
import model.Player;

/**
 * Tests the attack controller
 * @author Navjot kaur
 *
 */

public class AttackTest 
{
	AttackController attack;
	Player player1, player2, player3;
	Country country1, country2, country3, country4, country5, country6, country7;
	Continent continent1;
	HashMap<String, Country> temp;
	HashMap<Integer,Player> temp1;
	List<Country> n_list,n_list1;
	
	/**
	* Method called before each test
	*
	*/
	@Before
	public void onStart()
	{
		attack = new AttackController();
		player1 = new Player(2);
		country1 = new Country("India");
		country2 = new Country("China");
		country3 = new Country("Pakistan");
		country4 = new Country("Bhutan");
		country5 = new Country("Iran");
		country6 = new Country("Canada");
		country7 = new Country("Egypt");

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
		country1.setPlayer(player2);

		country2.setNeighbors(n_list3);
		country2.setNoOfArmies(4);
		country2.setPlayer(player1);

		country3.setNeighbors(n_list);
		country3.setNoOfArmies(4);
		country3.setPlayer(player1);

		country6.setNeighbors(n_list);
		country6.setNoOfArmies(0);
		country6.setPlayer(player2);

		country5.setNeighbors(n_list3);
		country5.setNoOfArmies(4);
		country5.setPlayer(player2);

		country4.setNeighbors(n_list1);
		country4.setNoOfArmies(2);
		country4.setPlayer(player1);
		
		ReadingFiles.CountryNameObject = new HashMap<>();
		ReadingFiles.playerId = new HashMap<>();
		temp = ReadingFiles.CountryNameObject;
		temp1 = ReadingFiles.playerId;
		ReadingFiles.playerId.clear();
		ReadingFiles.CountryNameObject.clear();
		ReadingFiles.CountryNameObject.put(country1.getName(), country1);
		ReadingFiles.CountryNameObject.put(country2.getName(), country2);
		ReadingFiles.CountryNameObject.put(country3.getName(), country3);
		ReadingFiles.CountryNameObject.put(country4.getName(), country4);
		ReadingFiles.CountryNameObject.put(country5.getName(), country5);
		ReadingFiles.CountryNameObject.put(country6.getName(), country6);
		
		ReadingFiles.playerId.put(player1.getPlayerId(),player1);
		ReadingFiles.playerId.put(player2.getPlayerId(),player2);
		
	}
	/**
	*Method called after each test
	*
	*/
	@After
	public void atEnd() {
		ReadingFiles.CountryNameObject.clear();
		ReadingFiles.playerId.clear();
		ReadingFiles.CountryNameObject = temp;
		ReadingFiles.playerId = temp1;

	}
	/**
	*Tests Countries of the player
	*
	*/
	@Test
	public void testGetMyCountries() {
		assertEquals(3, attack.getMyCountries(player2).size());
	}

	/**
	*Tests list neighbor countries of the player
	*
	*/
	@Test
	public void testGetMyNeighborsForAttack()
	{
		assertEquals(n_list,attack.getMyNeighborsForAttack(country1));
	}
	/**
	*Tests attack simulator
	*
	*/
	@Test
	public void testAttackButton()
	{
		assertEquals("Wrong input",attack.attackButton(country3, country6, 3,1,false));
	}
	/**
	*Tests attack simulator
	*
	*/
	@Test
	public void testAttackButton1()
	{
		assertEquals("Your country must have more than one army",attack.attackButton(country1,country2,3,1,false));
	}
	
	/**
	*Tests attack simulator
	*
	*/
	@Test
	public void testAttackButton2()
	{
		assertEquals("",attack.attackButton(country2,country1,3,1,true));		
	}
	
	/**
	*Tests update owner of the country
	*
	*/
	@Test
	public void testUpdateOwner()
	{
		attack.updateOwner(country1, player1);
		assertEquals(player1,country1.getOwner());
	}
	
}
