package controller.controllers;

import controller.editor.ReadingFiles;
import model.Country;
import model.Player;

import java.util.*;

/**
 * Fortification Controller has all the methods required during fortification
 * phase of the game
 *
 * @author bhargav
 * @version 1.0.0
 */
public class FortificationController {
    /**
     * Gets a list of countries that the player owns
     *
     * @param player: Player object must be given to fetch the countries
     * @return List of countries owned by the player
     */
    public List<Country> getMyCountries(Player player) {
        List<Country> countries = new ArrayList<Country>();
        for (Map.Entry<String, Country> entry : ReadingFiles.getCountryNameObject().entrySet()) {
            if (entry.getValue().getOwner().getPlayerId() == (player.getPlayerId())) {
                countries.add(entry.getValue());
            } else
                continue;
        }
        return countries;
    }

    /**
     * Check if exists a path between two countries
     *
     * @param source:      The country which armies are being moved from
     * @param destination: The country which armies are being moved to
     * @return Returns true if there is a path to move the armies between countries
     */
    public boolean hasPathBFS2(Country source, Country destination) {
        LinkedList<Country> nexttovisit = new LinkedList<Country>();
        HashSet<String> visited = new HashSet<String>();
        nexttovisit.add(source);
        while (!nexttovisit.isEmpty()) {
            Country node = nexttovisit.remove();
            if (node.getName().equals(destination.getName())) {
                return true;
            }
            if (visited.contains(node.getName()))
                continue;
            visited.add(node.getName());
            for (Country child : node.getNeighbors()) {
                if (child.getOwner().getPlayerId() == node.getOwner().getPlayerId()) {
                    nexttovisit.add(child);
                }
            }
        }
        return false;
    }

    /**
     * Validates and move armies between two countries owned by the same player
     *
     * @param sourceCountry:       The country which armies are being moved from
     * @param destinationCountry:  The country which armies are being moved to
     * @param armiesToBeMoved: Armies asked by player to move
     * @return: Returns a string if there is any error or a null string if
     * validations succeed
     */
    public String moveArmies(Country sourceCountry, Country destinationCountry, int armiesToBeMoved) {
        if (sourceCountry.getNoOfArmies() < 2) {
            return "less army";
        } else if (armiesToBeMoved >= sourceCountry.getNoOfArmies()) {
            int CanMove = sourceCountry.getNoOfArmies() - 1;
            System.out.println(CanMove);
            return "You can only move" + CanMove;
        } else if (!hasPathBFS2(sourceCountry, destinationCountry)) {
            return "NO path";
        } else {
            sourceCountry.setNoOfArmies(sourceCountry.getNoOfArmies() - armiesToBeMoved);
            int x = ReadingFiles.getContinentNameObject().get(sourceCountry.getContinent().getName()).getCountries()
                    .indexOf(sourceCountry);
            ReadingFiles.getContinentNameObject().get(sourceCountry.getContinent().getName()).getCountries().get(x)
                    .setNoOfArmies(sourceCountry.getNoOfArmies());
            destinationCountry.setNoOfArmies(destinationCountry.getNoOfArmies() + armiesToBeMoved);
            int y = ReadingFiles.getContinentNameObject().get(destinationCountry.getContinent().getName()).getCountries()
                    .indexOf(destinationCountry);
            ReadingFiles.getContinentNameObject().get(destinationCountry.getContinent().getName()).getCountries().get(y)
                    .setNoOfArmies(destinationCountry.getNoOfArmies());
            return "";
        }
    }
}
