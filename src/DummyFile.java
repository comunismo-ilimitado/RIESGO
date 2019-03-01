import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class DummyFile {
	
	public void dummyObjects()
	{
	
	Country country1 = new Country("India");
		country1.setContinentId(1);
		country1.setCountryId(11);
		country1.setName("India");
		
		Country country2 = new Country("China");
		country2.setContinentId(2);
		country2.setCountryId(21);
		country2.setName("China");
		
		Country country3 = new Country("Pakistan");
		country3.setContinentId(3);
		country3.setCountryId(31);
		country3.setName("Pakistan");
		
		Country country4 = new Country("Bhutan");
		country4.setContinentId(4);
		country4.setCountryId(41);
		country4.setName("Bhutan");
		
		Country country5 = new Country("Iran");
		country5.setContinentId(5);
		country5.setCountryId(51);
		country5.setName("Iran");
		
		Country country6 = new Country("Canada");
		country6.setContinentId(6);
		country6.setCountryId(61);
		country6.setName("Canada");
		
		
		List<Country> n_list = new ArrayList<Country>();
		n_list.add(country2);
		n_list.add(country3);
		
		List<Country> n_list1 = new ArrayList<Country>();
		n_list1.add(country5);
		n_list1.add(country6);
		
		Player player1 = new Player(9);
		player1.setPlayerId(9);
		player1.setPlayerName("Navjot");
		player1.setPlayerColor(new Color(255,255,0));
		player1.setPlayerTotalArmiesNotDeployed(4);
		player1.setContinentsOccupied(null);
		player1.setPlayerArmies(7);
		player1.setTotalCountriesOccupied(n_list);
		
		Player player2 = new Player(10);
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
		
		FortificationController fc = new FortificationController();
		fc.getMyCountries(player1);
		fc.moveArmies(country1, country3, 2);
		
		fc.getMyCountries(player2);
		fc.moveArmies(country4, country2, 3);
		
		
		
		
		
		
		
	}
}

