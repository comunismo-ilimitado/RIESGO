package model;

import controller.editor.ReadingFiles;
import controller.strategies.*;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a player in the game
 *
 * @author bhargav
 * @version 1.1
 */
public class Player implements Serializable {
    private Strategy strategy;
    private String strategyType;
    private int playerId;
    private String playerName;
    private int totalArmiesNotDeployed;
    private List<Country> totalCountriesOccupied;
    private final List<Country> countriesOccupied;
    private List<CardTypes> cards;
    private Color myColor;
    private int cardExchangeCounter;

    /**
     * Default Constructor
     */
    public Player(int player_id) {
        this.playerId = player_id;
        this.totalCountriesOccupied = new ArrayList<>();
        this.countriesOccupied = new ArrayList<>();
        this.cards = new ArrayList<>();
        cardExchangeCounter = 0;
    }

    /**
     * Gets strategyType
     */
    public String getStrategyType() {
        return strategyType;
    }

    /**
     * Sets strategyType
     */
    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }
    /**
     * Gets player_id
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Sets player_id
     */
    public void setPlayerId(int player_id) {
        this.playerId = player_id;
    }

    /**
     * Gets Player Armies that are not deployed
     */
    public int getPlayerArmiesNotDeployed() {
        return totalArmiesNotDeployed;
    }

    /**
     * Sets Player Armies that are not deployed
     */
    public void setPlayerTotalArmiesNotDeployed(int total_armies_not_deployed) {
        this.totalArmiesNotDeployed = total_armies_not_deployed;
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
        this.totalCountriesOccupied = total_countries_occupied;
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
        return myColor;
    }

    /**
     * Sets player color
     */
    public void setPlayerColor(Color color) {
        this.myColor = color;
    }

    /**
     * Gets count how many times player exchange the cards
     *
     * @return integer value
     */
    public int getCardExchangeValue() {
        return cardExchangeCounter;
    }

    /**
     * Sets count how many times player exchange the cards
     *
     * @param value
     */
    public void setCardExchangeValue(int value) {
        this.cardExchangeCounter = value;
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
        this.totalCountriesOccupied.add(countriesOccupied);
    }

    public void ClearArmies() {
        totalCountriesOccupied.clear();
        countriesOccupied.clear();
    }

    /**
     * Gets a list of countries that the player owns
     *
     * @param player: Player object must be given to fetch the countries
     * @return List of countries owned by the player
     */
    public List<Country> getMyCountries(Player player) {
            List<Country> countries = new ArrayList<>();
            for (Map.Entry<String, Country> entry : ReadingFiles.getCountryNameObject().entrySet()) {
                if (entry.getValue().getOwner().getPlayerId() == (player.getPlayerId())) {
                    countries.add(entry.getValue());
                }
            }
            return countries;
    }

    /**
     * this method calculates the number of armies according to the control value
     *
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
        for (Map.Entry<String, Continent> entry : ReadingFiles.getContinentNameObject().entrySet()) {
            List<Country> temp = entry.getValue().getCountries();
            int counter = 0;
            for (int i = 0; i < entry.getValue().getCountries().size(); i++) {
                if (entry.getValue().getCountries().get(i).getOwner().getPlayerId() == playerId)
                    counter++;
            }
            if (temp.size() == counter)
                continents.add(entry.getValue());
        }
        return continents;
    }

    public static class PlayerConfiguration{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    /**
     * Calculates the number of armies each player gets to reinforce
     */
    public void calculateReinforcementArmies() {
        int totalPlayerCountries = 0;
        for (Map.Entry<String, Country> entry : ReadingFiles.getCountryNameObject().entrySet()) {
            if (entry.getValue().getOwner().getPlayerId() == (getPlayerId())) {
                totalPlayerCountries++;
            }
        }
        float totalReinforcementArmies;
        totalReinforcementArmies = (float) totalPlayerCountries / 3;
        int armies = 0;
        if (totalReinforcementArmies < 3.0) {
            armies = armies + 3;
        } else {
            armies = armies + (int) totalReinforcementArmies;
        }
        armies = armies + calcArmiesByControlValue();
        setPlayerTotalArmiesNotDeployed(getPlayerArmiesNotDeployed() + armies);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
