import java.util.*;

/**
 * This controller class has all the methods needs for the reinforcement phase
 * of the game
 * 
 * @author neeraj
 * @version 1.1
 */
class ReinforcementController {
	public String addarmies(Country country) {
		 Player player=country.getOwner();
		if(player.getPlayerArmiesNotDeployed()==0) {
			return "NO ARMIES LEFT, PLEASE CLICK FINISH REINFORCEMENT";
		}
		else {
			updateValue(player,country);
			return "";
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
		float total_armies_to_reinforce;
		total_armies_to_reinforce =  (float)totalCountriesOFPlayer / 3;
		int armies = 0;
		if (total_armies_to_reinforce < 3.0) {
			armies = armies + 3;
		} else {
			armies = armies + (int) total_armies_to_reinforce;
		}
		armies = armies + calcArmiesByControlValue(player);
		player.setPlayerTotalArmiesNotDeployed(armies);
		//return armies;
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
