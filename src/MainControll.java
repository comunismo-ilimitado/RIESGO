import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainControll {
	ReadingFiles files;
	MFrame frame;
	Player player;
	Reinforcement reinforcement;

	public MainControll() throws IOException {

	}

	public void Function() throws IOException {
		files = new ReadingFiles();
		reinforcement = new Reinforcement(this);

		frame = new MFrame(reinforcement);
		files.Reads();
		frame.fun();
		SetButtons();
		PaintCountries();
		repaintAndRevalidate();
	}

	public void SetButtons() throws IOException {
		frame.SetButtons(countryObjects());
	}

	public void RefreshButtons() throws IOException {
		frame.Refresh(countryObjects());
		PaintCountries();
		repaintAndRevalidate();

	}

	public ArrayList<String> countriesNames() {
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
		/*
		 * for (int i = 0; i < files.players.size(); i++) {
		 * frame.SetColorToAll(neighbours(i), files.playerId.get(i)); }
		 */
	}

}
