import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ReadingFiles {
	String temp = "";
	HashMap<Integer, String> countrieNumber;
	HashMap<String, Country> countryObjects;
	public void Reads() throws IOException {
		countrieNumber = new HashMap<>();
		countryObjects = new HashMap<>();
		FileReader file = new FileReader("Resources/countries.txt");
		BufferedReader bufferedReader = new BufferedReader(file);
		while ((temp = bufferedReader.readLine()) != null) {
			String[] a = temp.split(":");
			countrieNumber.put(Integer.parseInt(a[0]), a[1]);
			countryObjects.put(a[1], new Country(Integer.parseInt(a[0]),a[1]));
		}
		System.out.println(countrieNumber.size());
		FileReader file2 = new FileReader("Resources/connections.txt");
		BufferedReader bufferedReader2 = new BufferedReader(file2);
		while ((temp = bufferedReader2.readLine()) != null) {
			String[] neighbours1 = temp.split(":");
			String[] neighbours2 = neighbours1[1].split(",");
			Country mainCountry = countryObjects.get(countrieNumber.get(Integer.parseInt(neighbours1[0])));
			for (int i = 0; i < neighbours2.length; i++) {
				mainCountry.addNeighbors(countryObjects.get(countrieNumber.get(Integer.parseInt(neighbours2[i]))));
			}
		}

	List<Country> xyz = countryObjects.get("Ukraine").getNeighbors();
	for(int i=0;i<xyz.size();i++) {
		System.out.println(xyz.get(i).getName());
	}
	}
	
}
