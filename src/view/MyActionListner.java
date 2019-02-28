package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import model.Country;

public class MyActionListner implements ActionListener {
	MFrame frame;
	MainControll controll;
	List<String> Phases;
	String currentPhase;
	int players = 6;
	int tem = 10;
	int currentPlayer = 0;
	Country attackCountry1, attackCountry2;
	Country fortifyCountry1, fortifyCountry2;

	public MyActionListner(MainControll controll) throws IOException {
		this.controll = controll;
		Phases = new ArrayList<>();
		Phases.add("Finish Reinforcement");
		Phases.add("Finish Attack");
		Phases.add("Finish Fortification");
		currentPhase = Phases.get(0);
	}

	public void playerUpdate() {
		if (currentPlayer >= players - 1)
			currentPlayer = 0;
		else
			currentPlayer++;
	}

	public void ReinforcementPhase2(Country country) {
		// controll.AddArmies(currentPlayer);
		String message = controll.reinforcementController.addarmies(country);
		controll.frame.noArmiesLeft = country.getOwner().getPlayerArmiesNotDeployed();
		controll.frame.NotifyAll();
		if (!message.equals(""))
			controll.frame.error(message);
	}

	public void ReinforcementPhase() {
		// controll.AddArmies(currentPlayer);
		controll.frame.ActivateAll();
		controll.frame.NotifyAll();
		controll.OnlyNeeded(controll.playerObjet(currentPlayer).getTotalCountriesOccupied());
		controll.reinforcementController.calculateReinforcementArmies(controll.playerObjet(currentPlayer));
	}

	public void FortificationPhase() {
		controll.frame.ActivateAll();
		controll.OnlyNeeded(controll.playerObjet(currentPlayer).getTotalCountriesOccupied());
		// controll.fortificationController.addNodes();
		// controll.fortificationController.addAllEdge();
		playerUpdate();
	}

	public void FortificationPhase2(Country country) throws IOException {

		if (fortifyCountry1 == null) {
			fortifyCountry1 = country;
			controll.frame.CCC = controll.NeighboursList(country);
			controll.frame.NotifyAll();
			controll.frame.error("Select One More Country You Want to move your Armies to");
		} else if (fortifyCountry2 == null) {
			fortifyCountry2 = country;
			String test1 = controll.frame.popupText(fortifyCountry1.getNoOfArmies() - 1);
			String message = controll.fortificationController.moveArmies(fortifyCountry1, fortifyCountry2,
					Integer.parseInt(test1));
			if (!message.equals("")) {
				controll.frame.error(message);
				fortifyCountry1 = null;
				fortifyCountry2 = null;
			} else {
				controll.RefreshButtons();
				currentPhase = "Finish Reinforcement";
				controll.frame.nextAction.setText("Finish Reinforcement");
				playerUpdate();
				controll.frame.ActivateAll();
				ReinforcementPhase();
			}

		}

	}

	public void AttackPhase(Country country) throws IOException {
		if (attackCountry1 == null) {
			attackCountry1 = country;
			controll.frame.ActivateAll();
			List<Country> abc = controll.attackController.getMyNeighborsForAttack(country);
			controll.frame.OnlyNeeded(abc);
			controll.RefreshButtons();

		} else if (attackCountry2 == null) {
			attackCountry2 = country;
			String reply = controll.attackController.attackButton(attackCountry1, attackCountry2);
			if (!reply.equals("")) {
				controll.frame.error(reply);
			}
			controll.frame.AAA = controll.attackController.attackerDiceRoll.toString();
			controll.frame.BBB = controll.attackController.defenderDiceRoll.toString();
			controll.frame.NotifyAll();
			controll.frame.ActivateAll();
			attackCountry1 = null;
			attackCountry2 = null;
			controll.OnlyNeeded(controll.playerObjet(currentPlayer).getTotalCountriesOccupied());
			controll.RefreshButtons();

		} else {
			attackCountry1 = null;
			attackCountry2 = null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.toString());
		// TODO Auto-generated method stub
		if (Phases.contains(e.getActionCommand())) {
			if (e.getActionCommand() == "Finish Reinforcement") {
			if(controll.playerObjet(currentPlayer).getPlayerArmiesNotDeployed()>0) {
				controll.frame.error("Connot End Reinforcement Untill All armies are deployed");
			}else {
				
				currentPhase = "Finish Attack";
				controll.frame.nextAction.setText("Finish Attack");
			} 

			} else if (e.getActionCommand() == "Finish Attack") {
				currentPhase = "Finish Fortification";
				controll.frame.nextAction.setText("Finish Fortification");
				FortificationPhase();
			} else if (e.getActionCommand() == "Finish Fortification") {
				currentPhase = "Finish Reinforcement";
				controll.frame.nextAction.setText("Finish Reinforcement");
				ReinforcementPhase();
			}

		} else {
			String Cname = e.getActionCommand().split("\\|")[0].trim();
			// JButton temp = controll.frame.hashButton.get(Cname);
			Country temp2 = controll.countryObjects().get(Cname);
			if (currentPhase.equals("Finish Reinforcement")) {
				ReinforcementPhase2(temp2);
				try {
					controll.RefreshButtons();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else if (currentPhase.equals("Finish Fortification")) {
				try {
					FortificationPhase2(temp2);
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			} else if (currentPhase.equals("Finish Attack")) {
				try {
					AttackPhase(temp2);
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			}

		}

	}

}
