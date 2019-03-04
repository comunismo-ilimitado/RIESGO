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
import view.AssignCountries;

public class ReadingFiles {
	public static HashMap<Integer, Player> PlayerId;
	public static List<Integer> Players;
	public static List<String> CountriesNames, ContinentNames;
	public static HashMap<String, Country> CountryNameObject;
	public static HashMap<String, Continent> ContinentNameObject;
	public static String adress = "Resources/World.map";
	public static String image = "noimage.bmp";

	public static void Reads(String adress) {
		try {
			CountryNameObject = new HashMap<>();
			CountriesNames = new ArrayList<>();
			ContinentNames = new ArrayList<>();
			ContinentNameObject = new HashMap<>(); 
			FileReader file = new FileReader(adress);
			BufferedReader bufferedreader = new BufferedReader(file);
			String temp = "";
			StringBuffer buffer = new StringBuffer();
			while ((temp = bufferedreader.readLine()) != null) {
				buffer.append(temp + "\n");
			}
			bufferedreader.close();
			String string = "\\[.*]";
			String[] dividedstring = buffer.toString().trim().replaceAll("\n+", "\n").split(string);
			String infostring = dividedstring[1].trim();
			String[] findinfo = infostring.split("\n");
			for (int i = 0; i < infostring.length(); i++) {
				String[] temparray = findinfo[i].split("=");
				if (temparray[0].trim().equals("image")) {
					image = temparray[1].trim();
					break;
				}
			}

			String continentsstring = dividedstring[2].trim();
			String countriesstring = dividedstring[3].trim();
			String[] tempcontinentarray = continentsstring.split("\n");

			for (int i = 0; i < tempcontinentarray.length; i++) {
				String temporary = tempcontinentarray[i].split("=")[0].trim().toLowerCase();
				int value = Integer.parseInt(tempcontinentarray[i].split("=")[1].trim());
				ContinentNames.add(temporary);
				ContinentNameObject.put(temporary, new Continent(value, temporary));
			}

			String[] tempcountryarray = countriesstring.split("\n");
			for (int i = 0; i < tempcountryarray.length; i++) {
				String a = tempcountryarray[i].split(",")[0].trim();
				CountriesNames.add(a);
				CountryNameObject.put(a, new Country(a));
			}
			for (int i = 0; i < tempcountryarray.length; i++) {
				String[] a = tempcountryarray[i].split(",");
				Country tempcountry = CountryNameObject.get(a[0].trim());
				Continent tempcontinent = ContinentNameObject.get(a[3].trim().toLowerCase());
				tempcountry.setContinent(tempcontinent);
				tempcontinent.addCountrie(tempcountry);
				for (int j = 4; j < a.length; j++) {
					tempcountry.addNeighbors(CountryNameObject.get(a[j].trim()));
				}
			}

			List<String> countriesnameslist = new ArrayList<String>(CountryNameObject.keySet());
			Collections.shuffle(countriesnameslist);
			Players = new ArrayList<>();
			PlayerId = new HashMap<>();
			List<Color> colorslist = new ArrayList<>();
			colorslist.add(Color.cyan);
			colorslist.add(Color.GREEN);
			colorslist.add(Color.YELLOW);
			colorslist.add(Color.ORANGE);
			colorslist.add(Color.decode("#33cccc"));
			colorslist.add(Color.PINK);

			int noofplayers = AssignCountries.NumberOfPlayers;

			for (int i = 0; i < noofplayers; i++) {
				Player player = new Player(i);
				player.setPlayerColor(colorslist.get(i));
				PlayerId.put(i, player);
				Players.add(i);
			}
			try {
				int n = noofplayers;
				for (int i = 0; i < countriesnameslist.size(); i++) {
					for (int j = 0; j < noofplayers; j++) {
						Country tempcountry = CountryNameObject.get(countriesnameslist.get(i + j));
						Player tempplayer = PlayerId.get(j);
						tempcountry.setPlayer(tempplayer);
						tempplayer.addCountriesOccupied(tempcountry);
					}
					i = i + n - 1;
				}
			} catch (Exception e) {
			}
			int armiesperplayer = 50 - (5 * noofplayers);
			for (int i = 0; i < noofplayers; i++) {
				Player currentplayer = PlayerId.get(Players.get(i));
				List<Country> clist = currentplayer.getTotalCountriesOccupied();
				int playersize = clist.size();
				int count = 0;
				for (int j = 0; j < armiesperplayer; j++) {
					if (count >= playersize)
						count = 0;
					clist.get(count).setNoOfArmies(clist.get(count).getNoOfArmies() + 1);
					count++;

				}

			}
		} catch (Exception e) {
			System.out.println("ERROR IN MAP");
		}
	}
}
