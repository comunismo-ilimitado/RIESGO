package model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;

public class MapVarification {
	ArrayList<String> arrayList = new ArrayList<>();
	HashMap<String, Country> hashMap;
	HashMap<String, Continent> hashMap2;

	public MapVarification(HashMap<String, Country> hashMap, HashMap<String, Continent> hashMap2) {
		this.hashMap = hashMap;
		this.hashMap2 = hashMap2;
	}

	public void BiDirectionalCheck() {

		for (int i = 0; i < hashMap.size(); i++) {
			Country temp = hashMap.get(hashMap.keySet().toArray()[i]);
			for (int j = 0; j < temp.getNeighbors().size(); j++) {
				if(!temp.getNeighbors().get(j).getNeighbors().contains(temp)) {
					temp.getNeighbors().get(j).getNeighbors().add(temp);
					arrayList.add("BI-Directional Error --Repaired");
				}

			}

		}

	}
}
