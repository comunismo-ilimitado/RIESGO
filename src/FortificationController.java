import java.util.*;

public class FortificationController { 
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
		private List<Node> nodeLookUp= new ArrayList<Node>();
		public class Node{
			private Country country;
			LinkedList<Node> adjacent=new LinkedList<Node>();
			private Node(Country country)
			{
				this.country=country;
			}
		}
		private Node getNode(Country source) {
			for(int i=0;i<nodeLookUp.size();i++)
			{
				if(nodeLookUp.get(i).country.equals(source))
					return nodeLookUp.get(i);
				else continue;
			}
			return nodeLookUp.get(-1);
		}
		public void addEdge(Country source, Country destination) {
			Node s=getNode(source);
			Node d=getNode(destination);
			s.adjacent.add(d);
		}
		public boolean hasPathBFS(Node source,Node destination) {
			LinkedList<Node> nextToVisit = new LinkedList<Node>();
			HashSet<Country> visited = new HashSet<Country>();
			nextToVisit.add(source);
			while(!nextToVisit.isEmpty()) {
				Node node=nextToVisit.remove();
				if(node.equals(destination))
				{
					return true;
				}
				if(visited.contains(node.country))
					continue;
				visited.add(node.country);
				for(Node child:node.adjacent) {
					if(child.country.getOwner().equals(source.country.getOwner()))
					{
						nextToVisit.add(child);
					}
				}
			}
			return false;
		}
		public void addNodes() {
			for(Map.Entry m:ReadingFiles.CountryNameObject.entrySet())
			{
				nodeLookUp.add(new Node((Country) m.getValue()));
			}
		}
		public void addEdges() {
			for(int i=0;i<nodeLookUp.size();i++)
			{
				List<Country> neighbors=nodeLookUp.get(i).country.getNeighbors();
				for(int j=0;j<neighbors.size();i++)
				{
					addEdge(nodeLookUp.get(i).country, neighbors.get(j));
				}
			}
		}
		public void moveArmies(Country sourceCountry, Country destinationCountry, int noOfArmiesToBeMoved) {
			if(sourceCountry.getNoOfArmies()<2){
				//display user that he cannot move armies from this country
				//call getmycountries method again
			}
			else if(noOfArmiesToBeMoved >= sourceCountry.getNoOfArmies()) {
				int CanMove = sourceCountry.getNoOfArmies()-1;
				//display user that he can move only 'CanMove' no.of armies
				//call getmycountries method again
			}
			else if(!hasPathBFS(getNode(sourceCountry),getNode(destinationCountry))) {
				//display user that he cannot move armies to this country because there is no path
				//call getmycountries method again
			}
			else {
				sourceCountry.setNoOfArmies(sourceCountry.getNoOfArmies()-noOfArmiesToBeMoved);
				destinationCountry.setNoOfArmies(destinationCountry.getNoOfArmies()+noOfArmiesToBeMoved);
				//display to user that the armies are updated and move to next player reinforcement phase
			}
		}
		public void endFortificationPhaseButton() {
			//move to next player reinforcement phase
		}
}

