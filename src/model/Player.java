package model;

import controller.controllers.ReinforcementController;
import controller.strategies.AggressiveStratery;
import controller.strategies.BenevolentStrategy;
import controller.strategies.CheaterStrategy;
import controller.strategies.RandomStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game
 *
 * @author bhargav
 * @version 1.1
 */
public class Player {

    ReinforcementController reinforcementcontroller = new ReinforcementController();

    public AggressiveStratery aggressiveStrategy = new AggressiveStratery();
    public BenevolentStrategy benevolentStrategy = new BenevolentStrategy();
    public CheaterStrategy cheaterStrategy = new CheaterStrategy();
    public RandomStrategy randomStrategy = new RandomStrategy();

    private String strategy = "Human";
    private String name;
    private int player_id;
    private int total_armies;
    private int total_armies_not_deployed;
    private List<Country> total_countries_occupied;
    private final List<Country> countries_occupied;
    private List<Continent> continents_occupied;
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
     * Gets players strategy
     *
     * @param strategy
     */
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    /**
     * Sets players strategy
     *
     * @return
     */
    public String getStrategy() {
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
    public int calcArmiesByControlValue(Player player) {
        return reinforcementcontroller.calcArmiesByControlValue(player);
    }
}
