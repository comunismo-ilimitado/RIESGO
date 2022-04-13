package controller.game;

import controller.controllers.AttackController;
import controller.editor.ReadingFiles;
import model.CardTypes;
import model.Country;
import model.Player;
import view.menuFrames.GameStartWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * This class handles events of the User Interface and manages the action of the phases
 *
 * @author pazim
 * @version 3.o
 */
@SuppressWarnings("deprecation")
public class MyActionListener extends Observable implements ActionListener {
    private final MainController controller;
    private final List<String> phase;
    private String currentPhase;
    private int players = 0;
    public int currentPlayer = 0;
    private Country attackCountry1, attackCountry2;
    private Country fortifyCountry1, fortifyCountry2;
    private final List<CardTypes> cardTypesList = new ArrayList<>();

    /**
     * Builder of the class
     *
     * @param controller
     */
    public MyActionListener(MainController controller) {
        this.controller = controller;
        phase = new ArrayList<>();
        phase.add("Finish Reinforcement");
        phase.add("Finish Attack");
        phase.add("Finish Fortification");
        currentPhase = phase.get(0);
        players = controller.PlayerNo();
    }

    /**
     * This method changes the turn of the players
     */
    private void playerUpdate() {
        try {
            if (controller.PlayerNo2() > 1) { //Si el numero de jugadores es mayor que 1 se sigue jugando
                ArrayList<Integer> arrayList = new ArrayList<>(ReadingFiles.playerId2.keySet());
                int index = arrayList.indexOf(currentPlayer);
                index = index + 1;
                if (index >= arrayList.size()) {
                    currentPlayer = ReadingFiles.playerId2.get(arrayList.get(0)).getPlayerId(); //Le vuelve a tocar al primero
                } else {
                    currentPlayer = ReadingFiles.playerId2.get(arrayList.get(index)).getPlayerId();
                }
            } else { //Si solo queda un jugador ha ganado ese jugador
                controller.frame.error("Player :- " + ((int) ReadingFiles.playerId2.keySet().toArray()[0] + 1) + " Wins");
                controller.frame.dispose();
                System.exit(0);

            }
        } catch (Exception e) {
            playerUpdate();
        }
    }

    /**
     * This method refresh the armies that are not deployed and the armies in a country
     *
     * @param country: object
     */
    private void armiesNotDeployed(Country country) {
        cardTypesList.clear();
        controller.frame.jLabeCardl.setText(cardTypesList.toString());
        String message = controller.getReinforcementController().addArmies(country);
        controller.frame.noArmiesLeft = controller.playerObjet(currentPlayer).getPlayerArmiesNotDeployed();
        changed();
        if (!message.equals(""))
            controller.frame.error(message);
    }

    /**
     * This method switches between phases when the user loads a game
     *
     * @param phase
     */
    public void phaseResume(String phase) {
        if (phase.equals("Finish Reinforcement")) {
            selectTypeOfPlayer();
        } else if (phase.equals("Finish Attack")) {
            controller.frame.ActivateAll();
            controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
            finishReinforcement();
        } else if (phase.equals("Finish Fortification")) {
            controller.frame.ActivateAll();
            controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
            finishattack();
        }
    }

    /**
     * This method checks if someone doesnt have any country occupied and give his cards to the attacker player
     *
     * @param attacker
     */
    private void elimination(Player attacker) {
        ArrayList<Integer> arrayList = new ArrayList<>(ReadingFiles.playerId2.keySet());
        for (int i = 0; i < arrayList.size(); i++) {
            Player temp = ReadingFiles.playerId2.get(arrayList.get(i));
            if (temp.getTotalCountriesOccupied().size() == 0) {
                List<CardTypes> defcards = temp.getPlayerCards(); //Lista de cartas del jugador eliminado
                List<CardTypes> attcards = attacker.getPlayerCards(); //Lista de cartas del jugador que elimina
                attcards.addAll(defcards);  //Se juntan ambas listas
                attacker.setPlayerCards(attcards);  //Esta linea y la siguiente hacen lo mismo???
                ReadingFiles.playerId.get(attacker.getPlayerId()).setPlayerCards(attcards);
                ReadingFiles.playerId2.remove(temp.getPlayerId());
                ReadingFiles.players.remove((Integer) temp.getPlayerId());
            }

        }
        if (ReadingFiles.playerId2.size() <= 1) { //Si despues de la eliminacion solo queda un jugador ha ganado
            controller.frame.error("Player :- " + ((int) ReadingFiles.playerId2.keySet().toArray()[0] + 1) + " Wins");
            controller.frame.dispose();
            System.exit(0);

        }
    }

    /**
     * This method checks if the player is human or not and the difficulty of the CPU
     */
    public void selectTypeOfPlayer() {
        try {
            controller.RefreshButtons();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Player player = controller.playerObjet(currentPlayer);
        System.out.println("---------------------------------");
        System.out.println("Current Player " + (currentPlayer + 1));
        textarea("---------------------------------");
        textarea("Player Playing :- " + (currentPlayer + 1));
        String stratergy = player.getStrategy().trim(); //Elimina los caracteres blancos iniciales y finales
        if (stratergy.equals("Agressive")) {
            textarea("Currently in Reinforcement Mode for Agressive");
            player.aggressiveStrategy.reinforce(player);
            textarea("Currently in Attack Mode for Agressive ");
            player.aggressiveStrategy.attack(player);
            System.out.println("Attack Finished");
            elimination(player);
            textarea("Currently in Fortification Mode for Agressive");
            player.aggressiveStrategy.fortify(player);
            finishCPU();
        } else if (stratergy.equals("Benevolent")) {
            textarea("Currently in Reinforcement Mode Benevolent");
            player.benevolentStrategy.reinforce(player);
            textarea("Currently in Attack Mode Benevolent");
            player.benevolentStrategy.attack(player);
            elimination(player);
            textarea("Currently in Fortification Mode Benevolent");
            player.benevolentStrategy.fortify(player);
            finishCPU();
        } else if (stratergy.equals("Random")) {
            textarea("Currently in Reinforcement Mode Random");
            player.randomStrategy.reinforce(player);
            textarea("Currently in Attack Mode Random");
            player.randomStrategy.attack(player);
            elimination(player);
            textarea("Currently in Fortification Mode Random");
            player.randomStrategy.fortify(player);
            finishCPU();
        } else if (stratergy.equals("Cheater")) {
            textarea("Currently in Reinforcement Mode Cheater");
            player.cheaterStrategy.reinforce(player);
            textarea("Currently in Attack Mode Cheater");
            player.cheaterStrategy.attack(player);
            elimination(player);
            textarea("Currently in Fortification Mode Cheater");
            player.cheaterStrategy.fortify(player);
            finishCPU();
        } else if (stratergy.equals("Human")) {
            reinforcementPhase();
        }
    }

    /**
     * This method checks the validation of the reinforcement phase
     */
    private void reinforcementPhase() {
        textarea("Currently in Reinforcement Mode");
        buttonCards(true);
        changed();
        controller.frame.ActivateAll();
        controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
        controller.getReinforcementController().calculateReinforcementArmies(controller.playerObjet(currentPlayer));
        controller.frame.error("Its Player:- " + (currentPlayer + 1) + " Turn");
    }

    /**
     * This method checks the validation of the attack phase
     *
     * @param country: country object
     * @throws IOException
     */
    private void attackPhase(Country country) throws IOException {
        textarea("Attacking.... ");
        cardTypesList.clear();
        controller.frame.jLabeCardl.setText(cardTypesList.toString());
        changed();
        if (attackCountry1 == null) {
            attackCountry1 = country;
            controller.frame.ActivateAll();
            List<Country> neighbourList = controller.getAttackController().getMyNeighboursForAttack(country);
            if (neighbourList.size() < 1) {
                controller.frame.ActivateAll();
                attackCountry1 = null;
                attackCountry2 = null;
                controller.frame.error("No Neighbours to attack");
                controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
                controller.RefreshButtons();
            } else {
                controller.frame.OnlyNeeded(neighbourList);
                controller.RefreshButtons();
            }
        } else if (attackCountry2 == null) {
            attackCountry2 = country;
            int dice1 = 0;
            int dice2 = 0;
            boolean allout = false;

            try {
                allout = controller.frame.Allout();
                if (allout == true) {

                } else {
                    dice1 = Integer.parseInt(
                            controller.frame.popupTextNew("Enter No of Dices for player 1 --Minimum: 1 Maximum: "
                                    + controller.getAttackController().setNoOfDice(attackCountry1, 'A')));

                    dice2 = Integer.parseInt(
                            controller.frame.popupTextNew("Enter No of Dices for player 2 --Minimum: 1 Maximum: "
                                    + controller.getAttackController().setNoOfDice(attackCountry2, 'D')));
                }
            } catch (Exception e) {
                controller.frame.error("Invalid Entry Try again");
                controller.frame.ActivateAll();
                attackCountry1 = null;
                attackCountry2 = null;
                controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
                controller.RefreshButtons();
            }

            String reply = controller.attackController.attackButton(attackCountry1, attackCountry2, dice1, dice2,
                    allout);
            System.out.println(reply);
            if (reply.equals("Player won")) {
                controller.frame.error(reply);
                String[] args = {""};
                GameStartWindow.main(args);
            } else if (!reply.equals("")) {
                controller.frame.error(reply);
            }

            controller.frame.AAA = controller.attackController.attackerdicerolloutput.toString();
            controller.frame.BBB = controller.attackController.defenderdicerolloutput.toString();
            changed();
            controller.attackController.attackerdicerolloutput.clear();
            controller.attackController.defenderdicerolloutput.clear();
            controller.frame.ActivateAll();
            attackCountry1 = null;
            attackCountry2 = null;
            controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
            controller.RefreshButtons();
            controller.PaintCountries();
            boolean result = controller.getAttackController().canAttack(controller.playerObjet(currentPlayer));
            if (!result) {
               /* controller.frame.buttonCard4.setEnabled(false);
                changed();
                currentPhase = "Finish Fortification";
                controller.frame.nextAction.setText("Finish Fortification");
                fortifyCountry1 = null;
                fortifyCountry2 = null;
                fortificationPhase();*/
                finishattack();  //Puede dar algun error

            }

        } else {
            attackCountry1 = null;
            attackCountry2 = null;
        }
    }
/*
    /**
     * This method
     *
    private void fortificationPhase() {
        textarea("Currently in Fortification Mode");
        AttackController.card = false;
        changed();
        controller.frame.ActivateAll();
        controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
        playerUpdate(); // El jugador actual se cambia antes de fortificar??
    }
*/

    /**
     * This method checks the validation of the fortification phase
     *
     * @param country: country object
     * @throws IOException
     */
    private void fortificationPhase(Country country) throws IOException {
        cardTypesList.clear();
        controller.frame.jLabeCardl.setText(cardTypesList.toString());
        if (fortifyCountry1 == null) {
            fortifyCountry1 = country;
            controller.frame.CCC = controller.NeighboursList(country);
            changed();
            controller.frame.error("Select One More Country You Want to move your Armies to");
        } else if (fortifyCountry2 == null) {
            fortifyCountry2 = country;
            if (fortifyCountry1.equals(fortifyCountry2)) {
                controller.frame.error("SAME COUNTRY SELECTED");
                fortifyCountry1 = null;
                fortifyCountry2 = null;
            } else {
                try {
                    String test1 = controller.frame.popupText(fortifyCountry1.getNoOfArmies() - 1);  //Pregunta cuantas quiero transferir
                    String message = controller.getFortificationController().moveArmies(fortifyCountry1, fortifyCountry2,
                            Integer.parseInt(test1));
                    if (!message.equals("")) {
                        controller.frame.error(message);
                        fortifyCountry1 = null;
                        fortifyCountry2 = null;
                    } else {
                        controller.RefreshButtons();
                        currentPhase = "Finish Reinforcement";
                        controller.frame.nextAction.setText("Finish Reinforcement");
                        // playerUpdate();
                        fortifyCountry1 = null;
                        fortifyCountry2 = null;
                        controller.frame.ActivateAll();
                        selectTypeOfPlayer();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    controller.frame.error("Invalid Number");
                }
            }
        }
    }


    /**
     * This method prepares the game for attack phase
     */
    private void finishReinforcement() {
        cardTypesList.clear();
        buttonCards(false);
        currentPhase = "Finish Attack";
        controller.frame.nextAction.setText("Finish Attack");
        changed();
        attackCountry1 = null;
        attackCountry2 = null;
        cardTypesList.clear();
        controller.frame.jLabeCardl.setText(cardTypesList.toString());
        cardTypesList.clear();
        controller.frame.jLabeCardl.setText(cardTypesList.toString());

    }

    /**
     * This method prepares the game for fortification phase
     */
    private void finishattack() {
        buttonCards(false);
        changed();
        currentPhase = "Finish Fortification";
        controller.frame.nextAction.setText("Finish Fortification");
        fortifyCountry1 = null;
        fortifyCountry2 = null;
        cardTypesList.clear();
        controller.frame.jLabeCardl.setText(cardTypesList.toString());
        //fortificationPhase();
        textarea("Currently in Fortification Mode");
        AttackController.card = false;
        changed();
        controller.frame.ActivateAll();
        controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
        //playerUpdate(); // El jugador actual se cambia antes de fortificar??
        cardTypesList.clear();
        controller.frame.jLabeCardl.setText(cardTypesList.toString());

    }

    /**
     * This method prepares the game for the next player (Human mode)
     */
    private void finishFortification() {
        buttonCards(true);
        changed();
        currentPhase = "Finish Reinforcement";
        controller.frame.nextAction.setText("Finish Reinforcement");
        cardTypesList.clear();
        controller.frame.jLabeCardl.setText(cardTypesList.toString());
        playerUpdate();
        selectTypeOfPlayer();
        cardTypesList.clear();
        controller.frame.jLabeCardl.setText(cardTypesList.toString());
    }

    /**
     * This method prepares the game for the next player (CPU mode)
     */
    private void finishCPU() {
        playerUpdate();
        changed();
        selectTypeOfPlayer();
    }

    /**
     * This method enables or disables some buttons
     */
    private void buttonCards(boolean bool) {
        controller.frame.buttonCard4.setEnabled(bool);
        controller.frame.buttonCard3.setEnabled(bool);
        controller.frame.buttonCard2.setEnabled(bool);
        controller.frame.buttonCard1.setEnabled(bool);
    }

    /**
     * This method controls the action of the buttons
     *
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // TODO Auto-generated method stub
        if (phase.contains(event.getActionCommand())) {

            //Algun boton finish
            if (event.getActionCommand() == "Finish Reinforcement") {
                if (controller.playerObjet(currentPlayer).getPlayerArmiesNotDeployed() > 0) {  //Si quedan mas tropas -> Error
                    controller.frame.error("Cannot End Reinforcement Untill All armies are deployed");
                    cardTypesList.clear();
                    controller.frame.jLabeCardl.setText(cardTypesList.toString());
                } else {  //Si no termina fase refuerzo
                    finishReinforcement();
                }
            } else if (event.getActionCommand() == "Finish Attack") {
                finishattack();
            } else if (event.getActionCommand() == "Finish Fortification") { //Metodo finishFortification??
                finishFortification();
            }

            //Algun boton carta
        } else if (event.getActionCommand().split(" ")[0].equals("Infantry")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                cardTypesList.add(CardTypes.Infantry);
                controller.frame.buttonCard1.setText("Infantry " + (no - 1));
                controller.frame.jLabeCardl.setText(cardTypesList.toString());

            } else {
                controller.frame.error("No Card Of this Type");
            }

        } else if (event.getActionCommand().split(" ")[0].equals("Cavalry")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                cardTypesList.add(CardTypes.Cavalry);
                controller.frame.buttonCard3.setText("Cavalry " + (no - 1));
                controller.frame.jLabeCardl.setText(cardTypesList.toString());
            } else {
                controller.frame.error("No Card Of this Type");
            }
        } else if (event.getActionCommand().split(" ")[0].equals("Artillery")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                cardTypesList.add(CardTypes.Artillery);
                controller.frame.buttonCard2.setText("Artillery " + (no - 1));
                controller.frame.jLabeCardl.setText(cardTypesList.toString());

            } else {
                controller.frame.error("No Card Of this Type");
            }
        } else if (event.getActionCommand().equals("Exchange Cards")) {
            String answer = controller.getReinforcementController().exchangeCards(cardTypesList,
                    controller.playerObjet(currentPlayer));
            if (answer == "") {
                cardTypesList.clear();
                controller.frame.jLabeCardl.setText(cardTypesList.toString());
                controller.frame.noArmiesLeft = controller.playerObjet(currentPlayer).getPlayerArmiesNotDeployed();

            } else {
                controller.frame.error("Invalid Cards Selected");
                cardTypesList.clear();
                controller.frame.jLabeCardl.setText(cardTypesList.toString());

            }
            changed();

        } else {  //LLamada a las logicas de cada fase
            controller.frame.noArmiesLeft = controller.playerObjet(currentPlayer).getPlayerArmiesNotDeployed(); //Actualiza las tropas que quedan
            String Cname = event.getActionCommand().split("\\|")[0].trim();
            Country temp2 = controller.countryObjects().get(Cname); //Territorio seleccionado
            if (currentPhase.equals("Finish Reinforcement")) {
                if (controller.playerObjet(currentPlayer).getPlayerCards().size() >= 5) {
                    controller.frame.error("First Exchange Cards");
                } else {
                    armiesNotDeployed(temp2);   //Suma una tropa al territorio
                    try {
                        controller.RefreshButtons();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else if (currentPhase.equals("Finish Fortification")) {
                try {
                    fortificationPhase(temp2);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

            } else if (currentPhase.equals("Finish Attack")) {
                try {
                    attackPhase(temp2);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

            }

        }

    }

    /**
     * @return Number of infantry cards of the current player
     */
    public String getInfantryCards() {
        int cont = 0;
        List<CardTypes> type = controller.playerObjet(currentPlayer).getPlayerCards();
        for (int i = 0; i < type.size(); i++) {
            int x = type.get(i).compareTo(CardTypes.Infantry);
            if (x == 0) {
                cont += 1;
            }

        }

        return "" + cont;
    }

    /**
     * @return Number of cavalry cards of the current player
     */
    public String getCavalryCards() {
        int cont = 0;
        List<CardTypes> type = controller.playerObjet(currentPlayer).getPlayerCards();
        for (int i = 0; i < type.size(); i++) {
            int x = type.get(i).compareTo(CardTypes.Cavalry);
            if (x == 0) {
                cont += 1;
            }

        }

        return "" + cont;
    }

    /**
     * @return Number of artillery cards of the current player
     */
    public String getArtilleryCards() {
        int cont = 0;
        List<CardTypes> type = controller.playerObjet(currentPlayer).getPlayerCards();
        for (int i = 0; i < type.size(); i++) {
            int x = type.get(i).compareTo(CardTypes.Artillery);
            if (x == 0) {
                cont += 1;
            }
        }
        return "" + cont;
    }

    //Metodos de controller para MFrame ???
    public ArrayList<Float> countriesPercentage() {
        return controller.CountriesPercentage();
    }

    public ArrayList<String> continentsOccupied() {
        return controller.ContinentsOccupied();
    }

    public int getArmiesPerPlayer() {
        return controller.attackController.getTotalCountries(controller.playerObjet(currentPlayer));
    }

    /**
     * This method saves the game in SaveGame.txt
     */
    public void saveGameOnExit() {
        try {
            File file = new File("Resources/SaveGame.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(ReadingFiles.address + "\n");
            writer.write(ReadingFiles.playerId.size() + "\n");
            writer.write(currentPlayer + "\n");
            writer.write(currentPhase + "\n");
            writer.write(controller.frame.area.getText());
            for (int i = 0; i < controller.PlayerNo2(); i++) {
                Player tempPlayer = ReadingFiles.playerId2.get(ReadingFiles.playerId2.keySet().toArray()[i]);
                writer.write("----PLAYER----\n");
                System.out.println("Error Report :-" + tempPlayer + "---" + controller.PlayerNo2());
                writer.write(tempPlayer.getPlayerId() + "\n");
                System.out.println();
                writer.write(tempPlayer.getStrategy() + "\n");
                for (int j = 0; j < tempPlayer.getTotalCountriesOccupied().size(); j++) {
                    Country tempCountry = tempPlayer.getTotalCountriesOccupied().get(j);
                    writer.write(tempCountry.getName() + "***" + tempCountry.getNoOfArmies() + "\n");
                }
                writer.write("----CARDS----\n");
                writer.write(tempPlayer.getPlayerCards().toString() + "\n");
            }
            writer.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method writes on the graphic interface
     *
     * @param string
     */
    private void textarea(String string) {
        controller.frame.area.append("\n" + string);
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

}
