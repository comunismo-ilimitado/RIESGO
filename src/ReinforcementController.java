import java.util.*;

/**
 * This controller class has all the methods needs for the reinforcement phase
 * of the game
 * 
 * @author neeraj
 * @version 1.1
 */
class ReinforcementController {
	public void addarmies(Player player, Country country) {
		if(player.getPlayerArmiesNotDeployed()==0) {
			//display u dont have anymore to reinforce
		}
		else {
			updateValue(player,country);
		}
	}
	
	public List<Country> getMyCountries(Player player) {
		List<Country> countries = new ArrayList<Country>();
		for (Map.Entry<String, Country> entry : ReadingFiles.CountryNameObject.entrySet()) {
			if (entry.getValue().getOwner().equals(player)) {
				countries.add(entry.getValue());
			} else
				continue;
		}
		return countries;
	}

	public void calculateReinforcementArmies(Player player) {
		int totalCountriesOFPlayer = player.getTotalCountriesOccupied().size();
		int total_armies_to_reinforce;
		total_armies_to_reinforce = totalCountriesOFPlayer / 3;
		int armies = 0;
		if (totalCountriesOFPlayer < 3) {
			armies = armies + 3;
		} else {
			armies = armies + total_armies_to_reinforce;
		}
		armies = armies + calcArmiesByControlValue(player);
		player.setPlayerTotalArmiesNotDeployed(armies);
	}

	public List<Continent> playerOwnsContinent(Player player) {
		List<Continent> continents = new ArrayList<Continent>();
		for (Map.Entry<String, Continent> entry : ReadingFiles.ContinentNameObject.entrySet()) {
			List<Country> temp = entry.getValue().getCountries();
			int counter = 0;
			for (int i = 0; i < entry.getValue().getCountries().size(); i++) {
				if (entry.getValue().getCountries().get(i).getOwner().equals(player))
					counter++;
				else
					continue;
			}
			if (temp.size() == counter)
				continents.add(entry.getValue());
			else
				continue;
		}
		return continents;
	}

	public int calcArmiesByControlValue(Player player) {
		List<Continent> continents = playerOwnsContinent(player);
		int armies = 0;
		for (int i = 0; i < continents.size(); i++) {
			armies = armies + continents.get(i).getControlValue();
		}
		return armies;
	}

	public void updateValue(Player player, Country country) {
		country.setNoOfArmies(country.getNoOfArmies() + 1);
		player.setPlayerTotalArmiesNotDeployed(player.getPlayerArmiesNotDeployed() - 1);
	}

	public String endReinforcementCheck(Player player) {
		if (player.getPlayerArmiesNotDeployed() == 0) {
			return "Please deploy all your armies before proceeding to attack";
		} else
			return null;
	}
}
