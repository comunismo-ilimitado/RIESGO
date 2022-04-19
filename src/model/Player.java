package model;

import controller.editor.ReadingFiles;
import controller.strategies.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a player in the game
 *
 * @author bhargav
 * @version 1.1
 */
public class Player {
    private Strategy strategy;
    private String strategyType;
    private int player_id;
    private int total_armies_not_deployed;
    private List<Country> total_countries_occupied;
    private final List<Country> countries_occupied;
    private List<CardTypes> cards;
    private Color my_color;
    private int card_exchange_counter;

    /**
     * Default Constructor
     */
    public Player(int player_id) {
        this.player_id = player_id;
        this.total_countries_occupied = new ArrayList<>();
        this.countries_occupied = new ArrayList<>();
        this.cards = new ArrayList<>();
        card_exchange_counter = 0;
    }

    /**
     * Gets player_id
     */
    public int getPlayerId() {
        return player_id;
    }

    /**
     * Sets player_id
     */
    public void setPlayerId(int player_id) {
        this.player_id = player_id;
    }

    /**
     * Gets Player name
     */
    public String getPlayerName() {
        return name;
    }

    /**
     * Sets Player name
     */
    public void setPlayerName(String name) {
        this.name = name;
    }

    /**
     * Gets Player Armies
     */
    public int getPlayerArmies() {
        return total_armies;
    }

    /**
     * Sets Player Armies
     */
    public void setPlayerArmies(int total_armies) {
        this.total_armies = total_armies;
    }

    /**
     * Gets Player Armies that are not deployed
     */
    public int getPlayerArmiesNotDeployed() {
        return total_armies_not_deployed;
    }

    /**
     * Sets Player Armies that are not deployed
     */
    public void setPlayerTotalArmiesNotDeployed(int total_armies_not_deployed) {
        this.total_armies_not_deployed = total_armies_not_deployed;
    }

    /**
     * Gets Countries Occupied
     */
    public List<Country> getTotalCountriesOccupied() {
        return getMyCountries(this);
    }

    /**
     * Sets Countries Occupied
     */
    public void setTotalCountriesOccupied(List<Country> total_countries_occupied) {
        this.total_countries_occupied = total_countries_occupied;
    }

    /**
     * Gets Continents Occupied
     */
    public List<Continent> getContinentsOccupied() {
        return continents_occupied;
    }

    /**
     * Sets Continents Occupied
     */
    public void setContinentsOccupied(List<Continent> continents_occupied) {
        this.continents_occupied = continents_occupied;
    }

    /**
     * Gets Player cards
     */
    public List<CardTypes> getPlayerCards() {
        return cards;
    }

    /**
     * Sets Player cards
     */
    public void setPlayerCards(List<CardTypes> PlayerCards) {
        this.cards = PlayerCards;
    }

    /**
     * Gets player color
     */
    public Color getPlayerColor() {
        return my_color;
    }

    /**
     * Sets player color
     */
    public void setPlayerColor(Color color) {
        this.my_color = color;
    }

    /**
     * Gets count how many times player exchange the cards
     *
     * @return integer value
     */
    public int getCardExchangeValue() {
        return card_exchange_counter;
    }

    /**
     * Sets count how many times player exchange the cards
     *
     * @param value
     */
    public void setCardExchangeValue(int value) {
        this.card_exchange_counter = value;
    }

    /**
     * Sets players strategy
     *
     */
    public void setStrategy(String strategy) {
        this.strategyType = strategy;
        switch (strategy) {
            case "Aggressive":
                this.strategy = new AggressiveStrategy();
                break;
            case "Benevolent":
                this.strategy = new BenevolentStrategy();
                break;
            case "Random":
                this.strategy = new RandomStrategy();
                break;
            case "Cheater":
                this.strategy = new CheaterStrategy();
                break;
        }
    }

    public Strategy getStrategy(){
        return strategy;
    }

    public void addCountriesOccupied(Country countriesOccupied) {
        this.total_countries_occupied.add(countriesOccupied);
    }

    public void ClearArmies() {
        total_countries_occupied.clear();
        countries_occupied.clear();
    }

    /**
     * Gets a list of countries that the player owns
     *
     * @param player: Player object must be given to fetch the countries
     * @return List of countries owned by the player
     */
    public List<Country> getMyCountries(Player player) {
        return reinforcementcontroller.getMyCountries(player);
    }

    /**
     * this method calculates the number of armies according to the control value
     *
     * @param player: player object for which it calculates
     * @return armies
     */
    public int calcArmiesByControlValue() {
        List<Continent> continents = playerOwnsContinent();
        int armies = 0;
        for (Continent continent : continents) {
            armies = armies + continent.getControlValue();
        }
        return armies;
    }

    /**
     * Checks whether the player owns the whole continent or not.
     *
     * @return continents list of continents
     */
    public List<Continent> playerOwnsContinent() {
        List<Continent> continents = new ArrayList<>();
        for (Map.Entry<String, Continent> entry : ReadingFiles.ContinentNameObject.entrySet()) {
            List<Country> temp = entry.getValue().getCountries();
            int counter = 0;
            for (int i = 0; i < entry.getValue().getCountries().size(); i++) {
                if (entry.getValue().getCountries().get(i).getOwner().getPlayerId() == player_id)
                    counter++;
            }
            if (temp.size() == counter)
                continents.add(entry.getValue());
        }
        return continents;
    }
}
