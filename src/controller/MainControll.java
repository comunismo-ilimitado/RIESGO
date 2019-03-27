package controller;

import view.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.CardTypes;
import model.Continent;
import model.Country;
import model.Player;
import view.AttackerButtons;
import view.MFrame;
import view.MFrame2;

public class MainControll {
	ReadingFiles files;
	MFrame frame;
	MFrame2 frame2;
	Player player;
	MyActionListner myactionlistner;
	AttackController attackController;
	AttackerButtons attackerButtons;
	ReinforcementController reinforcementController;
	FortificationController fortificationController;
	MapValidation mapValidation;

	@SuppressWarnings("deprecation")
	public void Function() throws Exception {
		try {
			frame2 = new MFrame2();
			files = new ReadingFiles(frame2);
			String address = "Resources/World.map";
			if (StartUpWindow.MapType == 1)
				address = "Resources/" + MapSelection.getSelectedMap() + ".map";
			else if (StartUpWindow.MapType == 2)
				address = "Resources/LoadedMap.map";
			else if (StartUpWindow.MapType == 3)
				address = "Resources/UserMap.map";
//			System.out.print("Selected Map : " + address);
			files.Reads(address);

			mapValidation = new MapValidation(files.CountryNameObject, files.ContinentNameObject);
			mapValidation.CallAllMethods();
			if (!files.errors && !mapValidation.error) {
				myactionlistner = new MyActionListner(this);
				frame = new MFrame(myactionlistner, ReadingFiles.image);
				reinforcementController = new ReinforcementController();
				attackController = new AttackController();
				fortificationController = new FortificationController();
				frame.noArmiesLeft = playerObjet(0).getPlayerArmiesNotDeployed();

				myactionlistner.addObserver(frame);
				frame.fun();
				SetButtons();
				PaintCountries();

				SetDominationView();
				for (Continent val : files.ContinentNameObject.values()) {
					System.out.println(val.getName() + " : -" + ListToStringCountries(val.getCountries()));
				}
				myactionlistner.ReinforcementPhase();
				repaintAndRevalidate();
			}
		} catch (Exception e) {
			System.out.println("ERROR IN MAP Reading. Cant Use This Map File. Please Restart \n" + e);
			e.printStackTrace();
			frame2.error("ERROR IN MAP Reading. Cant Use This Map File. Please Restart \n" + e);
		}
	}

	/**
	 * Set domination View
	 */
	public void SetDominationView() {
		frame.SetDominationView(files.players.size());
		updateDominationView();

	}

	/**
	 * Update changes in the domination view
	 */
	public void updateDominationView() {
		frame.UpdateGameDominationViewPercentage(CountriesPercentage());
		frame.UpdateGameDominationViewContinentOccupied(ContinentsOccupied());

	}

	/**
	 * Add armies 
	 * @param armies
	 */
	public void AddArmies(int armies) {
		OnlyNeeded(neighbours(armies));

	}

	/**
	 * Set Button of country object
	 * 
	 * @throws IOException
	 */
	public void SetButtons() throws IOException {
		frame.SetButtons(countryObjects());
	}

	/**
	 * Refresh
	 * 
	 * @throws IOException
	 */
	public void RefreshButtons() throws IOException {
		frame.Refresh(countryObjects());
		PaintCountries();
		repaintAndRevalidate();
	}

	/**
	 * List of country names
	 * 
	 * @return
	 */
	public List<String> countriesNames() {
		return ReadingFiles.CountriesNames;
	}

	/**
	 * Calls repaint and re-validate
	 */
	public void repaintAndRevalidate() {
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Total countries occupied
	 * 
	 * @param id
	 * @return
	 */
	public List<Country> neighbours(Integer id) {
		return files.playerId.get(id).getTotalCountriesOccupied();
	}

	/**
	 * Country object
	 * 
	 * @return
	 */
	public HashMap<String, Country> countryObjects() {
		return ReadingFiles.CountryNameObject;
	}

	/**
	 * Give color to all countries
	 */
	public void PaintCountries() {
		frame.SetColorToAll(countryObjects());
	}

	/**
	 * Frame for countries that are to be viewed
	 * 
	 * @param country
	 */
	public void OnlyNeeded(List<Country> country) {
		frame.OnlyNeeded(country);
	}

	/**
	 * Player Number
	 * 
	 * @return
	 */
	public int PlayerNo() {
		return files.playerId.size();
	}
	public int PlayerNo2() {
		return files.playerId2.size();
	}
	

	/**
	 * Player object
	 * 
	 * @param id: id of the player
	 * @return
	 */
	public Player playerObjet(int id) {
		return files.playerId.get(id);
	}

	/**
	 * Gives list of neighbors
	 * 
	 * @param country: country whose neighbor list you want
	 * @return result: string of neighbors
	 */
	public String NeighboursList(Country country) {
		List<Country> countrylist = country.getNeighbors();
		String result = "";
		for (int i = 0; i < countrylist.size(); i++) {
			result = result.concat(countrylist.get(i).getName() + ",");
		}
		return result;
	}

	/**
	 * Changes player of the country
	 * 
	 * @param countryname: name of the country
	 * @throws IOException
	 */
	public void ChangePlayerCountry(String countryname) throws IOException {
		Country country = countryObjects().get(countryname);
		country.setPlayer(files.playerId.get(0));
		RefreshButtons();

	}

	/**
	 * Calculates Size of each country button
	 * 
	 * @param pl
	 * @return value in float
	 */
	public float calculations(Player pl) {
		try {
			float total = files.CountryNameObject.size();
//			System.out.println(player);
			float player_have = pl.getTotalCountriesOccupied().size();
			return (player_have / total) * 100;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Giving values to each country button
	 * 
	 * @return
	 */
	public ArrayList<Float> CountriesPercentage() {
		ArrayList<Float> arrayList = new ArrayList<>();
		for (int i = 0; i < PlayerNo(); i++) {
			// System.out.println(playerObjet(i));
			arrayList.add(calculations(playerObjet(i)));
		}
		return arrayList;
	}

	/**
	 * Converts user input into map file
	 * 
	 * @param list: list of continents
	 * @return occu: String of continents
	 */
	public String ListToStringContinent(List<Continent> list) {
		String occu = "";
		try {
			for (int i = 0; i < list.size(); i++) {
				occu = occu + ", " + list.get(i).getName();
			}
			if (occu == "") {
				occu = "No Continents";
			}
			return occu;
		} catch (Exception e) {
			e.printStackTrace();
			return "None";
		}
	}

	/**
	 * Converts user input into map file
	 * 
	 * @param list: list of countries
	 * @return occu: String of countries
	 */
	public String ListToStringCountries(List<Country> list) {
		String occu = "";
		try {
			for (int i = 0; i < list.size(); i++) {
				occu = occu + ", " + list.get(i).getName();
			}
			return occu;
		} catch (Exception e) {
			e.printStackTrace();
			return "NONE";
		}
	}

	/**
	 * list of countries that are occupied
	 * 
	 * @return list
	 */
	public ArrayList<String> ContinentsOccupied() {
		ArrayList<String> arrayList = new ArrayList<>();
		for (int i = 0; i < PlayerNo(); i++) {
			arrayList.add(ListToStringContinent(reinforcementController.playerOwnsContinent(playerObjet(i))));
		}
		return arrayList;
	}

	public void temp() {
		List<CardTypes> cardTypes = new ArrayList<>();
		cardTypes.add(CardTypes.Artillery);
		cardTypes.add(CardTypes.Artillery);
		cardTypes.add(CardTypes.Artillery);
		cardTypes.add(CardTypes.Infantry);
		cardTypes.add(CardTypes.Infantry);
		cardTypes.add(CardTypes.Infantry);
		cardTypes.add(CardTypes.Infantry);
		cardTypes.add(CardTypes.Cavalry);
		cardTypes.add(CardTypes.Cavalry);
		cardTypes.add(CardTypes.Cavalry);
		cardTypes.add(CardTypes.Cavalry);
		cardTypes.add(CardTypes.Cavalry);
		cardTypes.add(CardTypes.Cavalry);
		playerObjet(0).setPlayerCards(cardTypes);
	}

}
