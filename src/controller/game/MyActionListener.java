package controller.game;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.net.ClientUpdate;
import model.CardTypes;
import model.Country;
import model.Player;

/**
 * This class handles events of the User Interface and manages the action of the phases
 *
 * @author pazim
 * @version 3.o
 */

@SuppressWarnings("deprecation")
public class MyActionListener implements ActionListener {
    private final ServerController controller;
    private int players = 0;

    /**
     * Builder of the class
     *
     * @param controller
     */
    public MyActionListener(ServerController controller) {
        this.controller = controller;
        controller.setPhaseList(new ArrayList<>());
        controller.getPhaseList().add("Finish Reinforcement");
        controller.getPhaseList().add("Finish Attack");
        controller.getPhaseList().add("Finish Fortification");
        controller.setCurrentPhase(controller.getPhaseList().get(0));
        players = controller.PlayerNo();
    }


    public void clientActionPerformed(ClientUpdate.ClientAction event) {
        System.out.println("Evento: " + event.getActionCommand());


        boolean success = false;

        // ERROR MANAGEMENT

        // If empty command received
        if (event.getActionCommand().equals("")) {
            return;
        }

        // If a player decides to send a message and its not his turn cancel operation
        if (!event.getActionCommand().contains("Dice")) {
            if (controller.getCurrentPlayer() != event.getPlayer().getPlayerId()) {
                return;
            }
        }

        Player player = controller.playerObjet(controller.getCurrentPlayer());
        // Si se trata de un boton de terminar fase
        if (controller.getPhaseList().contains(event.getActionCommand())) {

            //Algun boton finish
            switch (event.getActionCommand()) {
                case "Finish Reinforcement":

                    if (player.getPlayerArmiesNotDeployed() > 0) {  //Si quedan mas tropas -> Error
                        getController().getBoardFacade().sendErrorMessage("Cannot End Reinforcement Until All armies are deployed", player);

                        // TODO Remove UI references
                        controller.frame.error("Cannot End Reinforcement Until All armies are deployed");
                        controller.getBoardFacade().sendErrorMessage("Cannot End Reinforcement Until All armies are deployed", player);
                        controller.cardTypesList.clear();
                        controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                        controller.getBoardFacade().setSelectedCards(controller.cardTypesList);
                    } else {  //Si no termina fase refuerzo
                        controller.reinforcementController.finishReinforcement(controller);
                    }
                    break;
                case "Finish Attack":
                    controller.attackController.finishattack(controller);
                    break;
                case "Finish Fortification":  //Metodo finishFortification
                    controller.fortificationController.finishFortification(controller);
                    break;
            }

            //Algun boton carta
        } else if (event.getActionCommand().split(" ")[0].equals("Infantry")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                controller.cardTypesList.add(CardTypes.Infantry);

                // TODO borrar referncias graficas
                controller.frame.buttonCard1.setText("Infantry " + (no - 1));
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);

            } else {
                controller.frame.error("No Card Of this Type");
                getController().getBoardFacade().sendErrorMessage("No Card Of this Type", player);
            }

        } else if (event.getActionCommand().split(" ")[0].equals("Cavalry")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                controller.cardTypesList.add(CardTypes.Cavalry);

                // TODO borrar referncias graficas
                controller.frame.buttonCard3.setText("Cavalry " + (no - 1));
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);
            } else {
                controller.frame.error("No Card Of this Type");
                getController().getBoardFacade().sendErrorMessage("No Card Of this Type", player);
            }
        } else if (event.getActionCommand().split(" ")[0].equals("Artillery")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                controller.cardTypesList.add(CardTypes.Artillery);

                // TODO borrar referncias graficas
                controller.frame.buttonCard2.setText("Artillery " + (no - 1));
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);

            } else {
                controller.frame.error("No Card Of this Type");
                getController().getBoardFacade().sendErrorMessage("No Card Of this Type", player);
            }
        } else if (event.getActionCommand().equals("Exchange Cards")) {
            // TODO Integrar exchange cards en la funcionalidad online
            String answer = controller.getReinforcementController().exchangeCards(controller.cardTypesList,
                    controller.playerObjet(controller.getCurrentPlayer()));
            if (answer == "") {
                controller.cardTypesList.clear();
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.frame.noArmiesLeft = controller.playerObjet(controller.getCurrentPlayer()).getPlayerArmiesNotDeployed();
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);

            } else {
                controller.frame.error("Invalid Cards Selected");
                controller.getBoardFacade().sendErrorMessage("Invalid Cards Selected", player);
                controller.cardTypesList.clear();
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);

            }
            controller.changed();


        } else if (event.getActionCommand().split(" ")[0].equals("Dice")) {
            if (controller.getCurrentPhase().equals("Finish Attack")) {
                try {
                    getController().attackController.attackPhase(event.getActionCommand().split(" ")[1],
                            event.getActionCommand().split(" ")[2], getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Comando de seleccion de pais
        } else if (event.getActionCommand().split(" ")[0].equals("Reinforce")) {
            if (controller.getCurrentPhase().equals("Finish Fortification")) {
                try {
                    controller.fortificationController.fortificationPhase(event.getActionCommand().split(" ")[1], controller);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {  //LLamada a las logicas de cada fase

            // TODO eliminar la UI
            controller.frame.noArmiesLeft = controller.playerObjet(controller.getCurrentPlayer()).getPlayerArmiesNotDeployed(); //Actualiza las tropas que quedan
            String Cname = event.getActionCommand().split("\\|")[0].trim();
            Country country = controller.countryObjects().get(Cname); //Territorio seleccionado
            switch (controller.getCurrentPhase()) {
                case "Finish Reinforcement":
                    if (controller.playerObjet(controller.getCurrentPlayer()).getPlayerCards().size() >= 5) {
                        // TODO eliminar la UI
                        controller.frame.error("First Exchange Cards");
                        getController().getBoardFacade().sendErrorMessage("First Exchange Cards", player);
                    } else {
                        controller.armiesNotDeployed(country);   //Suma una tropa al territorio
                        try {
                            controller.RefreshButtons();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                case "Finish Fortification":
                    if (getController().getFortifyCountry1() == null) {
                        getController().setFortifyCountry1(country);
                        getController().getFrame().CCC = getController().fortificationController.NeighboursList(country);
                        getController().changed();
                        getController().getFrame().error("Select One More Country You Want to move your Armies to");
                        getController().getBoardFacade().sendErrorMessage("Select One More Country You Want to move your Armies to"
                                ,getController().playerObjet(getController().getCurrentPlayer()));
                    } else if (getController().getFortifyCountry2() == null) {
                        getController().setFortifyCountry2(country);
                        if (getController().getFortifyCountry1().equals(getController().getFortifyCountry2())) {
                            getController().getFrame().error("SAME COUNTRY SELECTED");
                            getController().getBoardFacade().sendErrorMessage("SAME COUNTRY SELECTED",
                                    getController().playerObjet(getController().getCurrentPlayer()));
                            getController().setFortifyCountry1(null);
                            getController().setFortifyCountry2(null);
                        }
                    }

                    break;
                case "Finish Attack":
                    try {
                        if (getController().getAttackCountry1() == null) {
                            getController().setAttackCountry1(country);
                            getController().getFrame().ActivateAll();
                            List<Country> neighbourList = getController().getAttackController().getMyNeighboursForAttack(country);
                            if (neighbourList.size() < 1) {
                                getController().getFrame().ActivateAll();
                                getController().setAttackCountry1(null);
                                getController().setAttackCountry2(null);
                                getController().getBoardFacade().sendErrorMessage("No Card Of this Type", player);
                                getController().getFrame().error("No Neighbours to attack");
                                getController().getBoardFacade().sendErrorMessage("No Neighbours to attack",getController().playerObjet(getController().getCurrentPlayer()));
                                getController().OnlyNeeded(getController().playerObjet(getController().getCurrentPlayer()).getTotalCountriesOccupied());
                                getController().RefreshButtons();
                            } else {
                                getController().getFrame().OnlyNeeded(neighbourList);
                                getController().RefreshButtons();
                            }
                        } else if (getController().getAttackCountry2() == null) {
                            getController().setAttackCountry2(country);

                        } else {
                            getController().setAttackCountry1(null);
                            getController().setAttackCountry2(null);
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                    break;
            }

        }

        getController().getBoardFacade().copyInformation();
        getController().getServer().update();
    }


    /**
     * This method controls the action of the buttons
     *
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println("Evento: "+event.getActionCommand());

        Player player = controller.playerObjet(controller.getCurrentPlayer());
        // Si se trata de un boton de terminar fase
        if (controller.getPhaseList().contains(event.getActionCommand())) {

            //Algun boton finish
            switch (event.getActionCommand()) {
                case "Finish Reinforcement":

                    if (player.getPlayerArmiesNotDeployed() > 0) {  //Si quedan mas tropas -> Error
                        getController().getBoardFacade().sendErrorMessage("Cannot End Reinforcement Until All armies are deployed", player);

                        // TODO Remove UI references
                        controller.frame.error("Cannot End Reinforcement Until All armies are deployed");
                        controller.getBoardFacade().sendErrorMessage("Cannot End Reinforcement Until All armies are deployed",player);
                        controller.cardTypesList.clear();
                        controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                        controller.getBoardFacade().setSelectedCards(controller.cardTypesList);
                    } else {  //Si no termina fase refuerzo
                        controller.reinforcementController.finishReinforcement(controller);
                    }
                    break;
                case "Finish Attack":
                    controller.attackController.finishattack(controller);
                    break;
                case "Finish Fortification":  //Metodo finishFortification
                    controller.fortificationController.finishFortification(controller);
                    break;
            }

        //Algun boton carta
        } else if (event.getActionCommand().split(" ")[0].equals("Infantry")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                controller.cardTypesList.add(CardTypes.Infantry);

                // TODO borrar referncias graficas
                controller.frame.buttonCard1.setText("Infantry " + (no - 1));
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);

            } else {
                controller.frame.error("No Card Of this Type");
                getController().getBoardFacade().sendErrorMessage("No Card Of this Type", player);
            }

        } else if (event.getActionCommand().split(" ")[0].equals("Cavalry")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                controller.cardTypesList.add(CardTypes.Cavalry);

                // TODO borrar referncias graficas
                controller.frame.buttonCard3.setText("Cavalry " + (no - 1));
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);
            } else {
                controller.frame.error("No Card Of this Type");
                getController().getBoardFacade().sendErrorMessage("No Card Of this Type", player);
            }
        } else if (event.getActionCommand().split(" ")[0].equals("Artillery")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                controller.cardTypesList.add(CardTypes.Artillery);

                // TODO borrar referncias graficas
                controller.frame.buttonCard2.setText("Artillery " + (no - 1));
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);

            } else {
                controller.frame.error("No Card Of this Type");
                getController().getBoardFacade().sendErrorMessage("No Card Of this Type", player);
            }
        } else if (event.getActionCommand().equals("Exchange Cards")) {
            // TODO Integrar exchange cards en la funcionalidad online
            String answer = controller.getReinforcementController().exchangeCards(controller.cardTypesList,
                    controller.playerObjet(controller.getCurrentPlayer()));
            if (answer == "") {
                controller.cardTypesList.clear();
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.frame.noArmiesLeft = controller.playerObjet(controller.getCurrentPlayer()).getPlayerArmiesNotDeployed();
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);

            } else {
                controller.frame.error("Invalid Cards Selected");
                controller.getBoardFacade().sendErrorMessage("Invalid Cards Selected",player);
                controller.cardTypesList.clear();
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                controller.getBoardFacade().setSelectedCards(controller.cardTypesList);

            }
            controller.changed();


        } else if(event.getActionCommand().split(" ")[0].equals("Dice")){
            if(controller.getCurrentPhase().equals("Finish Attack")){
                try {
                    getController().attackController.attackPhase(event.getActionCommand().split(" ")[1],
                            event.getActionCommand().split(" ")[2], getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        // Comando de seleccion de pais
        } else if (event.getActionCommand().split(" ")[0].equals("Reinforce")) {
            if (controller.getCurrentPhase().equals("Finish Fortification")) {
                try {
                    controller.fortificationController.fortificationPhase(event.getActionCommand().split(" ")[1], controller);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {  //LLamada a las logicas de cada fase

            // TODO eliminar la UI
            controller.frame.noArmiesLeft = controller.playerObjet(controller.getCurrentPlayer()).getPlayerArmiesNotDeployed(); //Actualiza las tropas que quedan
            String Cname = event.getActionCommand().split("\\|")[0].trim();
            Country country = controller.countryObjects().get(Cname); //Territorio seleccionado
            switch (controller.getCurrentPhase()) {
                case "Finish Reinforcement":
                    if (controller.playerObjet(controller.getCurrentPlayer()).getPlayerCards().size() >= 5) {
                        // TODO eliminar la UI
                        controller.frame.error("First Exchange Cards");
                        getController().getBoardFacade().sendErrorMessage("First Exchange Cards", player);
                    } else {
                        controller.armiesNotDeployed(country);   //Suma una tropa al territorio
                        try {
                            controller.RefreshButtons();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                case "Finish Fortification":
                    if (getController().getFortifyCountry1() == null) {
                        getController().setFortifyCountry1(country);
                        getController().getFrame().CCC = getController().fortificationController.NeighboursList(country);
                        getController().changed();
                        getController().getFrame().error("Select One More Country You Want to move your Armies to");
                        getController().getBoardFacade().sendErrorMessage("Select One More Country You Want to move your Armies to"
                                ,getController().playerObjet(getController().getCurrentPlayer()));
                    } else if (getController().getFortifyCountry2() == null) {
                        getController().setFortifyCountry2(country);
                        if (getController().getFortifyCountry1().equals(getController().getFortifyCountry2())) {
                            getController().getFrame().error("SAME COUNTRY SELECTED");
                            getController().getBoardFacade().sendErrorMessage("SAME COUNTRY SELECTED",
                                    getController().playerObjet(getController().getCurrentPlayer()));
                            getController().setFortifyCountry1(null);
                            getController().setFortifyCountry2(null);
                        }
                        String test1 = getController().getFrame().popupText(getController().getFortifyCountry1().getNoOfArmies() - 1);  //Pregunta cuantas quiero transferir
                        actionPerformed(new ActionEvent(this, 1, "Reinforce "+test1));
                    }


                    break;
                case "Finish Attack":
                    try {
                        if (getController().getAttackCountry1() == null) {
                            getController().setAttackCountry1(country);
                            getController().getFrame().ActivateAll();
                            List<Country> neighbourList = getController().getAttackController().getMyNeighboursForAttack(country);
                            if (neighbourList.size() < 1) {
                                getController().getFrame().ActivateAll();
                                getController().setAttackCountry1(null);
                                getController().setAttackCountry2(null);
                                getController().getBoardFacade().sendErrorMessage("No Card Of this Type", player);
                                getController().getFrame().error("No Neighbours to attack");
                                getController().getBoardFacade().sendErrorMessage("No Neighbours to attack",getController().playerObjet(getController().getCurrentPlayer()));
                                getController().OnlyNeeded(getController().playerObjet(getController().getCurrentPlayer()).getTotalCountriesOccupied());
                                getController().RefreshButtons();
                            } else {
                                getController().getFrame().OnlyNeeded(neighbourList);
                                getController().RefreshButtons();
                            }
                        } else if (getController().getAttackCountry2() == null) {
                            getController().setAttackCountry2(country);

                            // TODO remove this bit of code
                            try {
                                boolean allout;
                                int dice1 = -1, dice2 = -1;
                                allout = getController().getFrame().Allout();
                                if (allout == true) {

                                } else {
                                    dice1 = Integer.parseInt(
                                            getController().getFrame().popupTextNew("Enter No of Dices for player 1 --Minimum: 1 Maximum: "
                                                    + getController().getAttackController().setNoOfDice(getController().getAttackCountry1(), 'A')));
                                    System.out.println("Dice 1 : "+ Integer.toString(dice1));
                                    dice2 = Integer.parseInt(
                                            getController().getFrame().popupTextNew("Enter No of Dices for player 2 --Minimum: 1 Maximum: "
                                                    + getController().getAttackController().setNoOfDice(getController().getAttackCountry2(), 'D')));
                                    System.out.println("Dice 2 : "+ Integer.toString(dice2));
                                }
                                if(allout) dice1 = -2;
                                actionPerformed(new ActionEvent(this, 1, "Dice "+Integer.toString(dice1)+" A"));
                                actionPerformed(new ActionEvent(this, 1, "Dice "+Integer.toString(dice2)+" D"));

                            } catch (Exception e) {
                                e.printStackTrace();
                                getController().getFrame().error("Invalid Entry Try again");
                                getController().getBoardFacade().sendErrorMessage("Invalid Entry Try again",getController().playerObjet(getController().getCurrentPlayer()));
                                getController().getFrame().ActivateAll();
                                getController().setAttackCountry1(null);
                                getController().setAttackCountry2(null);
                                getController().OnlyNeeded(getController().playerObjet(getController().getCurrentPlayer()).getTotalCountriesOccupied());
                                getController().RefreshButtons();
                            }
                            // till here

                        } else {
                            getController().setAttackCountry1(null);
                            getController().setAttackCountry2(null);
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                    break;
            }

        }

        getController().getServer().send();

    }

    public ServerController getController() {
        return controller;
    }
}
