package controller.editor;

import model.Continent;
import model.Country;
import model.Player;
import view.gameFrames.BoardController;
import view.gameFrames.MFrame2;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class reads the map files
 *
 * @author pazim
 * @version 1.1
 */
public class ReadingFiles {
    private static HashMap<Integer, Player> playerId;
    private static HashMap<Integer, Player> playerId2;
    private boolean errors = false;
    private static List<Integer> players;
    private static List<String> CountriesNames;
    private static List<String> ContinentNames;
    private static HashMap<String, Country> CountryNameObject;
    private static HashMap<String, Continent> ContinentNameObject;
    private static String address = "Resources/World.map";
    private static String image = "noimage.bmp";
    private static int ArmiesPerPlayer;
    BoardController board;
    public ReadingFiles(BoardController controller) {
        this.board = controller;
    }

    /**
     * This method will store all values from the map file in static variables
     *
     * @param address: File location of the map file
     * @param noofplayers: Number of players
     * @throws IOException Error in reading
     */
    public void Reads(String address, int noofplayers) throws IOException {
        try {
            setCountryNameObject(new HashMap<>());
            setCountriesNames(new ArrayList<>());
            setContinentNames(new ArrayList<>());
            setContinentNameObject(new HashMap<>());

            // Reading Country File
            ReadingFiles.setAddress(address);
            FileReader file = new FileReader(address);
            BufferedReader bufferedReader = new BufferedReader(file);
            String temp;
            StringBuffer buffer = new StringBuffer();
            while ((temp = bufferedReader.readLine()) != null) {
                buffer.append(temp).append("\n");
            }
            bufferedReader.close();
            String string = "\\[.*]";
            String[] aaa = buffer.toString().trim().replaceAll("\n+", "\n").split(string);
            String InfoString = aaa[1].trim();
            String[] findInfo = InfoString.split("\n");
            for (int i = 0; i < InfoString.length(); i++) {
                String[] temp1 = findInfo[i].split("=");
                if (temp1[0].trim().equals("image")) {
                    setImage(temp1[1].trim());
                    break;
                }
            }

            String ContinentsString = aaa[2].trim();
            String CountriesString = aaa[3].trim();
            String[] tempContinentArray = ContinentsString.split("\n");

            for (String s : tempContinentArray) {
                String temporary = s.split("=")[0].trim().toLowerCase();
                int value = Integer.parseInt(s.split("=")[1].trim());
                getContinentNames().add(temporary);
                getContinentNameObject().put(temporary, new Continent(value, temporary));
            }
            String[] tempCountryArray = CountriesString.split("\n");
            for (String s : tempCountryArray) {
                String a = s.split(",")[0].trim();
                getCountriesNames().add(a);
                getCountryNameObject().put(a, new Country(a));
            }
            for (String s : tempCountryArray) {
                String[] a = s.split(",");
                Country temp1 = getCountryNameObject().get(a[0].trim());
                Continent temp2 = getContinentNameObject().get(a[3].trim().toLowerCase());
                temp1.setContinent(temp2);
                temp2.addCountry(temp1);
                for (int j = 4; j < a.length; j++) {
                    temp1.addNeighbors(getCountryNameObject().get(a[j].trim()));
                }
            }

            List<String> CountriesNames2 = new ArrayList<>(getCountryNameObject().keySet());
            Collections.shuffle(CountriesNames2);

            setPlayers(new ArrayList<>());
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
                player.setPlayerColor(arrayListc.get(i % (arrayListc.size()-1)));
                playerId.put(i, player);
                getPlayers().add(i);
            }

            // TODO create a better randomizer function
            for (int i = 0; i < CountriesNames2.size(); i++) {
                for (int j = 0; j < noofplayers; j++) {
                    if ((i + j) < CountriesNames2.size()) {
                        Country temp1 = getCountryNameObject().get(CountriesNames2.get(i + j));
                        Player tempPlayer = playerId.get(j);
                        temp1.setPlayer(tempPlayer);
                        tempPlayer.addCountriesOccupied(temp1);
                    }
                }
                i = i + noofplayers - 1;
            }

            setArmiesPerPlayer(50 - (5 * noofplayers));

            for (int i = 0; i < noofplayers; i++) {
                Player temp1 = playerId.get(getPlayers().get(i));
                List<Country> clis = temp1.getTotalCountriesOccupied();
                int playersize = clis.size();
                int count = 0;
                for (int j = 0; j < getArmiesPerPlayer(); j++) {
                    if (count >= playersize)
                        count = 0;
                    clis.get(count).setNoOfArmies(clis.get(count).getNoOfArmies() + 1);
                    count++;

                }
            }
            playerId2 = (HashMap<Integer, Player>) playerId.clone();

        } catch (Exception e) {
            e.printStackTrace();
            errors = true;
            System.out.println("ERROR IN MAP READING \n" + e);
            board.boardError("Error In MAP READING START AGAIN\n" + e);
        }

    }

    //Getters and setters
    public static HashMap<Integer, Player> getPlayerId() {
        return playerId;
    }
    public static void setPlayerId(HashMap<Integer, Player> playerId) {
        ReadingFiles.playerId = playerId;
    }
    public static HashMap<Integer, Player> getPlayerId2() {
        return playerId2;
    }
    public boolean isErrors() {
        return errors;
    }
    public static List<Integer> getPlayers() {
        return players;
    }
    public static void setPlayers(List<Integer> players) {
        ReadingFiles.players = players;
    }
    public static List<String> getCountriesNames() {
        return CountriesNames;
    }
    public static void setCountriesNames(List<String> countriesNames) {
        CountriesNames = countriesNames;
    }
    public static List<String> getContinentNames() {
        return ContinentNames;
    }
    public static void setContinentNames(List<String> continentNames) {
        ContinentNames = continentNames;
    }
    public static HashMap<String, Country> getCountryNameObject() {
        return CountryNameObject;
    }
    public static void setCountryNameObject(HashMap<String, Country> countryNameObject) {
        CountryNameObject = countryNameObject;
    }
    public static HashMap<String, Continent> getContinentNameObject() {
        return ContinentNameObject;
    }
    public static void setContinentNameObject(HashMap<String, Continent> continentNameObject) {
        ContinentNameObject = continentNameObject;
    }
    public static String getAddress() {
        return address;
    }
    public static void setAddress(String address) {
        ReadingFiles.address = address;
    }
    public static String getImage() {
        return image;
    }
    public static void setImage(String image) {
        ReadingFiles.image = image;
    }
    public static int getArmiesPerPlayer() {
        return ArmiesPerPlayer;
    }
    public static void setArmiesPerPlayer(int armiesPerPlayer) {
        ArmiesPerPlayer = armiesPerPlayer;
    }
}

