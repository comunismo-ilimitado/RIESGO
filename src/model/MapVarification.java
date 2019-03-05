package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import view.MFrame2;


public class MapVarification {
	ArrayList<String> arrayList = new ArrayList<>();
	ArrayList<String> arrayList2 = new ArrayList<>();
	HashMap<String, Country> hashMap;
	HashMap<String, Continent> hashMap2;
	public static boolean error=false;

	/**
	 * 
	 * @param hashMap
	 * @param hashMap2
	 * @author Pazim
	 */
	public MapVarification(HashMap<String, Country> hashMap, HashMap<String, Continent> hashMap2) {
		this.hashMap = hashMap;
		this.hashMap2 = hashMap2;
	
	}

	public void BiDirectionalCheck() {

		for (int i = 0; i < hashMap.size(); i++) {
			Country temp = hashMap.get(hashMap.keySet().toArray()[i]);
			System.out.println(temp.getName());
			for (int j = 0; j < temp.getNeighbors().size(); j++) {
				if (!temp.getNeighbors().get(j).getNeighbors().contains(temp)) {
					temp.getNeighbors().get(j).getNeighbors().add(temp);
					arrayList2.add("BI-Directional Error --Repaired");
				}

			}

		}

	}

	public void NoCountryIsUnused() {

	}

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

	public void NotItsOwnNeighbour() {
		for (int i = 0; i < hashMap.size(); i++) {
			Country temp = hashMap.get(hashMap.keySet().toArray()[i]);
			if (temp.getNeighbors().contains(temp)) {
				temp.getNeighbors().remove(temp);
				arrayList2.add("Neighbour Of itself Removed");
			}

		}
	}

	public void NoContinentOrCountry() {
		if (hashMap.size() < 1 || hashMap2.size() < 1) {
			arrayList.add("NO COUNTRY OR CONTINENT");
		}

	}

	public void ContinentHaveSameCountry() {
		for (int i = 0; i < hashMap2.size(); i++) {
			Continent temp = hashMap2.get(hashMap2.keySet().toArray()[i]);
			for (int j = 0; j < hashMap2.size(); j++) {
				Continent temp2 = hashMap2.get(hashMap2.keySet().toArray()[j]);
				if(i!=j||!temp.equals(temp2)) {
				if (temp.getCountries().containsAll(temp2.getCountries())) {
					arrayList.add("MULTIPLE CONTINENTS HAVE SAME COUNTRIES");
				}}

			}
		}

	}

	public void EmptyNeighbours() {
		for (int i = 0; i < hashMap.size(); i++) {
			Country temp = hashMap.get(hashMap.keySet().toArray()[i]);
			if (temp.getNeighbors().size() <= 0) {
				arrayList.add("No Neighbours - Unresolver");
			}
		}
	}

	public void GraphConnectivity() {

	}

	private void checkConnectedGraph(Country country, Set<Country> queue, Continent continent) {

		for (int i = 0; i < country.getNeighbors().size(); i++) {

			Country neighbouringTerritory = country.getNeighbors().get(i);
			if (continent == null && !queue.contains(neighbouringTerritory)) {
				queue.add(neighbouringTerritory);
				checkConnectedGraph(neighbouringTerritory, queue, continent);
			} else if (!queue.contains(neighbouringTerritory)
					&& neighbouringTerritory.getContinent().getName() == continent.getName()
					&& neighbouringTerritory.getNeighbors().size() != 0) {
				queue.add(neighbouringTerritory);
				checkConnectedGraph(neighbouringTerritory, queue, continent);
			}
		}

	}

	public void CallAllMethods() {
//		BiDirectionalCheck();
		EmptyNeighbours();
		ContinentHaveSameCountry();
		NoContinentIsUnused();
		NoCountryIsUnused();
		NoContinentOrCountry();
		NotItsOwnNeighbour();
//		GraphConnectivity();
/*		Set<Country> countries = new HashSet<Country>();
		checkConnectedGraph(hashMap.get(hashMap.keySet().toArray()[0]), countries,
				hashMap2.get(hashMap2.keySet().toArray()[0]));
*/
		if(arrayList.size()>0) {
			System.out.println(arrayList);
			MFrame2 frame2=new MFrame2();
			error=true;
			frame2.error("Error in Map File Start Again");
		}
	}

}