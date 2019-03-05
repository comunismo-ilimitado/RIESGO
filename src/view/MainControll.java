package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import controller.AttackController;
import controller.FortificationController;
import controller.ReadingFiles;
import controller.ReinforcementController;
import model.Country;
import model.Player;

public class MainControll {
	ReadingFiles files;
	MFrame frame;
	MFrame2 frame2;
	Player player;
	MyActionListner my_action_listner;
	AttackController attack_controller;
	AttackerButtons attacker_buttons;
	ReinforcementController reinforcement_controller;
	FortificationController fortification_controller;

	public void function() throws Exception {
		try {
			frame2=new MFrame2();
			files = new ReadingFiles(frame2);
			
			String address = "Resources/World.map";
			if (StartUpWindow.MapType == 1)
				address = "Resources/" + MapSelection.getSelectedMap() + ".map";
			else if (StartUpWindow.MapType == 2)
				address = "Resources/LoadedMap.map";
			else if (StartUpWindow.MapType == 3)
				address = "Resources/UserMap.map";
			System.out.print("Selected Map : " + address);
			files.Reads(address);
			my_action_listner = new MyActionListner(this);
			frame = new MFrame(my_action_listner, ReadingFiles.image);
			reinforcement_controller = new ReinforcementController();
			attack_controller = new AttackController();
			fortification_controller = new FortificationController();
			frame.fun();
			setButtons();
			paintCountries();

			my_action_listner.reinforcementPhase();
			repaintAndRevalidate();

		} catch (Exception e) {
			System.out.println("ERROR IN MAP Reading. Cant Use This Map File. Please Restart \n" + e);
			frame2.error("ERROR IN MAP Reading. Cant Use This Map File. Please Restart \n" + e);
		}
	}

	public void addArmies(int armies) {
		onlyNeeded(neighbours(armies));

	}

	public void setButtons() throws IOException {
		frame.SetButtons(countryObjects());
	}

	public void refreshButtons() throws IOException {
		frame.Refresh(countryObjects());
		paintCountries();
		repaintAndRevalidate();
	}

	public List<String> countriesNames() {
		return ReadingFiles.CountriesNames;
	}

	public void repaintAndRevalidate() {
		frame.revalidate();
		frame.repaint();
	}

	public List<Country> neighbours(Integer id) {
		return files.playerId.get(id).getTotalCountriesOccupied();
	}

	public HashMap<String, Country> countryObjects() {
		return ReadingFiles.CountryNameObject;
	}

	public void paintCountries() {
		frame.SetColorToAll(countryObjects());
	}

	public void onlyNeeded(List<Country> country) {
		frame.OnlyNeeded(country);
	}

	public int playerNo() {
		return files.playerId.size();
	}

	public Player playerObjet(int id) {
		return files.playerId.get(id);
	}

	public String neighboursList(Country country) {
		List<Country> countrylist = country.getNeighbors();
		String result = "";
		for (int i = 0; i < countrylist.size(); i++) {
			result = result.concat(countrylist.get(i).getName() + ",");
		}
		return result;
	}

	public void changePlayerCountry(String countryname) throws IOException {
		Country country = countryObjects().get(countryname);
		country.setPlayer(files.playerId.get(0));
		refreshButtons();

	}

}
