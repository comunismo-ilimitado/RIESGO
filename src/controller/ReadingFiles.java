package controller;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import model.Continent;
import model.Country;
import model.Player;
import view.SelectNoOfPlayers;
import view.MFrame;
import view.MFrame2;

/**
 * This class reads the map files
 * 
 * @author pazim
 * @version 1.1
 */
public class ReadingFiles {

	public static HashMap<Integer, Player> playerId;

	public static HashMap<Integer, Player> playerId2;
public static boolean errors = false;
	public static List<Integer> players;
	public static List<String> CountriesNames, ContinentNames;
	public static HashMap<String, Country> CountryNameObject;
	public static HashMap<String, Continent> ContinentNameObject;
	public static String address = "Resources/World.map";
	public static String image = "noimage.bmp";
	MFrame2 frame2;
	public static int ArmiesPerPlayer;

	public ReadingFiles(MFrame2 frame2) {
		this.frame2 = frame2;
	}

	/**
	 * This method will store all values from the map file in static variables
	 * 
	 * @param address:
	 *            location of the map file
	 * @throws IOException
	 */
	public void Reads(String address,int noofplayers) throws IOException {
		try {
			CountryNameObject = new HashMap<>();
			CountriesNames = new ArrayList<>();
			ContinentNames = new ArrayList<>();
			ContinentNameObject = new HashMap<>();
			// Reading Country File
			this.address=address;
			FileReader file = new FileReader(address);
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
			String[] findInfo = InfoString.split("\n");
			for (int i = 0; i < InfoString.length(); i++) {
				String[] temp1 = findInfo[i].split("=");
				if (temp1[0].trim().equals("image")) {
					image = temp1[1].trim();
					break;
				}
			}

			String ContinentsString = aaa[2].trim();
			String CountriesString = aaa[3].trim();
			String[] tempContinentArray = ContinentsString.split("\n");

			for (int i = 0; i < tempContinentArray.length; i++) {
				String temporary = tempContinentArray[i].split("=")[0].trim().toLowerCase();
				int value = Integer.parseInt(tempContinentArray[i].split("=")[1].trim());
				ContinentNames.add(temporary);
				ContinentNameObject.put(temporary, new Continent(value, temporary));
			}
			String[] tempCountryArray = CountriesString.split("\n");
			for (int i = 0; i < tempCountryArray.length; i++) {
				String a = tempCountryArray[i].split(",")[0].trim();
				CountriesNames.add(a);
				CountryNameObject.put(a, new Country(a));
			}
			for (int i = 0; i < tempCountryArray.length; i++) {
				String[] a = tempCountryArray[i].split(",");
				Country temp1 = CountryNameObject.get(a[0].trim());
				Continent temp2 = ContinentNameObject.get(a[3].trim().toLowerCase());
				temp1.setContinent(temp2);
				temp2.addCountrie(temp1);
				for (int j = 4; j < a.length; j++) {
					temp1.addNeighbors(CountryNameObject.get(a[j].trim()));
				}
			}

			List<String> CountriesNames2 = new ArrayList<String>(CountryNameObject.keySet());
			Collections.shuffle(CountriesNames2);

			players = new ArrayList<>();
			playerId = new HashMap<>();
			List<Color> arrayListc = new ArrayList<>();
			arrayListc.add(Color.cyan);
			arrayListc.add(Color.GREEN);
			arrayListc.add(Color.WHITE);
			arrayListc.add(Color.PINK);
			arrayListc.add(Color.decode("#ffff00"));
			arrayListc.add(Color.decode("#FF6600"));


			for (int i = 0; i < noofplayers; i++) {
				Player player = new Player(i);
				player.setPlayerColor(arrayListc.get(i));
				playerId.put(i, player);
				players.add(i);
			}
			int n = noofplayers;
			for (int i = 0; i < CountriesNames2.size(); i++) {
				for (int j = 0; j < noofplayers; j++) {
					if ((i + j) < CountriesNames2.size()) {
						Country temp1 = CountryNameObject.get(CountriesNames2.get(i + j));
						Player tempPlayer = playerId.get(j);
						temp1.setPlayer(tempPlayer);
						tempPlayer.addCountriesOccupied(temp1);
					}
				}
				i = i + n - 1;
			}

			ArmiesPerPlayer = 50 - (5 * noofplayers);

			for (int i = 0; i < noofplayers; i++) {
				Player temp1 = playerId.get(players.get(i));
				List<Country> clis = temp1.getTotalCountriesOccupied();
				int playersize = clis.size();
				int count = 0;
				for (int j = 0; j < ArmiesPerPlayer; j++) {
					if (count >= playersize)
						count = 0;
					clis.get(count).setNoOfArmies(clis.get(count).getNoOfArmies() + 1);
					count++;

				}

			}
			playerId2=(HashMap<Integer, Player>) playerId.clone();
			
		} catch (Exception e) {
			e.printStackTrace();
			errors = true;
			System.out.println("ERROR IN MAP READING \n" + e);
			frame2.error("Error In MAP READING START AGAIN\n" + e);
		}

	}
}
