package controller.controllers;

import controller.editor.ReadingFiles;
import controller.game.ServerController;
import model.CardTypes;
import model.Country;
import model.Player;
import view.menuFrames.GameStartWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AttackController has all the methods needed in attack phase of the game
 *
 * @author bhargav
 * @version 1.0.0
 */
public class AttackController {
    private List<Integer> attackerdiceroll;
    private List<Integer> defenderdiceroll;
    private List<String> attackerdicerolloutput = new ArrayList<>();
    private List<String> defenderdicerolloutput = new ArrayList<>();
    private static boolean card;

    /**
     * Gets list of total number of countries
     */
    public int getTotalCountries() {
        int count = 0;
        for (Map.Entry<String, Country> entry : ReadingFiles.getCountryNameObject().entrySet()) {
            count += entry.getValue().getNoOfArmies();
        }
        return count;
    }

    /**
     * Gets list of total number of countries player owns
     *
     * @param player: Player object must be given to fetch the countries
     */
    public int getTotalCountries(Player player) {
        int count = 0;
        for (Map.Entry<String, Country> entry : ReadingFiles.getCountryNameObject().entrySet()) {
            if (entry.getValue().getOwner().getPlayerId() == player.getPlayerId()) {
                count += entry.getValue().getNoOfArmies();
            }
        }
        return count;
    }

    /**
     * Checks if a player can attack or not
     *
     * @param player: player object
     * @return true if player can attack else false
     */
    public boolean canAttack(Player player) {
        List<Country> list = getMyCountries(player);
        for (Country temp : list) {
            if (temp.getNeighbors().size() != 0 && temp.getNoOfArmies() >= 2) {
                List<Country> tempList = temp.getNeighbors();
                for (Country country : tempList) {
                    if (country.getOwner().getPlayerId() != temp.getOwner().getPlayerId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets a list of countries that the player owns
     *
     * @param player: Player object must be given to fetch the countries
     * @return List of countries owned by the player
     */
    public List<Country> getMyCountries(Player player) {
        List<Country> countries = new ArrayList<Country>();
        for (Map.Entry<String, Country> entry : ReadingFiles.getCountryNameObject().entrySet()) {
            if (entry.getValue().getOwner().getPlayerId() == player.getPlayerId()) {
                countries.add(entry.getValue());
            }
        }
        return countries;
    }

    /**
     * Gets the neighbors of a given country excluding the countries with the same
     * owner as the given country and any countries with only one army
     *
     * @param country: Country object must be passed to fetch its neighbors
     * @return Returns List of countries which are neighbors of the given country
     */
    public List<Country> getMyNeighboursForAttack(Country country) {
        List<Country> neighbors = country.getNeighbors();
        List<Country> temp = new ArrayList<Country>();
        for (Country neighbor : neighbors) {
            try {
                if (neighbor.getOwner().getPlayerId() == country.getOwner().getPlayerId()) {
                    temp.add(neighbor);
                }
            } catch (Exception ignored) {
            }
        }
        neighbors.removeAll(temp);
        return neighbors;
    }

    /**
     * Decrements armies of the given country which lost the dice roll in attack
     * phase
     *
     * @param country: Country object must be passed so its armies will be updated
     */
    public void updateArmies(Country country) {
        country.setNoOfArmies(country.getNoOfArmies() - 1);
    }

    /**
     * Updates the owner of attacked country if the attacker wins
     *
     * @param player:  Player object must be passed to get the new owner information
     *                 to be updated in the country
     * @param country: Country object must be passed to update the owner
     */
    public void updateOwner(Player player, Country country) {
        country.setPlayer(player);
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
        try {
            if (ad == 'A') {
                if (country.getNoOfArmies() == 2)
                    return 1;
                else if (country.getNoOfArmies() == 3)
                    return 2;
                else
                    return 3;
            } else if (ad == 'D') {
                if (country.getNoOfArmies() == 1)
                    return 1;
                else
                    return 2;
            } else
                return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * To get a random number between 1 and 6 which simulates a dice roll
     *
     * @return Returns an integer which is the dice roll value
     */
    public int rollDice() {
        int number = (int) (Math.random() * 6 + 1);
        return number;
    }

    /**
     * Updates the owner of a country if the attacker destroys all armies of
     * opponent
     *
     * @param country: Country object to update owner information
     * @param player:  Player object to update the owner in the given country
     */
    public void updateOwner(Country country, Player player) {
        country.setPlayer(player);
        ReadingFiles.getCountryNameObject().get(country.getName()).setPlayer(player);
        return;
    }

    /**
     * Attack simulator
     *
     * @param attacker:     player who attacks
     * @param defender:     player who's country being attacked
     * @param attackerDice: dice of attacker
     * @param defenderDice: dice of defender
     * @param allOut:if     it's all out attack or not
     * @return string which player has won
     */
    public String attackButton(Country attacker, Country defender, int attackerDice, int defenderDice, boolean allOut) {
        try {
            if (attackerDice <= setNoOfDice(attacker, 'A') && defenderDice <= setNoOfDice(defender, 'D')) {
                if (attacker.getNoOfArmies() >= 2 && defender.getNoOfArmies() >= 1) {
                    attackerdiceroll = new ArrayList<Integer>();
                    defenderdiceroll = new ArrayList<Integer>();
                    if (allOut) {
                        while (attacker.getNoOfArmies() > 1
                                && defender.getOwner().getPlayerId() != attacker.getOwner().getPlayerId()) {
                            attackerDice = setNoOfDice(attacker, 'A');
                            defenderDice = setNoOfDice(defender, 'D');
                            attackerdiceroll.clear();
                            defenderdiceroll.clear();
                            for (int i = 0; i < attackerDice; i++) {
                                attackerdiceroll.add(rollDice());
                            }
                            for (int i = 0; i < defenderDice; i++) {
                                defenderdiceroll.add(rollDice());
                            }
                            attackerdicerolloutput.add(attackerdiceroll.toString());
                            defenderdicerolloutput.add(defenderdiceroll.toString());

                            while (attackerdiceroll.size() != 0 && defenderdiceroll.size() != 0) {
                                int attackermax = getMaxValue(attackerdiceroll);
                                int defendermax = getMaxValue(defenderdiceroll);
                                if (attackermax <= defendermax) {
                                    updateArmies(attacker);
                                } else {
                                    updateArmies(defender);
                                }

                                attackerdiceroll.remove((Integer) attackermax);
                                defenderdiceroll.remove((Integer) defendermax);
                                if (attackerDice == 1)
                                    break;
                                else
                                    continue;
                            }
                            if (defender.getNoOfArmies() == 0) {
                                Player elimination = defender.getOwner();

                                List<Country> newlistofcountriesatt = ReadingFiles.getPlayerId()
                                        .get(attacker.getOwner().getPlayerId()).getTotalCountriesOccupied();
                                newlistofcountriesatt.add(defender);
                                ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId())
                                        .setTotalCountriesOccupied(newlistofcountriesatt);
                                attacker.getOwner().setTotalCountriesOccupied(newlistofcountriesatt);
                                List<Country> newlistofcountriesdef = ReadingFiles.getPlayerId()
                                        .get(defender.getOwner().getPlayerId()).getTotalCountriesOccupied();
                                newlistofcountriesdef.remove(defender);
                                ReadingFiles.getPlayerId().get(defender.getOwner().getPlayerId())
                                        .setTotalCountriesOccupied(newlistofcountriesdef);
                                defender.getOwner().setTotalCountriesOccupied(newlistofcountriesdef);
                                updateOwner(defender, attacker.getOwner());
                                defender.setNoOfArmies(attackerDice);
                                attacker.setNoOfArmies(attacker.getNoOfArmies() - attackerDice);
                                if (getMyCountries(elimination).size() == 0) {
                                    List<CardTypes> defcards = elimination.getPlayerCards();
                                    List<CardTypes> attcards = attacker.getOwner().getPlayerCards();
                                    attcards.addAll(defcards);
                                    attacker.getOwner().setPlayerCards(attcards);
                                    ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId()).setPlayerCards(attcards);
                                    ReadingFiles.getPlayerId2().remove(elimination.getPlayerId());
                                    ReadingFiles.getPlayers().remove((Integer) elimination.getPlayerId());
                                }
                                if (!card) {
                                    int cardnumber = (int) (Math.random() * 3 + 1);
                                    List<CardTypes> newsetofcards = new ArrayList<CardTypes>();
                                    if (cardnumber == 1) {
                                        newsetofcards = attacker.getOwner().getPlayerCards();
                                        newsetofcards.add(CardTypes.Artillery);
                                        attacker.getOwner().setPlayerCards(newsetofcards);
                                        ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId())
                                                .setPlayerCards(newsetofcards);

                                    } else if (cardnumber == 2) {
                                        newsetofcards = attacker.getOwner().getPlayerCards();
                                        newsetofcards.add(CardTypes.Cavalry);
                                        attacker.getOwner().setPlayerCards(newsetofcards);
                                        ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId())
                                                .setPlayerCards(newsetofcards);
                                    } else if (cardnumber == 3) {
                                        newsetofcards = attacker.getOwner().getPlayerCards();
                                        newsetofcards.add(CardTypes.Infantry);
                                        attacker.getOwner().setPlayerCards(newsetofcards);
                                        ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId())
                                                .setPlayerCards(newsetofcards);
                                    }
                                    card = true;
                                }
                            }
                        }
                        if (attacker.getOwner().getTotalCountriesOccupied().size() == getTotalCountries()) {
                            return "Player " + attacker.getOwner().getPlayerId() + " wins";
                        }
                        return "";
                    } else {
                        for (int i = 0; i < attackerDice; i++) {
                            attackerdiceroll.add(rollDice());
                        }
                        for (int i = 0; i < defenderDice; i++) {
                            defenderdiceroll.add(rollDice());
                        }
                        attackerdicerolloutput.add(attackerdiceroll.toString());
                        defenderdicerolloutput.add(defenderdiceroll.toString());

                        while (attackerdiceroll.size() != 0 && defenderdiceroll.size() != 0) {
                            int attackermax = getMaxValue(attackerdiceroll);
                            int defendermax = getMaxValue(defenderdiceroll);
                            if (attackermax <= defendermax) {
                                updateArmies(attacker);
                            } else {
                                updateArmies(defender);
                            }

                            attackerdiceroll.remove((Integer) attackermax);
                            defenderdiceroll.remove((Integer) defendermax);
                            if (attackerDice == 1)
                                break;
                            else
                                continue;
                        }
                        if (defender.getNoOfArmies() == 0) {
                            Player elimination = defender.getOwner();
                            List<Country> newlistofcountriesatt = ReadingFiles.getPlayerId()
                                    .get(attacker.getOwner().getPlayerId()).getTotalCountriesOccupied();
                            newlistofcountriesatt.add(defender);
                            ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId())
                                    .setTotalCountriesOccupied(newlistofcountriesatt);
                            attacker.getOwner().setTotalCountriesOccupied(newlistofcountriesatt);
                            List<Country> newlistofcountriesdef = ReadingFiles.getPlayerId()
                                    .get(defender.getOwner().getPlayerId()).getTotalCountriesOccupied();
                            newlistofcountriesdef.remove(defender);
                            ReadingFiles.getPlayerId().get(defender.getOwner().getPlayerId())
                                    .setTotalCountriesOccupied(newlistofcountriesdef);
                            defender.getOwner().setTotalCountriesOccupied(newlistofcountriesdef);
                            updateOwner(defender, attacker.getOwner());
                            defender.setNoOfArmies(attackerDice);
                            attacker.setNoOfArmies(attacker.getNoOfArmies() - attackerDice);
                            if (getMyCountries(elimination).size() == 0) {
                                List<CardTypes> defcards = elimination.getPlayerCards();
                                List<CardTypes> attcards = attacker.getOwner().getPlayerCards();
                                attcards.addAll(defcards);
                                attacker.getOwner().setPlayerCards(attcards);
                                ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId()).setPlayerCards(attcards);
                                ReadingFiles.getPlayerId2().remove(elimination.getPlayerId());
                                ReadingFiles.getPlayers().remove((Integer) elimination.getPlayerId());
                            }
                            if (!card) {
                                int cardnumber = (int) (Math.random() * 3 + 1);
                                List<CardTypes> newsetofcards = new ArrayList<CardTypes>();
                                if (cardnumber == 1) {
                                    newsetofcards = attacker.getOwner().getPlayerCards();
                                    newsetofcards.add(CardTypes.Artillery);
                                    attacker.getOwner().setPlayerCards(newsetofcards);
                                    ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId())
                                            .setPlayerCards(newsetofcards);

                                } else if (cardnumber == 2) {
                                    newsetofcards = attacker.getOwner().getPlayerCards();
                                    newsetofcards.add(CardTypes.Cavalry);
                                    attacker.getOwner().setPlayerCards(newsetofcards);
                                    ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId())
                                            .setPlayerCards(newsetofcards);
                                } else if (cardnumber == 3) {
                                    newsetofcards = attacker.getOwner().getPlayerCards();
                                    newsetofcards.add(CardTypes.Infantry);
                                    attacker.getOwner().setPlayerCards(newsetofcards);
                                    ReadingFiles.getPlayerId().get(attacker.getOwner().getPlayerId())
                                            .setPlayerCards(newsetofcards);
                                }
                                card = true;
                            }
                        }
                        if (attacker.getOwner().getTotalCountriesOccupied().size() == getTotalCountries()) {
                            return "Player won";
                        }
                        return "";
                    }
                } else {
                    if (attacker.getNoOfArmies() <= 1)
                        return "Your country must have more than one army";
                    else
                        return "Wrong input";
                }
            } else {
                return "Set the dice with the maximum value given or less";
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "Invalid Try Again";
    }

    /**
     * Gets maximum value of the dice
     *
     * @param list: list of dice roll numbers
     * @return maximum value from list
     */
    public int getMaxValue(List<Integer> list) {
        int max = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) > max)
                max = list.get(i);
            else
                continue;
        }
        return max;
    }

    //GETTERS AND SETTERS
    public List<String> getAttackerdicerolloutput() {
        return attackerdicerolloutput;
    }

    public List<String> getDefenderdicerolloutput() {
        return defenderdicerolloutput;
    }

    public static boolean isCard() {
        return card;
    }

    public static void setCard(boolean card) {
        AttackController.card = card;
    }

    /**
     * This method checks the validation of the attack phase
     * @param dice the number of dices
     * @param ad "A" for attacker "D" for defendant
     * @param mainController
     * @throws IOException
     */
    public void attackPhase(String dice, String ad, ServerController mainController) throws IOException {
        Player player = mainController.playerObjet(mainController.getCurrentPlayer());
        mainController.textarea("Attacking.... ");
        mainController.getCardTypesList().clear();
        mainController.getFrame().jLabeCardl.setText(mainController.getCardTypesList().toString());
        mainController.getBoardFacade().setSelectedCards(mainController.getCardTypesList());
        mainController.changed();
        boolean allout = false;
        if (ad.equals("A")){
            mainController.setDice1(Integer.parseInt(dice));
            if(mainController.getDice1() == -2){
                allout = true;
            }
        }else if(ad.equals("D")){
            mainController.setDice2(Integer.parseInt(dice));
        }
        if((mainController.getDice1() != -1 && mainController.getDice2() != -1)||allout){
            /*
            int dice1 = 0;
            int dice2 = 0;
            boolean allout = false;

            try {
                allout = mainController.getFrame().Allout();
                if (allout == true) {

                } else {
                    dice1 = Integer.parseInt(
                            mainController.getFrame().popupTextNew("Enter No of Dices for player 1 --Minimum: 1 Maximum: "
                                    + mainController.getAttackController().setNoOfDice(mainController.getAttackCountry1(), 'A')));
                    System.out.println("Dice 1 : "+ Integer.toString(dice1));
                    dice2 = Integer.parseInt(
                            mainController.getFrame().popupTextNew("Enter No of Dices for player 2 --Minimum: 1 Maximum: "
                                    + mainController.getAttackController().setNoOfDice(mainController.getAttackCountry2(), 'D')));
                }
            } catch (Exception e) {
                mainController.getFrame().error("Invalid Entry Try again");
                mainController.getBoardFacade().sendErrorMessage("Invalid Entry Try again",mainController.playerObjet(mainController.getCurrentPlayer()));
                mainController.getFrame().ActivateAll();
                mainController.setAttackCountry1(null);
                mainController.setAttackCountry2(null);
                mainController.OnlyNeeded(mainController.playerObjet(mainController.getCurrentPlayer()).getTotalCountriesOccupied());
                mainController.RefreshButtons();
            }*/
            String reply = attackButton(mainController.getAttackCountry1(), mainController.getAttackCountry2(), mainController.getDice1(),
                    mainController.getDice2(), allout);
            System.out.println(reply);
            if (reply.equals("Player won")) {
                mainController.getFrame().error(reply);
                mainController.getBoardFacade().sendErrorMessage(reply,mainController.playerObjet(mainController.getCurrentPlayer()));
                String[] args = {""};
                GameStartWindow.main(args);
            } else if (!reply.equals("")) {
                mainController.getFrame().error(reply);
            }

            mainController.getFrame().AAA = getAttackerdicerolloutput().toString();
            mainController.getFrame().BBB = getDefenderdicerolloutput().toString();
            mainController.changed();
            getAttackerdicerolloutput().clear();
            getDefenderdicerolloutput().clear();
            mainController.getFrame().ActivateAll();
            mainController.setAttackCountry1(null);
            mainController.setAttackCountry2(null);
            mainController.OnlyNeeded(mainController.playerObjet(mainController.getCurrentPlayer()).getTotalCountriesOccupied());
            mainController.RefreshButtons();
            mainController.PaintCountries();
            mainController.setDice1(-1);
            mainController.setDice2(-1);
            boolean result = mainController.getAttackController().canAttack(mainController.playerObjet(mainController.getCurrentPlayer()));
            if (!result) {
               /* controller.frame.buttonCard4.setEnabled(false);
                changed();
                currentPhase = "Finish Fortification";
                controller.frame.nextAction.setText("Finish Fortification");
                fortifyCountry1 = null;
                fortifyCountry2 = null;
                fortificationPhase();*/
                mainController.attackController.finishattack(mainController);  //Puede dar algun error

            }

        }
    }

    /**
     * This method prepares the game for fortification phase
     *
     * @param serverController
     */
    public void finishattack(ServerController serverController) {
        serverController.buttonCards(false);
        serverController.changed();
        serverController.setCurrentPhase("Finish Fortification");
        serverController.getFrame().nextAction.setText("Finish Fortification");
        serverController.setFortifyCountry1(null);
        serverController.setFortifyCountry2(null);
        serverController.getCardTypesList().clear();
        serverController.getFrame().jLabeCardl.setText(serverController.getCardTypesList().toString());
        serverController.getBoardFacade().setSelectedCards(serverController.getCardTypesList());
        //fortificationPhase();
        serverController.textarea("Currently in Fortification Mode");
        setCard(false);
        serverController.changed();
        serverController.getFrame().ActivateAll();
        serverController.OnlyNeeded(serverController.playerObjet(serverController.getCurrentPlayer()).getTotalCountriesOccupied());
        //playerUpdate(); // El jugador actual se cambia antes de fortificar??
        serverController.getCardTypesList().clear();
        serverController.getFrame().jLabeCardl.setText(serverController.getCardTypesList().toString());
        serverController.getBoardFacade().setSelectedCards(serverController.getCardTypesList());

    }
}