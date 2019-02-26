import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class reads the text files
 * @author Navjot kaur
 * @version 1.0.0
 */

public class ReadingFiles {
//	public static HashMap<String, Player> playerObject;
	public static HashMap<Integer, Player> playerId;
	public static ArrayList<Integer> players;
	public static ArrayList<String> CountriesNames, ContinentNames; // List of countries Strings
	public static HashMap<String, Country> CountryNameObject;
	public static HashMap<String, Continent> ContinentNameObject; // HashMAp COntaining Country name as // // key and
	// returns country object
	
	/**
	 * 
	 * @throws IOException
	 */

	public static void Reads() throws IOException {
		CountryNameObject = new HashMap<>();
		CountriesNames = new ArrayList<>();
		ContinentNames = new ArrayList<>();
		ContinentNameObject = new HashMap<>();
		// Reading Country File
		FileReader file = new FileReader("Resources/Asia.map");
		BufferedReader bufferedReader = new BufferedReader(file);
		String temp = "";
		StringBuffer buffer = new StringBuffer();
		while ((temp = bufferedReader.readLine()) != null) {
			buffer.append(temp + "\n");
		}
		bufferedReader.close();
		String string = "\\[.*]";
		String[] aaa = buffer.toString().trim().replaceAll("\n+", "\n").split(string);
		String InfoString = aaa[1].trim();
		String ContinentsString = aaa[2].trim();
		String CountriesString = aaa[3].trim();
		String[] tempInfoArray = ContinentsString.split("\n");

		for (int i = 0; i < tempInfoArray.length; i++) {
			String temporary = tempInfoArray[i].split("=")[0].trim().toUpperCase();
			int value = Integer.parseInt(tempInfoArray[i].split("=")[1].trim());
			ContinentNames.add(temporary);
			ContinentNameObject.put(temporary, new Continent(value, temporary));
		}
		String[] tempCountryArray = CountriesString.split("\n");
		for (int i = 0; i < tempCountryArray.length; i++) {
			String a = tempCountryArray[i].split(",")[0].trim();
			CountriesNames.add(a);
			CountryNameObject.put(a, new Country(a));
		}
		System.out.println(CountriesString);
		for (int i = 0; i < tempCountryArray.length; i++) {
		String[] a = tempCountryArray[i].split(",");
		System.out.println(a[3]);
		System.out.println(a[0]);


		Country temp1 = CountryNameObject.get(a[0].trim());
			Continent temp2 = ContinentNameObject.get(a[3].trim().toUpperCase());

			temp2.addCountrie(temp1);
			for (int j = 4; j < a.length; j++) {
				temp1.addNeighbors(CountryNameObject.get(a[j].trim()));
			}
		}
		ArrayList<String> CountriesNames2 =  (ArrayList<String>) CountriesNames.clone();
		Collections.shuffle(CountriesNames2);

//		playerObject = new HashMap<>();
		players=new ArrayList<>();
		playerId = new HashMap<>();
		ArrayList<Color> arrayListc = new ArrayList<>();
		arrayListc.add(Color.cyan);
		arrayListc.add(Color.GREEN);
		arrayListc.add(Color.YELLOW);

		for (int i = 0; i < 3; i++) {
			Player player = new Player(i);
			player.setPlayerColor(arrayListc.get(i));
			playerId.put(i, player);
			players.add(i);
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

	}
}