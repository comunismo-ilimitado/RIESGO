import java.util.*;
import java.io.*;
//When the player clicks attack phase call getAllMyCountries method and using the list of countries display all his countries
//When the player selects his country that he wants to do the attack with, show its neighbours by 
//calling getMyNeighbors, that are not his own
//If the list is empty show popup asking to choose another country and go to step-1 again
//when the player clicks the country that he wants to attack, call getNoOfDice method for both countries
//Then call rolldice method every time till the no.of armies becomes zero
//No.of armies will be updated for each country after every rolldice method call
//Write a check before calling rolldice(not before getNoOfDice method because after starting 
//attack you cannot change no.of dice) method if the no.of armies is zero or not
//If the player clicks continue attack button go to step-1 and continue


public void attackButtonCLick(Country attacker, Country defender) {
	int attackerDice=setNoOfDice(attacker);
	int defenderDice=setNoOfDice(defender);
	String winner="";
	while(attacker.getNoOfArmies()!=0 && defender.getNoOfArmies()!=0) {
		winner=attack(attackerDice, defenderDice);
		if(winner.equals("Attacker"))
			updateArmies(defender);
		else
			updateArmies(attacker);
	}
	if(attacker.getNoOfArmies()==0 && defender.getNoOfArmies()!=0) 
		updateOwner(defender.getOwner(), attacker);
	else if(defender.getNoOfArmies()==0 && attacker.getNoOfArmies()!=0)
		updateOwner(attacker.getOwner(), defender);
	else
	{
		updateOwner(null, attacker);
		updateOwner(null, defender);
	}
}
/**
 * AttackController has all the methods needed in attack phase of the game
 * @author Bhargav Raghavendra
 * @version 1.0.0
 */
public class AttackController {
	/**
	 * Sets number of dice based on armies before rolling dice
	 * Only call this method once before attack
	 * @param country
	 * @return
	 */
	public int setNoOfDice(Country country) {
		return getNoOfDice(country);
	}
	/**
	 * Returns the winner of each dice roll as a string, either "Attacker" or "Defender"
	 * @param attackerDice
	 * @param defenderDice
	 * @return
	 */
	public String attack(int attackerDice, int defenderDice) {
		int attackerSum=rollDice(attackerDice);
		int defenderSum=rollDice(defenderDice);
		if(attackerSum<=defenderSum)
			return "Defender";
		else
			return "Attacker";
	}
	/**
	 * Gets a list of countries that the player owns
	 * @param player
	 * @return List of countries
	 */
	public List<Country> getMyCountries(Player player){
		List<Country> countries=new ArrayList<Country>();
		for(Map.Entry<String, Country> entry:ReadingFiles.CountryNameObject.entrySet()){       
			if(entry.getValue().getOwner().equals(player))
			{
				countries.add(entry.getValue());
			}
			else
				continue;
		}
		return countries;
	}
	/**
	 * Gets the neighbors of a given country excluding the countries with the same owner as the given country
	 * @param country
	 * @return
	 */
	public List<Country> getMyNeighborsForAttack(Country country){
		List<Country> neighbors=country.getNeighbors();
		int total=neighbors.size();
		for(int i=0;i<total;i++)
		{
			if(neighbors.get(i).getOwner().equals(country.getOwner()))
				neighbors.remove(i);
			else
				continue;
		}
		return neighbors;
	}

	/**
	 * Decrements armies of a country every dice roll
	 * @param country
	 * @return
	 */
	public int updateArmies(Country country) {
		int armies=country.getNoOfArmies();
		country.setNoOfArmies(armies-1);
		return armies-1;
	}
	/**
	 * Update owner of the country if the opponent wins
	 * Or update the owner of attacked country if the attacker wins
	 * @param player
	 * @param country
	 */
	public void updateOwner(Player player, Country country) {
		country.setPlayer(player);
	}
	/**
	 * Gets number of dice for the attack and defense
	 * @param country
	 * @return Number of Dice 
	 */
	public int getNoOfDice(Country country)
	{
		if(country.getNoOfArmies()==2)
			return 2;
		else if(country.getNoOfArmies()>=3)
			return 3;
		else
			return 0;
	}
	/**
	 * Rolls dice as required depending on number of armies
	 * @param noOfDice
	 * @return sum
	 */
	public int rollDice(int noOfDice){
		if(noOfDice==1)
		{
		    int sum=(int)(Math.random()*6+1);
		    return sum;
		}
		else if(noOfDice==2)
		{
		    int sum=(int)(Math.random()*6+1);
		    sum=sum+(int)(Math.random()*6+1);
		    return sum;
		}
		else if(noOfDice>=3)
		{
			int sum=(int)(Math.random()*6+1);
		    sum=sum+(int)(Math.random()*6+1);
		    sum=sum+(int)(Math.random()*6+1);
		    return sum;
		}
		return 0;
	}
	/**
	 * Check if the country has any armies to attack before rolling the dice
	 * @param country
	 * @return
	 */
	public boolean checkArmies(Country country){
		if(country.getNoOfArmies()==0)
			return true;
		return false;
	}
	/**
	 * Gets player based on Owner Name from Country
	 * @param PlayerName
	 * @return
	 */
	public Player getPlayerByName(String PlayerName){
		return ReadingFiles.playerObject.get(PlayerName);
	}
	/**
	 * Updates the owner of a country according to the winner and loser of the attack
	 * @param CountryName
	 * @param PlayerName
	 */
	public void updateOwner(Country country,Player player){
		ReadingFiles.CountryNameObject.get(country.getName()).setPlayer(player);
		return;
	}
	/**
	 * Adds armies to a country that the player has won
	 * @param country
	 */
	public void placeArmies(Country country) {
		int armies=country.getNoOfArmies();
		country.setNoOfArmies(armies+1);
	}
}
