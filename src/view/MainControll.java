package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import controller.AttackController;
import controller.FortificationController;
import controller.ReadingFiles;
import controller.ReinforcementController;
import model.Country;
import model.MapVarification;
import model.Player;

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
	MapVarification mapVarification;

	public void Function() throws Exception {
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
			mapVarification=new MapVarification(files.CountryNameObject,files.ContinentNameObject);
			mapVarification.CallAllMethods();
			if(!files.errors&&!mapVarification.error) {
				
			myactionlistner = new MyActionListner(this);
			frame = new MFrame(myactionlistner, ReadingFiles.image);
			reinforcementController = new ReinforcementController();
			attackController = new AttackController();
			fortificationController = new FortificationController();
			frame.fun();
			SetButtons();
			PaintCountries();

			myactionlistner.ReinforcementPhase();
			repaintAndRevalidate();
			}
		} catch (Exception e) {
			System.out.println("ERROR IN MAP Reading. Cant Use This Map File. Please Restart \n" + e);
			frame2.error("ERROR IN MAP Reading. Cant Use This Map File. Please Restart \n" + e);
		}
	}

	public void AddArmies(int armies) {
		OnlyNeeded(neighbours(armies));

	}

	public void SetButtons() throws IOException {
		frame.SetButtons(countryObjects());
	}

	public void RefreshButtons() throws IOException {
		frame.Refresh(countryObjects());
		PaintCountries();
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

	public void PaintCountries() {
		frame.SetColorToAll(countryObjects());
	}

	public void OnlyNeeded(List<Country> country) {
		frame.OnlyNeeded(country);
	}

	public int PlayerNo() {
		return files.playerId.size();
	}

	public Player playerObjet(int id) {
		return files.playerId.get(id);
	}

	public String NeighboursList(Country country) {
		List<Country> countrylist = country.getNeighbors();
		String result = "";
		for (int i = 0; i < countrylist.size(); i++) {
			result = result.concat(countrylist.get(i).getName() + ",");
		}
		return result;
	}

	public void ChangePlayerCountry(String countryname) throws IOException {
		Country country = countryObjects().get(countryname);
		country.setPlayer(files.playerId.get(0));
		RefreshButtons();

	}

}
