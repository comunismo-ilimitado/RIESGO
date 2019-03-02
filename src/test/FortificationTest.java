package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.FortificationController;
import model.Continent;
import model.Country;
import model.Player;

public class FortificationTest 
{
	FortificationController fortification;
	Player player1,player2;
	Country country1,country2,country3,country4,country5,country6;
	Continent continent1;
	
	@Before
	public void onStart()
	{
		fortification = new FortificationController();
		player1 = new Player(2);
		country1 = new Country("India");
		country2 = new Country("China");
		country3 = new Country("Pakistan");
		country4 = new Country("Bhutan");
		country5 = new Country("Iran");
		country6 = new Country("Canada");
		
		
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
		
		
		List<Country> n_list = new ArrayList<Country>();
		n_list.add(country2);
		n_list.add(country3);
		n_list.add(country4);
		
		List<Country> n_list1 = new ArrayList<Country>();
		n_list1.add(country5);
		n_list1.add(country6);
		
		player1 = new Player(9);
		player1.setPlayerId(9);
		player1.setPlayerName("Navjot");
		player1.setPlayerColor(new Color(255,255,0));
		player1.setPlayerTotalArmiesNotDeployed(4);
		player1.setContinentsOccupied(null);
		player1.setPlayerArmies(7);
		player1.setTotalCountriesOccupied(n_list);
		
		player2 = new Player(10);
		player2.setPlayerId(10);
		player2.setPlayerName("Neeraj");
		player2.setPlayerColor(new Color(0-191-255));
		player2.setPlayerTotalArmiesNotDeployed(5);
		player2.setContinentsOccupied(null);
		player2.setPlayerArmies(8);
		player2.setTotalCountriesOccupied(n_list1);
		
	
		country1.setNeighbors(n_list);
		country1.setNoOfArmies(4);
		country1.setPlayer(player1);
		
		country4.setNeighbors(n_list1);
		country4.setNoOfArmies(2);
		country4.setPlayer(player2);
		
		continent1.setContinentId(81);
		continent1.setName("Asia");
		continent1.setCountries(n_list);
		continent1.setControlValue(4);
		
	}
	
	@Test
	public void testHasPathBFS() 
	{
		assertEquals(true,fortification.hasPathBFS2(country1, country4));
	}
	
	@Test
	public void test1HasPathBFS() 
	{
		assertNotEquals(true,fortification.hasPathBFS2(country1, country5));
	}
}
