import java.util.*;





/**
 * This class contains all the information about any particular country
 * @author Team 1
 * @version 1.0.0
 */
public class Country {

	
	/**
	 * CountryId This contains unique id of the country of type integer
	 */
	private int CountryId;
	
	//Continent which it belongs to
	private Continent Continent;
	
	/**
	 * ContinentId This contains unique id of the continent of type integer
	 */
	private int ContinentId;

	
	/**
	 * OwnedPlayer This contains name of the player who owns this country of type Player
	 */
	private Player OwnedPlayer;
	
	
	/**
	 * Name This is the name of the country of type String
	 */
	private String Name;
	
	
	/**
	 * Neighbors This is the list of the neighboring countries of this country
	 */
	private List<Country> Neighbors;
	
	
	/**
	 * NoOfArmies This contains the number of armies this country has
	 */
	private int NoOfArmies;
	
	
	/**
	 * Color This represents the color of the country that will be shown on the UI
	 */
	private String Color;
	
	/**
	 * Gets country color
	 * @return Color This is the color of this country of type String
	 */
	public String getColor() {
		return Color;
	}
				
	/**
	 * Sets country color
	 * @param Color color of this country of type String
	 */
	public void setId(String Color) {
		this.Color = Color;
	}
	
	
	/**
	 * Get the id of the country
	 * @return CountryId Id of the country of type Integer 
	 */
		public int getCountryId() {
			return CountryId;
		}
		
		
		/**
		 * Set the id of the country
		 * @param CountryId Id of the country of type Integer
		 */
		public void setCountryId(int CountryId) {
			this.CountryId=CountryId;
		}
		
		//Gets Continent ID
		public Continent getContinent() {
			return Continent;
		}
			
		//Set Continent ID
		public void setContinent(Continent Continent) {
			this.Continent=Continent;
		}	
		/**
		 * Get the id of the continent
		 * @return ContinentId Id of the continent of type Integer
		 */
		public int getContinentId() {
			return ContinentId;
		}
			
		
		/**
		 * Set the id of the continent
		 * @param ContinentId Id of the continent of type Integer
		 */
		public void setContinentId(int ContinentId) {
			this.ContinentId=ContinentId;
}
		
		
		/**
		 * Get the owner of the country
		 * @return OwnedPlayer It returns the object of the Player type
		 */
				public Player getOwner() {
					return OwnedPlayer;
				}
					
				
				/**
				 * Set the owner of the country
				 * @param Player It is the object of the Player type
				 */
				public void setPlayer(Player Player) {
					this.OwnedPlayer=Player;
				}
				
				
				/**
				 * Get the name of the country
				 * @return Name It is the name of the country of type String
				 */
				public String getName() {
					return Name;
				}
					
				
				/**
				 * Set the name of the country
				 * @param Name It is the name of the country of type String
				 */
				public void setName(String Name) {
					this.Name=Name;
				}
				
				
				/**
				 * Get the list of the neighbor countries
				 * @return Neighbors It returns the list of the neighbor countries
				 */
				public List<Country> getNeighbors() {
					return Neighbors;
				}

				
				/**
				 * Set the neighbors list of this country
				 * @param Neighbors It takes the list of the neighbor countries
				 */ 
				public void setNeighbors(List<Country> Neighbors) {
					this.Neighbors = Neighbors;
				}
				
				/**
				 * This method add the neighbor countries to the list of neighbors
				 * @param Neighbor It takes the object of type Country
				 */
				public void addNeighbors(Country Neighbor) {
					this.Neighbors.add(Neighbor);
				}
				
				
				/**
				 * This method is used to get the number of armies in this country
				 * @return NoOfArmies It is of type Integer
				 */
				public int getNoOfArmies() {
					return NoOfArmies;
				}
				
				
				/**
				 * This method is used to set the number of armies in this country
				 * @param NoOfArmies It is of type Integer
				 */
				public void setNoOfArmies(int NoOfArmies) {
					this.NoOfArmies=NoOfArmies;
				}
	
				/**
				 * This constructor initializes the data fields 
				 * @param name It takes the name of the country of type String
				 */
			public Country(String name) {
				this.NoOfArmies = 0;
				this.Neighbors = new ArrayList<>();
				this.Name=name;
				this.OwnedPlayer=new Player(0);
				this.Color="grey";
			}

			
}
