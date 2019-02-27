package view;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
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
	Player player;
	MyActionListner myActionListner;
	AttackController attackController;
	ReinforcementController reinforcementController;
	FortificationController fortificationController;

	public MainControll() throws IOException {

	}

	public void Function() throws IOException {
		files = new ReadingFiles();
		reinforcementController=new ReinforcementController();
		myActionListner = new MyActionListner(this);
		files.Reads();
		frame = new MFrame(myActionListner,files.image);
		attackController = new AttackController();
		fortificationController=new FortificationController();
		frame.fun();

		SetButtons();
		// OnlyNeeded( attackController.getMyCountries(files.playerId.get(0)));
		PaintCountries();
		
		myActionListner.ReinforcementPhase();
		repaintAndRevalidate();
	}

	public void AddArmies(int no) {
			OnlyNeeded(neighbours(no));
		
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
		return files.CountriesNames;
	}

	public void repaintAndRevalidate() {
		frame.revalidate();
		frame.repaint();
	}

	public List<Country> neighbours(Integer id) {
		return files.playerId.get(id).getTotalCountriesOccupied();
	}

	public HashMap<String, Country> countryObjects() {
		return files.CountryNameObject;
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
		List<Country> lis = country.getNeighbors();
		String haha="";
		for(int i=0;i<lis.size();i++) {
			haha=haha.concat(lis.get(i).getName()+",");
		}
		return haha;
	}

	public void ChangePlayerCountry(String Cname) throws IOException {
		Country country = countryObjects().get(Cname);
		country.setPlayer(files.playerId.get(0));
		RefreshButtons();

	}

}
