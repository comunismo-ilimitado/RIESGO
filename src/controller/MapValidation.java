package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.Continent;
import model.Country;
import view.MFrame2;


/**
 * this class validates the map
 * @author pazim
 *
 */

public class MapValidation {
	ArrayList<String> arrayList = new ArrayList<>();
	ArrayList<String> arrayList2 = new ArrayList<>();
	HashMap<String, Country> hashMap;
	HashMap<String, Continent> hashMap2;
	public static boolean error = false;

	/**
	 * Parameterized constructor
	 * 
	 * @param hashMap
	 * @param hashMap2
	 * @author Pazim
	 */
	public MapValidation(HashMap<String, Country> hashMap, HashMap<String, Continent> hashMap2) {
		this.hashMap = hashMap;
		this.hashMap2 = hashMap2;

	}

	/**
	 * Checks if graph is functional in two ways
	 */
	public void BiDirectionalCheck() {

		try {
			for (int i = 0; i < hashMap.size(); i++) {
				Country temp = hashMap.get(hashMap.keySet().toArray()[i]);
				int x = temp.getNeighbors().size();
				for (int j = 0; j < x; j++) {
					Country a = temp.getNeighbors().get(j);
					List<Country> b = a.getNeighbors();
					if (!b.contains(temp)) {
						temp.getNeighbors().get(j).getNeighbors().add(temp);
						arrayList2.add("BI-Directional Error --Repaired");
					}

				}

			}
		} catch (Exception e) {
			arrayList.add("BI-Directional Error --NotRepaired");

		}

	}

	/**
	 * Gets list
	 * 
	 * @return arrayList
	 */
	public ArrayList<String> getString() {
		return arrayList;
	}

	/**
	 * Gets list2
	 * 
	 * @return arrayList2
	 */
	public ArrayList<String> getString2() {
		return arrayList2;
	}

	/**
	 * continent must not be empty
	 */
	public void NoContinentIsUnused() {
		ArrayList<Continent> temparrayList = new ArrayList<>(hashMap2.values());

		for (int i = 0; i < hashMap.size(); i++) {

			Country temp = hashMap.get(hashMap.keySet().toArray()[i]);
			for (int j = 0; j < temp.getNeighbors().size(); j++) {

				if (hashMap2.containsValue(temp.getContinent())) {
					if (temparrayList.contains(temp.getContinent())) {
						temparrayList.remove(temp.getContinent());
					}
				} else {
					arrayList.add("Continent not Found in Continent Object");
				}
			}
		}
		if (temparrayList.size() > 0) {
			arrayList.add("EVERY CONTINENT IS NOT USED");
		}

	}

	/**
	 * country should not be its own neighbor
	 */
	public void NotItsOwnNeighbour() {
		for (int i = 0; i < hashMap.size(); i++) {
			Country temp = hashMap.get(hashMap.keySet().toArray()[i]);
			if (temp.getNeighbors().contains(temp)) {
				temp.getNeighbors().remove(temp);
				arrayList2.add("Neighbour Of itself Removed");
			}

		}
	}

	/**
	 * checks if no country or continent in the map file
	 */
	public void NoContinentOrCountry() {
		if (hashMap.size() < 1 || hashMap2.size() < 1) {
			arrayList.add("NO COUNTRY OR CONTINENT");
		}

	}

	/**
	 * No two continents can have same country
	 */
	public void ContinentHaveSameCountry() {
		for (int i = 0; i < hashMap2.size(); i++) {
			Continent temp = hashMap2.get(hashMap2.keySet().toArray()[i]);
			for (int j = 0; j < hashMap2.size(); j++) {
				Continent temp2 = hashMap2.get(hashMap2.keySet().toArray()[j]);
				if (i != j || !temp.equals(temp2)) {
					if (temp.getCountries().containsAll(temp2.getCountries())) {
						arrayList.add("MULTIPLE CONTINENTS HAVE SAME COUNTRIES");
					}
				}

			}
		}

	}

	/**
	 * if country doesn't have neighbors
	 */
	public void EmptyNeighbours() {
		for (int i = 0; i < hashMap.size(); i++) {
			Country temp = hashMap.get(hashMap.keySet().toArray()[i]);
			if (temp.getNeighbors().size() <= 0) {
				arrayList.add("No Neighbours - Unresolver");
			}
		}
	}

	/**
	 * checks graph is connected or not
	 */
	public void GraphConnectivity() {
		Country start = hashMap.get(hashMap.keySet().toArray()[0]);
		LinkedList<Country> nexttovisit = new LinkedList<Country>();
		HashSet<Country> visited = new HashSet<Country>();
		nexttovisit.add(start);

		while (!nexttovisit.isEmpty()) {
			Country node = nexttovisit.remove();
			if (visited.contains(node))
				continue;
			visited.add(node);
			for (Country child : node.getNeighbors()) {
				nexttovisit.add(child);

			}
		}
		if(visited.size()!=hashMap.size()) {
			arrayList.add("Error Connectivity Graph");
		}
		
	}

	public void ContinentConectivity() {
		Country start = hashMap.get(hashMap.keySet().toArray()[0]);
		LinkedList<Country> nexttovisit = new LinkedList<Country>();
		HashSet<Country> visited = new HashSet<Country>();
		HashSet<Continent> vis=new HashSet<>();
		nexttovisit.add(start);

		while (!nexttovisit.isEmpty()) {
			Country node = nexttovisit.remove();
			if (visited.contains(node))
				continue;
			visited.add(node);
			vis.add(node.getContinent());
			for (Country child : node.getNeighbors()) {
				nexttovisit.add(child);
				

			}
		}
		if(vis.size()!=hashMap2.size()) {
			arrayList.add("Error Connectivity Graph");
		}

	}

	public void CallAllMethods() {
		GraphConnectivity();
		ContinentConectivity();
		BiDirectionalCheck();
		EmptyNeighbours();
		ContinentHaveSameCountry();
		NoContinentIsUnused();
		NoContinentOrCountry();
		NotItsOwnNeighbour();
		/*
		 * Set<Country> countries = new HashSet<Country>();
		 * checkConnectedGraph(hashMap.get(hashMap.keySet().toArray()[0]), countries,
		 * hashMap2.get(hashMap2.keySet().toArray()[0]));
		 */
		if (arrayList.size() > 0) {
			System.out.println(arrayList);
			MFrame2 frame2 = new MFrame2();
			error = true;
			frame2.error("Error in Map File Start Again");
		}
	}

}
