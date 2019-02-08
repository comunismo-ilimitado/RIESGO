import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ReadingFiles {
	String temp = "";
	HashMap<Integer, String> CountryIdName, ContinentIdName;
	HashMap<String, Player> playerObject;
	HashMap<Integer, Player> playerId;

	ArrayList<String> CountriesNames, ContinentNames; // List of countries Strings

	HashMap<String, Country> CountryNameObject, ContinentNameObject; // HashMAp COntaining Country name as key and
																		// returns country object

	public void Reads() throws IOException {
		CountryIdName = new HashMap<>();
		CountryNameObject = new HashMap<>();
		CountriesNames = new ArrayList<>();
		ContinentNames = new ArrayList<>();
		ContinentNameObject = new HashMap<>();
		ContinentIdName = new HashMap<>();

		// Reading Country File
		FileReader file = new FileReader("Resources/countries.txt");
		BufferedReader bufferedReader = new BufferedReader(file);
		while ((temp = bufferedReader.readLine()) != null) {
			String[] a = temp.split(":");
			CountryIdName.put(Integer.parseInt(a[0]), a[1]);
			CountriesNames.add(a[1]);
			CountryNameObject.put(a[1], new Country(Integer.parseInt(a[0]), a[1]));
		}
		bufferedReader.close();
		// Connection Between Countries
		FileReader file2 = new FileReader("Resources/connections.txt");
		BufferedReader bufferedReader2 = new BufferedReader(file2);
		while ((temp = bufferedReader2.readLine()) != null) {
			String[] neighbours1 = temp.split(":");
			String[] neighbours2 = neighbours1[1].split(",");
			Country mainCountry = CountryNameObject.get(CountryIdName.get(Integer.parseInt(neighbours1[0])));
			for (int i = 0; i < neighbours2.length; i++) {
				mainCountry.addNeighbors(CountryNameObject.get(CountryIdName.get(Integer.parseInt(neighbours2[i]))));
			}
		}
		bufferedReader2.close();

		// ContinentFile
		StringBuffer  buffer=new StringBuffer();
		FileReader file3 = new FileReader("Resources/continents.txt");
		BufferedReader bufferedReader3 = new BufferedReader(file3);
		System.out.println(bufferedReader3.toString());
		while ((temp = bufferedReader3.readLine()) != null) {
			buffer.append(temp);
		}
		bufferedReader3.close();
		System.out.println(buffer);

	
		@SuppressWarnings("unchecked")
		ArrayList<String> CountriesNames2 =  (ArrayList<String>) CountriesNames.clone();
		Collections.shuffle(CountriesNames2);

		// Temporary
		playerObject = new HashMap<>();
		playerId = new HashMap<>();
		ArrayList<Color> arrayListc = new ArrayList<>();
		arrayListc.add(Color.cyan);
		arrayListc.add(Color.GREEN);
		arrayListc.add(Color.YELLOW);

		for (int i = 0; i < 3; i++) {
			Player player = new Player(i);
			player.setPlayerColor(arrayListc.get(i));
			playerId.put(i, player);
		}
		try {
			for (int i = 0; i < CountriesNames2.size(); i++) {
				playerId.get(0).addCountriesOccupied(CountryNameObject.get(CountriesNames2.get(i)));
				playerId.get(1).addCountriesOccupied(CountryNameObject.get(CountriesNames2.get(i + 1)));
				playerId.get(2).addCountriesOccupied(CountryNameObject.get(CountriesNames2.get(i + 2)));
				i = i + 2;
			}
		} catch (Exception e) {
		}

		/*
		 * List<Country> xyz = CountryNameObject.get("Ukraine").getNeighbors(); for(int
		 * i=0;i<xyz.size();i++) { System.out.println(xyz.get(i).getName()); }
		 */
	}

}
