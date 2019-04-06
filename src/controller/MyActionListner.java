package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import model.CardTypes;
import model.Country;
import model.Player;
import view.*;

/**
 * This class handles events of the User Interface
 *
 * @author pazim
 * @version 3.o
 */
@SuppressWarnings("deprecation")
public class MyActionListner extends Observable implements ActionListener {
	boolean gotCard = false;
	MFrame frame;
	MainController controller;
	List<String> Phases;
	String currentPhase;
	int players = 0;
	public int currentPlayer = 0;
	Country attackCountry1, attackCountry2;
	Country fortifyCountry1, fortifyCountry2;
	public int cardsSelected = 0;
	List<CardTypes> cardTypesList = new ArrayList<CardTypes>();

	public MyActionListner(MainController controller) {
		this.controller = controller;
		Phases = new ArrayList<>();
		Phases.add("Finish Reinforcement");
		Phases.add("Finish Attack");
		Phases.add("Finish Fortification");
		currentPhase = Phases.get(0);
		players = controller.PlayerNo();
	}

	public void playerUpdate() {
		try {

			if (controller.PlayerNo2() > 1) {
				if (currentPlayer >= controller.PlayerNo2() - 1)
					currentPlayer = 0;
				else
					currentPlayer++;
				if (!controller.files.playerId2.containsKey(currentPlayer)) {
					playerUpdate();
				}
			} else {
				controller.frame.error("YOU WON");
				String[] args = { "" };
				GameStartWindow.main(args);
				controller.frame.dispose();
			}
		} catch (Exception e) {
			playerUpdate();
		}
	}

	/**
	 * This method display the armies that are not deployed
	 *
	 * @param country:
	 *            object
	 */
	public void ReinforcementPhase2(Country country) {
		cardTypesList.clear();
		controller.frame.jLabeCardl.setText(cardTypesList.toString());
		// controll.AddArmies(currentPlayer);
		String message = controller.playerObjet(currentPlayer).addarmies(country);
		controller.frame.noArmiesLeft = controller.playerObjet(currentPlayer).getPlayerArmiesNotDeployed();
		changed();
		if (!message.equals(""))
			controller.frame.error(message);
	}

	/**
	 * This method display number of armies player can deploy
	 */
	public void PhaseResume(String phase) {
		if (phase.equals("Finish Reinforcement")) {
			ReinforcementPhase();
		} else if (phase.equals("Finish Attack")) {
			controller.frame.ActivateAll();
			controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
			endReinforcement();
		} else if (phase.equals("Finish Fortification")) {
			controller.frame.ActivateAll();
			controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
			finishAttack();
		}
	}

	public void ReinforcementPhase() {
		try {
			controller.RefreshButtons();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Player pl = controller.playerObjet(currentPlayer);
		textarea("Player Playing :- " + pl);
		String stratergy = pl.getStatergy().trim();
		if (stratergy.equals("Agressive")) {
			textarea(" Reinforcement Mode");

			pl.aggressiveStratergy.reinforce(pl);
			textarea(" Attack Mode");
			pl.aggressiveStratergy.attack(pl);
			textarea(" Fortification Mode");
			pl.aggressiveStratergy.fortify(pl);
			playerUpdate();
			changed();
			ReinforcementPhase();
		} else if (stratergy.equals("Benevolent")) {
			System.out.println("player2-");
			textarea("Benevolent Stratergy");
			textarea(" Reinforcement Mode");
			pl.benevolentStrategy.reinforce(pl);
			textarea(" Attack Mode");
			pl.benevolentStrategy.attack(pl);
			textarea(" Fortification Mode");
			pl.benevolentStrategy.fortify(pl);
			playerUpdate();
			changed();
			ReinforcementPhase();
		} else if (stratergy.equals("Random")) {
			textarea(" Reinforcement Mode");

			pl.randomStrategy.reinforce(pl);
			textarea(" Attack Mode");

			pl.randomStrategy.attack(pl);
			textarea(" Fortification Mode");
			pl.randomStrategy.fortify(pl);
			playerUpdate();
			changed();
			ReinforcementPhase();
		} else if (stratergy.equals("Cheater")) {
			textarea("Benevolent Stratergy");
			pl.cheaterStrategy.reinforce(pl);
			pl.cheaterStrategy.attack(pl);
			pl.cheaterStrategy.fortify(pl);
			playerUpdate();
			changed();
			ReinforcementPhase();
		} else if (stratergy.equals("Human")) {
			textarea(" Reinforcement Mode");
			controller.frame.error("Its Player:- " + (currentPlayer + 1) + " Turn");
			controller.frame.buttonCard4.setEnabled(true);
			controller.frame.buttonCard3.setEnabled(true);
			controller.frame.buttonCard2.setEnabled(true);
			controller.frame.buttonCard1.setEnabled(true);
			changed();
			controller.frame.ActivateAll();
			controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
			controller.playerObjet(currentPlayer).calculateReinforcementArmies(controller.playerObjet(currentPlayer));
		}
	}

	/**
	 * This method
	 */
	public void FortificationPhase() {
		textarea(" Fortification Mode");

		AttackController.card = false;
		changed();
		controller.frame.ActivateAll();
		controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
		playerUpdate();
	}

	/**
	 * This method does the validations of fortification phase
	 *
	 * @param country:
	 *            country object
	 * @throws IOException
	 */
	public void FortificationPhase2(Country country) throws IOException {
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
					String test1 = controller.frame.popupText(fortifyCountry1.getNoOfArmies() - 1);
					String message = controller.playerObjet(currentPlayer).moveArmies(fortifyCountry1, fortifyCountry2,
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
						ReinforcementPhase();
					}
				} catch (Exception e) {
					// TODO: handle exception
					controller.frame.error("Invalid Number");
				}
			}
		}
	}

	/**
	 * This method check validations of attack phase
	 *
	 * @param country:
	 *            object
	 * @throws IOException
	 */
	public void AttackPhase(Country country) throws IOException {
		cardTypesList.clear();
		controller.frame.jLabeCardl.setText(cardTypesList.toString());
		changed();
		if (attackCountry1 == null) {
			attackCountry1 = country;
			controller.frame.ActivateAll();
			List<Country> abc = controller.playerObjet(currentPlayer).getMyNeighborsForAttack(country);
			if (abc.size() < 1) {
				controller.frame.ActivateAll();
				attackCountry1 = null;
				attackCountry2 = null;
				controller.frame.error("No Neighbours to attack");
				controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
				controller.RefreshButtons();
			} else {
				controller.frame.OnlyNeeded(abc);
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
									+ controller.playerObjet(currentPlayer).setNoOfDice(attackCountry1, 'A')));
					dice2 = Integer.parseInt(
							controller.frame.popupTextNew("Enter No of Dices for player 2 --Minimum: 1 Maximum: "
									+ controller.playerObjet(currentPlayer).setNoOfDice(attackCountry2, 'D')));
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
				GameStartWindow gameStartWindow = new GameStartWindow();
				String args[] = { "" };
				gameStartWindow.main(args);
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
			boolean result = controller.playerObjet(currentPlayer).canAttack(controller.playerObjet(currentPlayer));
			if (!result) {
				controller.frame.buttonCard4.setEnabled(false);
				changed();
				currentPhase = "Finish Fortification";
				controller.frame.nextAction.setText("Finish Fortification");
				fortifyCountry1 = null;
				fortifyCountry2 = null;
				FortificationPhase();
			}

		} else {
			attackCountry1 = null;
			attackCountry2 = null;
		}
	}

	public void endReinforcement() {
		cardTypesList.clear();
		controller.frame.buttonCard4.setEnabled(false);
		controller.frame.buttonCard3.setEnabled(false);
		controller.frame.buttonCard2.setEnabled(false);
		controller.frame.buttonCard1.setEnabled(false);
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

	public void finishAttack() {
		controller.frame.buttonCard4.setEnabled(false);
		controller.frame.buttonCard3.setEnabled(false);
		controller.frame.buttonCard2.setEnabled(false);
		controller.frame.buttonCard1.setEnabled(false);
		changed();
		currentPhase = "Finish Fortification";
		controller.frame.nextAction.setText("Finish Fortification");
		fortifyCountry1 = null;
		fortifyCountry2 = null;
		cardTypesList.clear();
		controller.frame.jLabeCardl.setText(cardTypesList.toString());
		FortificationPhase();
		cardTypesList.clear();
		controller.frame.jLabeCardl.setText(cardTypesList.toString());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (Phases.contains(e.getActionCommand())) {
			if (e.getActionCommand() == "Finish Reinforcement") {
				if (controller.playerObjet(currentPlayer).getPlayerArmiesNotDeployed() > 0) {
					controller.frame.error("Connot End Reinforcement Untill All armies are deployed");
					cardTypesList.clear();
					controller.frame.jLabeCardl.setText(cardTypesList.toString());
				} else {
					// cardTypesList.clear();
					// controller.frame.buttonCard4.setEnabled(false);
					// controller.frame.buttonCard3.setEnabled(false);
					// controller.frame.buttonCard2.setEnabled(false);
					// controller.frame.buttonCard1.setEnabled(false);
					// currentPhase = "Finish Attack";
					// controller.frame.nextAction.setText("Finish Attack");
					// changed();
					// attackCountry1 = null;
					// attackCountry2 = null;
					// cardTypesList.clear();
					// controller.frame.jLabeCardl.setText(cardTypesList.toString());
					// cardTypesList.clear();
					// controller.frame.jLabeCardl.setText(cardTypesList.toString());
					endReinforcement();
				}

			} else if (e.getActionCommand() == "Finish Attack") {
				// controller.frame.buttonCard4.setEnabled(false);
				// controller.frame.buttonCard3.setEnabled(false);
				// controller.frame.buttonCard2.setEnabled(false);
				// controller.frame.buttonCard1.setEnabled(false);
				// changed();
				// currentPhase = "Finish Fortification";
				// controller.frame.nextAction.setText("Finish Fortification");
				// fortifyCountry1 = null;
				// fortifyCountry2 = null;
				// cardTypesList.clear();
				// controller.frame.jLabeCardl.setText(cardTypesList.toString());
				// FortificationPhase();
				// cardTypesList.clear();
				// controller.frame.jLabeCardl.setText(cardTypesList.toString());
				finishAttack();
			} else if (e.getActionCommand() == "Finish Fortification") {
				controller.frame.buttonCard4.setEnabled(true);
				controller.frame.buttonCard3.setEnabled(true);
				controller.frame.buttonCard2.setEnabled(true);
				controller.frame.buttonCard1.setEnabled(true);
				changed();
				currentPhase = "Finish Reinforcement";
				controller.frame.nextAction.setText("Finish Reinforcement");
				cardTypesList.clear();
				controller.frame.jLabeCardl.setText(cardTypesList.toString());
				ReinforcementPhase();
				cardTypesList.clear();
				controller.frame.jLabeCardl.setText(cardTypesList.toString());
			}
		} else if (e.getActionCommand().split(" ")[0].equals("Infantry")) {
			int no = Integer.parseInt(e.getActionCommand().split(" ")[1]);
			if (no > 0) {
				cardTypesList.add(CardTypes.Infantry);
				controller.frame.buttonCard1.setText("Infantry " + (no - 1));
				controller.frame.jLabeCardl.setText(cardTypesList.toString());

			} else {
				controller.frame.error("No Card Of this Type");
			}

		} else if (e.getActionCommand().split(" ")[0].equals("Cavalry")) {
			int no = Integer.parseInt(e.getActionCommand().split(" ")[1]);
			if (no > 0) {
				cardTypesList.add(CardTypes.Cavalry);
				controller.frame.buttonCard3.setText("Cavalry " + (no - 1));
				controller.frame.jLabeCardl.setText(cardTypesList.toString());
			} else {
				controller.frame.error("No Card Of this Type");
			}
		} else if (e.getActionCommand().split(" ")[0].equals("Artillery")) {
			int no = Integer.parseInt(e.getActionCommand().split(" ")[1]);
			if (no > 0) {
				cardTypesList.add(CardTypes.Artillery);
				controller.frame.buttonCard2.setText("Artillery " + (no - 1));
				controller.frame.jLabeCardl.setText(cardTypesList.toString());

			} else {
				controller.frame.error("No Card Of this Type");
			}
		} else if (e.getActionCommand().equals("Exchange Cards")) {
			String answer = controller.playerObjet(currentPlayer).exchangeCards(cardTypesList,
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

		} else {
			controller.frame.noArmiesLeft = controller.playerObjet(currentPlayer).getPlayerArmiesNotDeployed();
			String Cname = e.getActionCommand().split("\\|")[0].trim();
			Country temp2 = controller.countryObjects().get(Cname);
			if (currentPhase.equals("Finish Reinforcement")) {
				if (controller.playerObjet(currentPlayer).getPlayerCards().size() >= 5) {
					controller.frame.error("First Exchange Cards");

				} else {

					ReinforcementPhase2(temp2);
					try {
						controller.RefreshButtons();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
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

	public String getCardsType1() {
		int aaa = 0;
		List<CardTypes> type = controller.playerObjet(currentPlayer).getPlayerCards();
		for (int i = 0; i < type.size(); i++) {
			int x = type.get(i).compareTo(CardTypes.Infantry);
			if (x == 0) {
				aaa += 1;
			}

		}

		return "" + aaa;
	}

	public String getCardsType3() {
		int aaa = 0;
		List<CardTypes> type = controller.playerObjet(currentPlayer).getPlayerCards();
		for (int i = 0; i < type.size(); i++) {
			int x = type.get(i).compareTo(CardTypes.Cavalry);
			if (x == 0) {
				aaa += 1;
			}

		}

		return "" + aaa;
	}

	public String getCardsType2() {
		int aaa = 0;
		List<CardTypes> type = controller.playerObjet(currentPlayer).getPlayerCards();
		for (int i = 0; i < type.size(); i++) {
			int x = type.get(i).compareTo(CardTypes.Artillery);
			if (x == 0) {
				aaa += 1;
			}
		}
		return "" + aaa;
	}

	public String getSelectedCards() {
		return cardTypesList.toString();
	}

	public ArrayList<Float> CountriesPercentage() {
		return controller.CountriesPercentage();
	}

	public ArrayList<String> ContinentsOccupied() {
		return controller.ContinentsOccupied();
	}

	public int getArmiesPerPlayer() {
		return controller.attackController.getTotalCountries(controller.playerObjet(currentPlayer));
	}

	public void SaveGameOnExit() {
		try {
			File file = new File("Resources/SaveGame.txt");
			FileWriter writer = new FileWriter(file);
			writer.write(controller.files.address + "\n");
			writer.write(controller.files.playerId.size() + "\n");
			writer.write(currentPlayer + "\n");
			writer.write(currentPhase + "\n");
			writer.write(controller.frame.area.getText());
			for (int i = 0; i < controller.PlayerNo2(); i++) {
				Player tempPlayer = controller.files.playerId2.get(i);
				writer.write("----PLAYER----\n");
				writer.write(tempPlayer.getPlayerId() + "\n");
				writer.write(tempPlayer.getStatergy() + "\n");
				for (int j = 0; j < tempPlayer.getTotalCountriesOccupied().size(); j++) {
					Country tempCountry = tempPlayer.getTotalCountriesOccupied().get(j);
					writer.write(tempCountry.getName() + "***" + tempCountry.getNoOfArmies() + "\n");
				}
				writer.write("----CARDS----\n");
				writer.write(tempPlayer.getPlayerCards().toString() + "\n");
			}
			writer.close();
			// FileOutputStream fileOut = new FileOutputStream("Resources/employee.ser");
			// ObjectOutputStream out = new ObjectOutputStream(fileOut);
			// for (int i = 0; i < controller.PlayerNo2(); i++) {
			// Player tempPlayer = controller.files.playerId2.get(i);
			// out.writeObject(tempPlayer);
			// }
			// out.close();
			// fileOut.close();
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void textarea(String string) {
		controller.frame.area.append("\n" + string);
	}

	public void changed() {
		setChanged();
		notifyObservers();
	}

}
