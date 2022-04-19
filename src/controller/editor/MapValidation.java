package controller.editor;

import model.Continent;
import model.Country;
import view.gameFrames.MFrame2;

import java.util.*;


/**
 * Class used for precessing the integrity of country graph connectivity. Tries to solve
 * for common problems such as Graph Connectivity and Neighbourhood bidirectionality
 *
 * @author pazim
 */

public class MapValidation {
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    HashMap<String, Country> countryHashMap;
    HashMap<String, Continent> continentHashMap;
    private boolean error = false;
    public boolean isError() {
        return error;
    }
    /**
     * Parameterized constructor
     *
     * @param hashMap country hashmap
     * @param hashMap2 continent hashmap
     * @author Pazim
     */
    public MapValidation(HashMap<String, Country> hashMap, HashMap<String, Continent> hashMap2) {
        this.countryHashMap = hashMap;
        this.continentHashMap = hashMap2;
    }

    /**
     * Checks if graph is functional in two ways
     */
    public void BiDirectionalCheck() {

        try {
            for (int i = 0; i < countryHashMap.size(); i++) {
                Country temp = countryHashMap.get(countryHashMap.keySet().toArray()[i]);
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
        ArrayList<Continent> temparrayList = new ArrayList<>(continentHashMap.values());

        for (int i = 0; i < countryHashMap.size(); i++) {

            Country temp = countryHashMap.get(countryHashMap.keySet().toArray()[i]);
            for (int j = 0; j < temp.getNeighbors().size(); j++) {

                if (continentHashMap.containsValue(temp.getContinent())) {
                    temparrayList.remove(temp.getContinent());
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
        for (int i = 0; i < countryHashMap.size(); i++) {
            Country temp = countryHashMap.get(countryHashMap.keySet().toArray()[i]);
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
        if (countryHashMap.size() < 1 || continentHashMap.size() < 1) {
            arrayList.add("NO COUNTRY OR CONTINENT");
        }
    }

    /**
     * No two continents can have same country
     */
    public void ContinentHaveSameCountry() {
        for (int i = 0; i < continentHashMap.size(); i++) {
            Continent temp = continentHashMap.get(continentHashMap.keySet().toArray()[i]);
            for (int j = 0; j < continentHashMap.size(); j++) {
                Continent temp2 = continentHashMap.get(continentHashMap.keySet().toArray()[j]);
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
        for (int i = 0; i < countryHashMap.size(); i++) {
            Country temp = countryHashMap.get(countryHashMap.keySet().toArray()[i]);
            if (temp.getNeighbors().size() <= 0) {
                arrayList.add("No Neighbours - Unresolver");
            }
        }
    }

    /**
     * checks graph is connected or not
     */
    public void GraphConnectivity() {
        Country start = countryHashMap.get(countryHashMap.keySet().toArray()[0]);
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
        if (visited.size() != countryHashMap.size()) {
            arrayList.add("Error Connectivity Graph");
        }
    }

    public void ContinentConectivity() {
        Country start = countryHashMap.get(countryHashMap.keySet().toArray()[0]);
        LinkedList<Country> nexttovisit = new LinkedList<Country>();
        HashSet<Country> visited = new HashSet<Country>();
        HashSet<Continent> vis = new HashSet<>();
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
        if (vis.size() != continentHashMap.size()) {
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
