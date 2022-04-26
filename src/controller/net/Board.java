package controller.net;

import model.Continent;
import model.Country;
import model.Player;

import java.util.HashMap;

public class Board extends NetPackages.Package {

    private HashMap<String, Country> countries;
    private HashMap<String, Continent> continents;
    private HashMap<Integer, Player> players;

    private static HashMap<Integer, ErrorMessage> errors;


    public HashMap<String, Continent> getContinents() {
        return continents;
    }

    public HashMap<String, Country> getCountries() {
        return countries;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public HashMap<Integer, ErrorMessage> getErrors() {
        return errors;
    }

    public static class ErrorMessage{
        String text;
        Player player;
        int id = 0;

        public ErrorMessage(String errortext, Player player){
            this.text = errortext;
            this.player = player;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
