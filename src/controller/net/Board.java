package controller.net;

import model.CardTypes;
import model.Continent;
import model.Country;
import model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board extends NetPackages.Package {

    private String mapName = "world";
    private String currentPhase = "";

    private Player currentPlayer;

    private HashMap<String, Country> countries;
    private HashMap<String, Continent> continents;
    private HashMap<Integer, Player> players;

    private Country attackCountry1;
    private Country attackCountry2;

    private Country selectedCountry1;
    private Country selectedCountry2;

    private List<CardTypes> cardTypesList;

    private HashMap<Integer, ErrorMessage> errors;

    private HashMap<Integer, ClientUpdate.ClientAction> actions;

    private HashMap<Integer, String> serverInfo;


    public Board(){
        countries = new HashMap<>();
        continents = new HashMap<>();
        players = new HashMap<>();
        errors = new HashMap<>();
        actions = new HashMap<>();
        cardTypesList = new ArrayList<>();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getCurrentPhase() {
        return currentPhase;
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

    public void setAttackCountry1(Country attackCountry1) {
        this.attackCountry1 = attackCountry1;
    }

    public Country getAttackCountry1() {
        return attackCountry1;
    }

    public void setSelectedCountry2(Country selectedCountry2) {
        this.selectedCountry2 = selectedCountry2;
    }

    public void setSelectedCountry1(Country selectedCountry1) {
        this.selectedCountry1 = selectedCountry1;
    }

    public Country getSelectedCountry2() {
        return selectedCountry2;
    }

    public Country getSelectedCountry1() {
        return selectedCountry1;
    }

    public void setAttackCountry2(Country attackCountry2) {
        this.attackCountry2 = attackCountry2;
    }

    public Country getAttackCountry2() {
        return attackCountry2;
    }

    public void setCardTypesList(List<CardTypes> cardTypesList) {
        this.cardTypesList = cardTypesList;
    }

    public List<CardTypes> getCardTypesList() {
        return cardTypesList;
    }

    public HashMap<Integer, String> getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(HashMap<Integer, String> serverInfo) {
        this.serverInfo = serverInfo;
    }

    public static class ErrorMessage implements Serializable {
        public String text;
        Player player;
        int id = 0;

        public ErrorMessage(String errortext, Player player){
            this.text = errortext;
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
