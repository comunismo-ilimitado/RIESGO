package model;

import controller.controllers.AttackController;
import controller.controllers.FortificationController;
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
    FortificationController fortificationcontroller = new FortificationController();
    AttackController attackcontroller = new AttackController();
    public AggressiveStratery aggressiveStratergy = new AggressiveStratery();
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
    public void setPlayerCards(List<CardTypes> Playercards) {
        this.cards = Playercards;
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

    public void addCountriesOccupied(Country countriesOccupied) {
        this.total_countries_occupied.add(countriesOccupied);
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
     * number of times player exchanges the cards
     *
     * @param list:   list of cards
     * @param player: player object must be given to fetch the player cards
     * @return message how player can exchange cards
     */
    public String exchangeCards(List<CardTypes> list, Player player) {
        return reinforcementcontroller.exchangeCards(list, player);
    }

    public void ClearArmies() {
        total_countries_occupied.clear();
        countries_occupied.clear();
    }

    /**
     * this method checks for the number of armies that are not deployed
     *
     * @param country source country of the player
     * @return some value of type string
     */
    public String addarmies(Country country) {
        return reinforcementcontroller.addArmies(country);
    }

    /**
     * this method calculates the number of armies each player gets to reinforce
     *
     * @param player: player object for which the armies are calculated
     */
    public void calculateReinforcementArmies(Player player) {
        reinforcementcontroller.calculateReinforcementArmies(player);
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

    /**
     * Method to validate and move armies between two countries owned by the same
     * player
     *
     * @param sourcecountry:       The country which armies are being moved from
     * @param destinationcountry:  The country which armies are being moved to
     * @param noofarmiestobemoved: Armies asked by player to move
     * @return: Returns a string if there is any error or a null string if
     * validations succeed
     */
    public String moveArmies(Country sourcecountry, Country destinationcountry, int noofarmiestobemoved) {
        return fortificationcontroller.moveArmies(sourcecountry, destinationcountry, noofarmiestobemoved);
    }

    /**
     * checks player can attack or not
     *
     * @param player: player object
     * @return true if player can attack else false
     */
    public boolean canAttack(Player player) {
        return attackcontroller.canAttack(player);
    }

    /**
     * Gets the neighbors of a given country excluding the countries with the same
     * owner as the given country and any countries with only one army
     *
     * @param country: Country object must be passed to fetch its neighbors
     * @return Returns List of countries which are neighbors of the given country
     */
    public List<Country> getMyNeighboursForAttack(Country country) {
        return attackcontroller.getMyNeighborsForAttack(country);
    }

    /**
     * Gets number of dice for the attack and defense
     *
     * @param country: Country object must be passed to get number of armies to set
     *                 dice
     * @param ad:      To know its the attacker or defender to set dice based on that
     * @return Number of Dice to be assigned
     */
    public int setNoOfDice(Country country, char ad) {
        return attackcontroller.setNoOfDice(country, ad);
    }

    public void setStratergy(String strategy) {
        this.strategy = strategy;
    }

    public String getStatergy() {
        return strategy;
    }
}
