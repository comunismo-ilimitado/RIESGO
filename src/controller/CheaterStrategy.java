package controller;

import java.util.*;
import model.*;

public class CheaterStrategy implements IStrategy {
	public void reinforce(Player player) {
		List<Country> countries = player.getMyCountries(player);
		for (int i = 0; i < countries.size(); i++) {
			countries.get(i).setNoOfArmies(countries.get(i).getNoOfArmies() * 2);
			player.getMyCountries(player).get(i).setNoOfArmies(countries.get(i).getNoOfArmies());
			ReadingFiles.CountryNameObject.put(countries.get(i).getName(), countries.get(i));
		}
		player.setPlayerTotalArmiesNotDeployed(0);
		player.setPlayerCards(null);
	}

	public void attack(Player player) {
//		AttackController aC = new AttackController();
		List<Country> mycountries = player.getMyCountries(player);
		for (int i = 0; i < mycountries.size(); i++) {
//			List<Country> neighbors = aC.getMyNeighborsForAttack(mycountries.get(i));
//			for(int j=0;j<neighbors.size();j++) {
//				int index = getIndex(neighbors.get(j), mycountries.get(i).getNeighbors());
//				player.getMyCountries(player).get(i).getNeighbors().get(index).setPlayer(player);
//				//mycountries.get(i).getNeighbors().get(index).setPlayer(player);
//				neighbors.get(j).setPlayer(player);
//				ReadingFiles.CountryNameObject.put(neighbors.get(j).getName(), neighbors.get(j));
//			}
			List<Country> neighbors = mycountries.get(i).getNeighbors();
			for (int j = 0; j < mycountries.size(); i++) {
				player.getMyCountries(player).get(i).getNeighbors().get(j).setPlayer(player);
				neighbors.get(j).setPlayer(player);
				ReadingFiles.CountryNameObject.get(neighbors.get(j).getName()).setPlayer(player);
			}
		}
	}

	public void fortify(Player player) {
		AttackController aC = new AttackController();
		List<Country> mycountries = player.getMyCountries(player);
		for (int i = 0; i < mycountries.size(); i++) {
			if (aC.getMyNeighborsForAttack(mycountries.get(i)).size() > 0) {
				mycountries.get(i).setNoOfArmies(mycountries.get(i).getNoOfArmies() * 2);
				player.getMyCountries(player).get(i).setNoOfArmies(mycountries.get(i).getNoOfArmies());
				ReadingFiles.CountryNameObject.put(mycountries.get(i).getName(), mycountries.get(i));
			}
		}
	}

	public int getIndex(Country country, List<Country> countries) {
		for (int i = 0; i < countries.size(); i++) {
			if (country.getName().equals(countries.get(i).getName())) {
				return i;
			}
		}
		return -1;
	}
}
