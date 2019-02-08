import java.util.*;


//Represents a country
public class Country {

	//Country ID
	private int CountryId;
	
	//Continent which it belongs to
	private int ContinentId;

	//Country occupied by
	private Player OwnedPlayer;
	
	//Country's name
	private String Name;
	
	//List of a country's neighbors
	private List<Country> Neighbors;
	
	//Number of armies deployed in the country
	private int NoOfArmies;
	
	//Color to represent player owned countries
	private String Color;
	
	//Gets country color
	public String getColor() {
		return Color;
	}
				
	//Sets country color
	public void setId(String Color) {
		this.Color = Color;
	}
	
	//Gets Country ID
		public int getCountryId() {
			return CountryId;
		}
		
		//Set Country ID
		public void setCountryId(int CountryId) {
			this.CountryId=CountryId;
		}
		
		//Gets Continent ID
		public int getContinentId() {
			return ContinentId;
		}
			
		//Set Continent ID
		public void setContinentId(int ContinentId) {
			this.ContinentId=ContinentId;
		}
		
		//Gets Owned Player
				public Player getPlayer() {
					return OwnedPlayer;
				}
					
				//Sets Owned Player
				public void setPlayer(Player Player) {
					this.OwnedPlayer=Player;
				}
				
				//Gets Country Name
				public String getName() {
					return Name;
				}
					
				//Sets Country Name
				public void setName(String Name) {
					this.Name=Name;
				}
				
				//Gets a country's neighbors
				public List<Country> getNeighbors() {
					return Neighbors;
				}

				//Sets Country's Neighbors
				public void setNeighbors(List<Country> Neighbors) {
					this.Neighbors = Neighbors;
				}
				public void addNeighbors(Country Neighbor) {
					this.Neighbors.add(Neighbor);
				}
				
				//Gets armies
				public int getNoOfArmies() {
					return NoOfArmies;
				}
				
				//Sets armies
				public void setNoOfArmies(int NoOfArmies) {
					this.NoOfArmies=NoOfArmies;
				}
	//Default Constructor
			public Country(int CountryId,String name) {
				this.CountryId = CountryId;
				this.NoOfArmies = 0;
				this.Neighbors = new ArrayList<>();
				this.Name=name;
				this.OwnedPlayer=new Player(0);
				this.ContinentId=0;
				this.Color="grey";
			}
}
