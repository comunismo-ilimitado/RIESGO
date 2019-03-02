import java.util.*;

/**
 * Fortification COntroller has all the methods required during fortification
 * phase of the game
 * 
 * @author bhargav
 * @version 1.0.0
 */
public class FortificationController {
	/**
	 * Gets a list of countries that the player owns
	 * 
	 * @param player:
	 *            Player object must be given to fetch the countries
	 * @return List of countries owned by the player
	 */
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

	/**
	 * List of Country object nodes of the garph
	 */
	private List<Node> nodeLookUp = new ArrayList<Node>();

	/**
	 * Nested Node class for constructing the graph
	 * 
	 * @author bhargav
	 */
	public class Node {
		private Country country;
		LinkedList<Node> adjacent = new LinkedList<Node>();

		private Node(Country country) {
			this.country = country;
		}
	}

	/**
	 * To find the node of a given country object in the graph
	 * 
	 * @param source:
	 *            Country object that needs to be found in the graph
	 * @return The node of the given country object will be returned
	 */
	private Node getNode(Country source) {
		for (int i = 0; i < nodeLookUp.size(); i++) {
			if (nodeLookUp.get(i).country.equals(source))
				return nodeLookUp.get(i);
			else
				continue;
		}
		return nodeLookUp.get(-1);
	}

	/**
	 * Adds an edge in the graph between two countries
	 * 
	 * @param source:
	 *            The source country object
	 * @param destination:
	 *            The destination country object
	 */
	public void addEdge(Country source, Country destination) {
		Node s = getNode(source);
		Node d = getNode(destination);
		s.adjacent.add(d);
	}

	/**
	 * Method to find if there is path between two countries to move armies during
	 * fortification
	 * 
	 * @param source:
	 *            Source Country object
	 * @param destination:
	 *            Destination Country object
	 * @return
	 */
	public boolean hasPathBFS(Node source, Node destination) {
		LinkedList<Node> nextToVisit = new LinkedList<Node>();
		HashSet<Country> visited = new HashSet<Country>();
		nextToVisit.add(source);
		while (!nextToVisit.isEmpty()) {
			Node node = nextToVisit.remove();
			if (node.equals(destination)) {
				return true;
			}
			if (visited.contains(node.country))
				continue;
			visited.add(node.country);
			for (Node child : node.adjacent) {
				if (child.country.getOwner().equals(source.country.getOwner())) {
					nextToVisit.add(child);
				}
			}
		}
		return false;
	}

	public boolean hasPathBFS2(Country source, Country destination) {
		LinkedList<Country> nextToVisit = new LinkedList<Country>();
		HashSet<Country> visited = new HashSet<Country>();
		nextToVisit.add(source);
		while (!nextToVisit.isEmpty()) {
			Country node = nextToVisit.remove();
			if (node.equals(destination)) {
				return true;
			}
			if (visited.contains(source))
				continue;
			visited.add(source);
			for (Country child : source.getNeighbors()) {
				if (child.getOwner().equals(source.getOwner())) {
					nextToVisit.add(child);
				}
			}
		}
		return false;
	}

	/**
	 * Creates nodes for country object and adds them to the list of nodes
	 */
	public void addNodes() {
		for (Map.Entry<String, Country> m : ReadingFiles.CountryNameObject.entrySet()) {
			nodeLookUp.add(new Node(m.getValue()));
		}
	}

	/**
	 * Adds edges bet ween countries based on the user given neighbors of each
	 * country
	 */
	public void addAllEdge() {
		for (int i = 0; i < nodeLookUp.size(); i++) {
			List<Country> neighbors = nodeLookUp.get(i).country.getNeighbors();
			for (int j = 0; j < neighbors.size(); i++) {
				addEdge(nodeLookUp.get(i).country, neighbors.get(j));
			}
		}
	}

	public String moveArmies(Country sourceCountry, Country destinationCountry, int noOfArmiesToBeMoved) {
		if (sourceCountry.getNoOfArmies() < 2) {
			// display user that he cannot move armies from this country
			// call getmycountries method again
			return "less army";
		} else if (noOfArmiesToBeMoved >= sourceCountry.getNoOfArmies()) {
			int CanMove = sourceCountry.getNoOfArmies() - 1;
			// display user that he can move only 'CanMove' no.of armies
			// call getmycountries method again
			System.out.println(CanMove);
			return "ksaodjpos";
		} /*
			 * else if (!hasPathBFS(getNode(sourceCountry), getNode(destinationCountry))) {
			 * // display user that he cannot move armies to this country because there is
			 * no // path // call getmycountries method again System.out.println("NO path");
			 */
		else if (!hasPathBFS2(sourceCountry, destinationCountry)) {
			// display user that he cannot move armies to this country because there is no
			// path
			// call getmycountries method again
			return "NO path";

		} else {
			sourceCountry.setNoOfArmies(sourceCountry.getNoOfArmies() - noOfArmiesToBeMoved);
			destinationCountry.setNoOfArmies(destinationCountry.getNoOfArmies() + noOfArmiesToBeMoved);
			// display to user that the armies are updated and move to next player
			// reinforcement phase
			return "";
			// endFortificationPhaseButton();
		}

	}

	/**
	 * Ends fortification phase of current player and will call the reinforcement
	 * phase of the next player
	 */
	public void endFortificationPhaseButton() {
		// move to next player reinforcement phase
	}
}
