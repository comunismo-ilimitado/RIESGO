import java.util.*;

//Represents a player in the game
public class Player {
	
		//Player Name
		private String Name;
		
		//Player ID
		private int PlayerId;
		
		//Total number of armies a player has
		private int TotalArmies;
		
		//Armies that are not assigned to any country
		private int TotalArmiesNotDeployed;
		
		//Total number of armies a player owns
		private List<Country> TotalCountriesOccupied;
		
		//Number of Continents occupied
		private List<Continent> ContinentsOccupied;
		
		//The cards that a player holds
		private List<CardTypes> Cards;
		
		//Color to represent player owned countries
		private String MyColor;
		
		
		//Default Constructor
		public Player(int playerId) {
			this.PlayerId = PlayerId;
			this.TotalCountriesOccupied = new ArrayList<>();
			this.Cards = new ArrayList<>();
		}
	
		//Gets PlayerID
		public int getPlayerId() {
			return PlayerId;
		}
	
		//Sets PlayerID
		public void setPlayerId(int PlayerId) {
			this.PlayerId = PlayerId;
		}

	
		//Gets Player Name
		public String getPlayerName() {
			return Name;
		}

		//Sets Player Name
		public void setPlayerName(String Name) {
			this.Name = Name;
		}
		
		//Gets Player Armies
		public int getPlayerArmies() {
			return TotalArmies;
		}

		//Sets Player Armies
		public void setPlayerArmies(int TotalArmies) {
			this.TotalArmies = TotalArmies;
		}
		
		//Gets Player Armies that are not deployed
		public int getPlayerArmiesNotDeployed() {
			return TotalArmiesNotDeployed;
		}

		//Sets Player Armies that are not deployed
		public void setPlayerTotalArmiesNotDeployed(int TotalArmiesNotDeployed) {
			this.TotalArmiesNotDeployed = TotalArmiesNotDeployed;
		}
				
		//Gets Countries Occupied
		public List<Country> getTotalCountriesOccupied() {
			return TotalCountriesOccupied;
		}

		//Sets Countries Occupied
		public void setTotalCountriesOccupied(List<Country> TotalCountriesOccupied) {
			this.TotalCountriesOccupied = TotalCountriesOccupied;
		}
		
		//Gets Continents Occupied
		public List<Continent> getContinentsOccupied() {
			return ContinentsOccupied;
		}
		//Sets Continents Occupied
		public void setContinentsOccupied(List<Continent> ContinentsOccupied) {
			this.ContinentsOccupied = ContinentsOccupied;
		}
		//Gets Player Cards
		public List<CardTypes> getPlayerCards() {
			return Cards;
		}

		//Sets Player Cards
		public void setPlayerCards(List<CardTypes> PlayerCards) {
			this.Cards = PlayerCards;
		}
		
		//Gets player color
		public String getPlayerColor() {
			return MyColor;
		}
			
		//Sets player color
		public void setPlayerColor(String MyColor) {
			this.MyColor = MyColor;
		}			
}
