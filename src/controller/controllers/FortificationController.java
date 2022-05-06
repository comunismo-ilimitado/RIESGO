package controller.controllers;

import controller.editor.ReadingFiles;
import controller.game.ServerController;
import model.Country;
import model.Player;

import java.io.IOException;
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
    public static boolean hasPathBFS2(Country source, Country destination) {
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

    /**
     * This method checks the validation of the fortification phase
     *
     * @param mainController
     * @throws IOException
     */
    public void fortificationPhase(String test1, ServerController mainController) throws IOException {
        mainController.getCardTypesList().clear();
        mainController.getFrame().jLabeCardl.setText(mainController.getCardTypesList().toString());
        mainController.getBoardFacade().setSelectedCards(mainController.getCardTypesList());

        if(mainController.getFortifyCountry2() == null || mainController.getFortifyCountry2() == null){
            mainController.getBoardFacade().sendErrorMessage("No countries selected to reinforce",
                    mainController.playerObjet(mainController.getCurrentPlayer()));
            return;
        }
        try {
            String message = mainController.getFortificationController().moveArmies(mainController.getFortifyCountry1(), mainController.getFortifyCountry2(),
                    Integer.parseInt(test1));
            if (!message.equals("")) {
                mainController.getFrame().error(message);
                mainController.getBoardFacade().sendErrorMessage(message,
                        mainController.playerObjet(mainController.getCurrentPlayer()));
                mainController.setFortifyCountry1(null);
                mainController.setFortifyCountry2(null);
            } else {
                mainController.getBoardFacade().sendServerInfo("Reinforced");
                mainController.setFortifyCountry1(null);
                mainController.setFortifyCountry2(null);
                finishFortification(mainController);

            }
        } catch (Exception e) {
            // TODO: handle exception
            mainController.getFrame().error("Invalid Number");
            mainController.getBoardFacade().sendErrorMessage("Invalid Number",
                    mainController.playerObjet(mainController.getCurrentPlayer()));
        }

    }

    /**
     * Gives list of neighbors
     *
     * @param country : country whose neighbor list you want
     * @return result: string of neighbors
     */
    public String NeighboursList(Country country) {
        List<Country> countrylist = country.getNeighbors();
        String result = "";
        for (int i = 0; i < countrylist.size(); i++) {
            result = result.concat(countrylist.get(i).getName() + ",");
        }
        return result;
    }

    /**
     * This method prepares the game for the next player (Human mode)
     *
     * @param mainController
     */
    public void finishFortification(ServerController mainController) {
        mainController.buttonCards(true);
        mainController.changed();
        mainController.setCurrentPhase("Finish Reinforcement");
        mainController.getFrame().nextAction.setText("Finish Reinforcement");
        mainController.getCardTypesList().clear();
        mainController.getFrame().jLabeCardl.setText(mainController.getCardTypesList().toString());
        mainController.getBoardFacade().setSelectedCards(mainController.getCardTypesList());
        mainController.playerUpdate();
        mainController.selectTypeOfPlayer();
        mainController.getCardTypesList().clear();
        mainController.getFrame().jLabeCardl.setText(mainController.getCardTypesList().toString());
        mainController.getBoardFacade().setSelectedCards(mainController.getCardTypesList());
    }
}
