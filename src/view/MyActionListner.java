package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import controller.ReadingFiles;
import model.Country;

public class MyActionListner implements ActionListener {
	MFrame frame;
	MainControll controll;
	List<String> phases;
	String current_phase;
	int players = 3;
	int current_player = 0;
	Country attack_country1, attack_country2;
	Country fortify_country1, fortify_country2;

	public MyActionListner(MainControll controll) {
		this.controll = controll;
		phases = new ArrayList<>();
		phases.add("Finish Reinforcement");
		phases.add("Finish Attack");
		phases.add("Finish Fortification");
		current_phase = phases.get(0);
		players=controll.playerNo();
	}

	public void playerUpdate() {
		if (current_player >= players - 1)
			current_player = 0;
		else
			current_player++;
	}

	public void reinforcementPhase2(Country country) {
		// controll.AddArmies(currentPlayer);
		String message = controll.reinforcement_controller.addarmies(country);
		controll.frame.noArmiesLeft = country.getOwner().getPlayerArmiesNotDeployed();
		controll.frame.NotifyAll();
		if (!message.equals(""))
			controll.frame.error(message);
	}

	public void reinforcementPhase() {
		// controll.AddArmies(currentPlayer);
		controll.frame.ActivateAll();
		controll.frame.NotifyAll();
		controll.onlyNeeded(controll.playerObjet(current_player).getTotalCountriesOccupied());
		controll.reinforcement_controller.calculateReinforcementArmies(controll.playerObjet(current_player));
	}

	public void fortificationPhase() {
		controll.frame.ActivateAll();
		controll.onlyNeeded(controll.playerObjet(current_player).getTotalCountriesOccupied());
		playerUpdate();
	}

	public void fortificationPhase2(Country country) throws IOException {

		if (fortify_country1 == null) {
			fortify_country1 = country;
			controll.frame.CCC = controll.neighboursList(country);
			controll.frame.NotifyAll();
			controll.frame.error("Select One More Country You Want to move your Armies to");
		} else if (fortify_country2 == null) {		
			fortify_country2 = country;
			
			if(fortify_country1.equals(fortify_country2)) {
				controll.frame.error("SAME COUNTRY SELECTED");
				fortify_country1 = null;
				fortify_country2 = null;

			}else {
			String test1 = controll.frame.popupText(fortify_country1.getNoOfArmies() - 1);
			String message = controll.fortification_controller.moveArmies(fortify_country1, fortify_country2,
					Integer.parseInt(test1));
			if (!message.equals("")) {
				controll.frame.error(message);
				fortify_country1 = null;
				fortify_country2 = null;
			} else {
				controll.refreshButtons();
				current_phase = "Finish Reinforcement";
				controll.frame.nextAction.setText("Finish Reinforcement");
//				playerUpdate();
				fortify_country1=null;
				fortify_country2=null;
				controll.frame.ActivateAll();
				reinforcementPhase();
			}

		}}

	}

	public void attackPhase(Country country) throws IOException {
		if (attack_country1 == null) {
			attack_country1 = country;
			controll.frame.ActivateAll();
			List<Country> abc = controll.attack_controller.getMyNeighborsForAttack(country);
			controll.frame.OnlyNeeded(abc);
			controll.refreshButtons();

		} else if (attack_country2 == null) {
			attack_country2 = country;
			String reply = controll.attack_controller.attackButton(attack_country1, attack_country2);
			if (!reply.equals("")) {
				controll.frame.error(reply);
			}
			controll.frame.AAA = controll.attack_controller.attackerDiceRoll.toString();
			controll.frame.BBB = controll.attack_controller.defenderDiceRoll.toString();
			controll.frame.NotifyAll();
			controll.frame.ActivateAll();
			attack_country1 = null;
			attack_country2 = null;
			controll.onlyNeeded(controll.playerObjet(current_player).getTotalCountriesOccupied());
			controll.refreshButtons();

		} else {
			attack_country1 = null;
			attack_country2 = null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (phases.contains(e.getActionCommand())) {
			if (e.getActionCommand() == "Finish Reinforcement") {
			if(controll.playerObjet(current_player).getPlayerArmiesNotDeployed()>0) {
				controll.frame.error("Connot End Reinforcement Untill All armies are deployed");
			}else {
				
				current_phase = "Finish Attack";
				controll.frame.nextAction.setText("Finish Attack");
				attack_country1=null;
				attack_country2=null;
			} 

			} else if (e.getActionCommand() == "Finish Attack") {
				current_phase = "Finish Fortification";
				controll.frame.nextAction.setText("Finish Fortification");
				fortify_country1=null;
				fortify_country2=null;
				fortificationPhase();
			} else if (e.getActionCommand() == "Finish Fortification") {
				current_phase = "Finish Reinforcement";
				controll.frame.nextAction.setText("Finish Reinforcement");
				reinforcementPhase();
			}

		} else {
			String Cname = e.getActionCommand().split("\\|")[0].trim();
			// JButton temp = controll.frame.hashButton.get(Cname);
			Country temp2 = controll.countryObjects().get(Cname);
			if (current_phase.equals("Finish Reinforcement")) {
				reinforcementPhase2(temp2);
				try {
					controll.refreshButtons();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else if (current_phase.equals("Finish Fortification")) {
				try {
					fortificationPhase2(temp2);
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			} else if (current_phase.equals("Finish Attack")) {
				try {
					attackPhase(temp2);
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			}

		}

	}

}