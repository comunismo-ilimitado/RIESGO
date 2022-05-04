package controller.net;

import model.CardTypes;
import model.Continent;
import model.Country;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board extends NetPackages.Package {

    private String mapName;

    private HashMap<String, Country> countries;
    private HashMap<String, Continent> continents;
    private HashMap<Integer, Player> players;

    private List<CardTypes> cardTypesList;

    private static HashMap<Integer, ErrorMessage> errors;

    private static HashMap<Integer, ClientUpdate.ClientAction> actions;

    private static HashMap<Integer, String> serverInfo;


    public Board(){
        countries = new HashMap<>();
        continents = new HashMap<>();
        players = new HashMap<>();
        errors = new HashMap<>();
        actions = new HashMap<>();
        cardTypesList = new ArrayList<>();
    }

    public HashMap<String, Continent> getContinents() {
        return continents;
    }

    public HashMap<String, Country> getCountries() {
        return countries;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public void setContinents(HashMap<String, Continent> continents) {
        this.continents = continents;
    }

    public void setCountries(HashMap<String, Country> countries) {
        this.countries = countries;
    }

    public void setPlayers(HashMap<Integer, Player> players) {
        this.players = players;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }

    public HashMap<Integer, ErrorMessage> getErrors() {
        return errors;
    }

    public HashMap<Integer, ClientUpdate.ClientAction> getActions() {
        return actions;
    }

    public void setCardTypesList(List<CardTypes> cardTypesList) {
        this.cardTypesList = cardTypesList;
    }

    public List<CardTypes> getCardTypesList() {
        return cardTypesList;
    }

    public static HashMap<Integer, String> getServerInfo() {
        return serverInfo;
    }

    public static void setServerInfo(HashMap<Integer, String> serverInfo) {
        Board.serverInfo = serverInfo;
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
