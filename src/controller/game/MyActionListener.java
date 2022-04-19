package controller.game;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import model.CardTypes;
import model.Country;

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


    /**
     * This method controls the action of the buttons
     *
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // TODO Auto-generated method stub
        if (controller.getPhaseList().contains(event.getActionCommand())) {

            //Algun boton finish
            if (event.getActionCommand() == "Finish Reinforcement") {
                if (controller.playerObjet(controller.getCurrentPlayer()).getPlayerArmiesNotDeployed() > 0) {  //Si quedan mas tropas -> Error
                    controller.frame.error("Cannot End Reinforcement Untill All armies are deployed");
                    controller.cardTypesList.clear();
                    controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
                } else {  //Si no termina fase refuerzo
                    controller.finishReinforcement();
                }
            } else if (event.getActionCommand() == "Finish Attack") {
                controller.finishattack();
            } else if (event.getActionCommand() == "Finish Fortification") { //Metodo finishFortification??
                controller.finishFortification();
            }

            //Algun boton carta
        } else if (event.getActionCommand().split(" ")[0].equals("Infantry")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                controller.cardTypesList.add(CardTypes.Infantry);
                controller.frame.buttonCard1.setText("Infantry " + (no - 1));
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());

            } else {
                controller.frame.error("No Card Of this Type");
            }

        } else if (event.getActionCommand().split(" ")[0].equals("Cavalry")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                controller.cardTypesList.add(CardTypes.Cavalry);
                controller.frame.buttonCard3.setText("Cavalry " + (no - 1));
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());
            } else {
                controller.frame.error("No Card Of this Type");
            }
        } else if (event.getActionCommand().split(" ")[0].equals("Artillery")) {
            int no = Integer.parseInt(event.getActionCommand().split(" ")[1]);
            if (no > 0) {
                controller.cardTypesList.add(CardTypes.Artillery);
                controller.frame.buttonCard2.setText("Artillery " + (no - 1));
                controller.frame.jLabeCardl.setText(controller.cardTypesList.toString());

            } else {
                controller.frame.error("No Card Of this Type");
            }
        } else if (event.getActionCommand().equals("Exchange Cards")) {
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

        } else {  //LLamada a las logicas de cada fase
            controller.frame.noArmiesLeft = controller.playerObjet(controller.getCurrentPlayer()).getPlayerArmiesNotDeployed(); //Actualiza las tropas que quedan
            String Cname = event.getActionCommand().split("\\|")[0].trim();
            Country temp2 = controller.countryObjects().get(Cname); //Territorio seleccionado
            if (controller.getCurrentPhase().equals("Finish Reinforcement")) {
                if (controller.playerObjet(controller.getCurrentPlayer()).getPlayerCards().size() >= 5) {
                    controller.frame.error("First Exchange Cards");
                } else {
                    controller.armiesNotDeployed(temp2);   //Suma una tropa al territorio
                    try {
                        controller.RefreshButtons();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else if (controller.getCurrentPhase().equals("Finish Fortification")) {
                try {
                    controller.fortificationPhase(temp2);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

            } else if (controller.getCurrentPhase().equals("Finish Attack")) {
                try {
                    controller.attackPhase(temp2);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

            }

        }

    }

    public MainController getController() {
        return controller;
    }
}
