package controller;

import view.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.CardTypes;
import model.Continent;
import model.Country;
import model.Player;
import view.MFrame;
import view.MFrame2;


/**
 * start up phase of the game
 * @author pazim
 * @version 1.1
 */
public class MainController {
	ReadingFiles files;
	MFrame frame;
	MFrame2 frame2;
	String phase;
	Player player;
	MyActionListner myactionlistner;
	AttackController attackController;
	ReinforcementController reinforcementController;
	FortificationController fortificationController;
	MapValidation mapValidation;
	public boolean resume = false;

	@SuppressWarnings("deprecation")
	public void Function() throws Exception {
		try {
			frame2 = new MFrame2();
			files = new ReadingFiles(frame2);

			FileReader fileReader;
			BufferedReader bufferedReader = null;
			File tempFile = new File("Resources/SaveGame.txt");
			boolean exists = tempFile.exists();
			if (exists) {
				fileReader = new FileReader("Resources/SaveGame.txt");
				bufferedReader = new BufferedReader(fileReader);
			}
			if (resume) {
				files.Reads(bufferedReader.readLine(), Integer.parseInt(bufferedReader.readLine()));
			} else {
				String address = "Resources/World.map";
				if (SelectMapType.MapType == 1)
					address = "Resources/" + SelectMap.getSingleModeSelectedMap() + ".map";
				else if (SelectMapType.MapType == 2)
					address = "Resources/LoadedMap.map";
				else if (SelectMapType.MapType == 3)
					address = "Resources/UserMap.map";
				if (SelectMapType.MapType < 4) {
					files.Reads(address, SelectNoOfPlayers.NumberOfPlayers);
				}
			}
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
				if (resume) {
					int no = Integer.parseInt(bufferedReader.readLine());
					myactionlistner.currentPlayer = no;
					phase = bufferedReader.readLine();
				}
				frame.fun();
				//temp();
				if (resume)
					LoadSavedGame(bufferedReader);

				for (int i = 0; i < ReadingFiles.playerId2.size(); i++) {
					ReadingFiles.playerId2.get(i).setStratergy(SelectPlayerStrategies.getStrategies().get(i));
				}
				SetButtons();
				PaintCountries();
				SetDominationView();
				if (resume) {
					myactionlistner.PhaseResume(phase);
				} else {
					myactionlistner.ReinforcementPhase();
				}
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
	 * this method loads the saved game
	 * @param bufferedReader
	 */
	public void LoadSavedGame(BufferedReader bufferedReader) {
		try {
			String temp = "";
			String Everything = "";
			while ((temp = bufferedReader.readLine()) != null) {
				Everything = Everything + temp + "\n";
			}
			files.playerId2.clear();
			SelectPlayerStrategies.strategy_selected.clear();
			String[] PlayersLis = Everything.trim().split("----PLAYER----");
			frame.area.setText(PlayersLis[0]);
			for (int i = 1; i < PlayersLis.length; i++) {
				String[] cards = PlayersLis[i].trim().split("----CARDS----");
				String[] countryandarmies = cards[0].trim().split("\n");
				Player tempPlayer = files.playerId.get(Integer.parseInt(countryandarmies[0]));
				files.playerId2.put(Integer.parseInt(countryandarmies[0]), tempPlayer);
				tempPlayer.setStratergy(countryandarmies[1]);

				SelectPlayerStrategies.strategy_selected.add(countryandarmies[1]);
				System.out.println(tempPlayer);
				tempPlayer.ClearArmies();
				for (int j = 2; j < countryandarmies.length; j++) {
					String[] country = countryandarmies[j].trim().split("\\*\\*\\*");
					Country tempCountry = files.CountryNameObject.get(country[0].trim());
					tempPlayer.addCountriesOccupied(tempCountry);

					tempCountry.setNoOfArmies(Integer.parseInt(country[1]));

					tempCountry.setPlayer(tempPlayer);
				}
				String[] arrlis = cards[1].substring(2, cards[1].length() - 1).split(",");
				ArrayList<CardTypes> arrayList = new ArrayList<>();

				for (int k = 0; k < arrlis.length; k++) {
					if (arrlis[k].trim().equals(""))
						break;
					arrayList.add(CardTypes.valueOf(arrlis[k].trim()));
				}
				// player.setPlayerCards(arrayList);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 *
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
		return ReadingFiles.playerId2.size();
	}

	/**
	 * Player object
	 *
	 * @param id:
	 *            id of the player
	 * @return
	 */
	public Player playerObjet(int id) {
		return files.playerId.get(id);
	}

	/**
	 * Gives list of neighbors
	 *
	 * @param country:
	 *            country whose neighbor list you want
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
	 * @param countryname:
	 *            name of the country
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
			// System.out.println(player);
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
	 * @param list:
	 *            list of continents
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
	 * @param list:
	 *            list of countries
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
		playerObjet(0).setPlayerCards(cardTypes);
	}

}
