package controller.game;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

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
    private final MainController controller;
    private int players = 0;

    /**
     * Builder of the class
     *
     * @param controller
     */
    public MyActionListener(MainController controller) {
        this.controller = controller;
        controller.setPhaseList(new ArrayList<>());
        controller.getPhaseList().add("Finish Reinforcement");
        controller.getPhaseList().add("Finish Attack");
        controller.getPhaseList().add("Finish Fortification");
        controller.setCurrentPhase(controller.getPhaseList().get(0));
        players = controller.PlayerNo();
    }


    public void clientActionPerformed(ClientUpdate.ClientAction event) {
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
                        controller.cardTypesList.clear();
                        controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                    } else {  //Si no termina fase refuerzo
                        controller.finishReinforcement();
                    }
                    break;
                case "Finish Attack":
                    controller.finishattack();
                    break;
                case "Finish Fortification":  //Metodo finishFortification??
                    controller.finishFortification();
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

            } else {
                controller.frame.error("Invalid Cards Selected");
                controller.cardTypesList.clear();
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());

            }
            controller.changed();

            // Comando de seleccion de pais
        } else {  //LLamada a las logicas de cada fase

            // TODO eliminar la UI
            controller.frame.noArmiesLeft = controller.playerObjet(controller.getCurrentPlayer()).getPlayerArmiesNotDeployed(); //Actualiza las tropas que quedan
            String Cname = event.getActionCommand().split("\\|")[0].trim();
            Country temp2 = controller.countryObjects().get(Cname); //Territorio seleccionado
            switch (controller.getCurrentPhase()) {
                case "Finish Reinforcement":
                    if (controller.playerObjet(controller.getCurrentPlayer()).getPlayerCards().size() >= 5) {
                        // TODO eliminar la UI
                        controller.frame.error("First Exchange Cards");
                        getController().getBoardFacade().sendErrorMessage("First Exchange Cards", player);
                    } else {
                        controller.armiesNotDeployed(temp2);   //Suma una tropa al territorio
                        try {
                            controller.RefreshButtons();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                case "Finish Fortification":
                    try {
                        controller.fortificationController.fortificationPhase(temp2, controller);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                    break;
                case "Finish Attack":
                    try {
                        controller.attackController.attackPhase(temp2, controller);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                    break;
            }

        }
        controller.getServer().update();

    }


    /**
     * This method controls the action of the buttons
     *
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
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
                        controller.cardTypesList.clear();
                        controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                    } else {  //Si no termina fase refuerzo
                        controller.finishReinforcement();
                    }
                    break;
                case "Finish Attack":
                    controller.finishattack();
                    break;
                case "Finish Fortification":  //Metodo finishFortification??
                    controller.finishFortification();
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

            } else {
                controller.frame.error("Invalid Cards Selected");
                controller.cardTypesList.clear();
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());

            }
            controller.changed();

        // Comando de seleccion de pais
        } else {  //LLamada a las logicas de cada fase

            // TODO eliminar la UI
            controller.frame.noArmiesLeft = controller.playerObjet(controller.getCurrentPlayer()).getPlayerArmiesNotDeployed(); //Actualiza las tropas que quedan
            String Cname = event.getActionCommand().split("\\|")[0].trim();
            Country temp2 = controller.countryObjects().get(Cname); //Territorio seleccionado
            switch (controller.getCurrentPhase()) {
                case "Finish Reinforcement":
                    if (controller.playerObjet(controller.getCurrentPlayer()).getPlayerCards().size() >= 5) {
                        // TODO eliminar la UI
                        controller.frame.error("First Exchange Cards");
                        getController().getBoardFacade().sendErrorMessage("First Exchange Cards", player);
                    } else {
                        controller.armiesNotDeployed(temp2);   //Suma una tropa al territorio
                        try {
                            controller.RefreshButtons();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                case "Finish Fortification":
                    try {
                        controller.fortificationController.fortificationPhase(temp2, controller);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                    break;
                case "Finish Attack":
                    try {
                        controller.attackController.attackPhase(temp2, controller);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                    break;
            }

        }

    }

    public MainController getController() {
        return controller;
    }
}
