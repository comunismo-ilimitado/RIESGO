package controller;

import java.util.*;
import model.*;

public class BenevolentStrategy implements IStrategy {
	public void reinforce(Player player) {
		player.calcArmiesByControlValue(player);
		List<Country> countries = player.getMyCountries(player);
		Country country = countries.get(getWeakestCountryIndex(countries));
		country.setNoOfArmies(country.getNoOfArmies() + player.getPlayerArmiesNotDeployed());
		player.getMyCountries(player).get(getWeakestCountryIndex(countries)).setNoOfArmies(country.getNoOfArmies());
		player.setPlayerTotalArmiesNotDeployed(0);
		ReadingFiles.CountryNameObject.put(country.getName(), country);
	}

	public void attack(Player player) {
		return;
	}

	public void fortify(Player player) {
		FortificationController fC = new FortificationController();
		List<Country> countries = player.getMyCountries(player);
		Country weakcountry = countries.get(getWeakestCountryIndex(countries));
		countries.remove(weakcountry);
		List<Country> canfortifycountries = new ArrayList<>();
		while (countries.size()>0) {
			for (int i = 0; i < countries.size(); i++) {
				if (countries.get(i).getNoOfArmies() > 1 && fC.hasPathBFS2(weakcountry, countries.get(i))) {
					canfortifycountries.add(countries.get(i));
				}
			}
			if (canfortifycountries.size() > 0) {
				Country strongestcountry = canfortifycountries.get(0);
				for (int i = 1; i < canfortifycountries.size(); i++) {
					if (canfortifycountries.get(i).getNoOfArmies() > strongestcountry.getNoOfArmies()) {
						strongestcountry = canfortifycountries.get(i);
					}
				}
				if (strongestcountry != null) {
					weakcountry.setNoOfArmies(weakcountry.getNoOfArmies() + strongestcountry.getNoOfArmies() - 1);
					strongestcountry.setNoOfArmies(1);
				}
				int index = getIndex(weakcountry, player.getMyCountries(player));
				player.getMyCountries(player).get(index).setNoOfArmies(weakcountry.getNoOfArmies());
				index=getIndex(strongestcountry, player.getMyCountries(player));
				player.getMyCountries(player).get(index).setNoOfArmies(strongestcountry.getNoOfArmies());
				ReadingFiles.CountryNameObject.put(weakcountry.getName(),weakcountry);
				ReadingFiles.CountryNameObject.put(strongestcountry.getName(),strongestcountry);
				break;
			} else {
				weakcountry = countries.get(getWeakestCountryIndex(countries));
				countries.remove(weakcountry);
				canfortifycountries.clear();
			}
		}
	}
	
	public int getIndex(Country country, List<Country> countries) {
		for(int i=0;i<countries.size();i++) {
			if(country.getName().equals(countries.get(i).getName())) {
				return i;
			}
		}
		return -1;
	}

	public int getWeakestCountryIndex(List<Country> countries) {
		Country country = countries.get(0);
		int index = 0;
		for (int i = 1; i < countries.size(); i++) {
			if (country.getNoOfArmies() > countries.get(i).getNoOfArmies()) {
				country = countries.get(i);
				index = i;
			}
		}
		return index;
	}
}
