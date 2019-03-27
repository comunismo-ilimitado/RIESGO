package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import java.util.*;

import model.CardTypes;
import model.Country;
import view.*;

/**
 * This class handles events of the User Interface
 * 
 * @author pazim and bhargav
 * @version 1.o
 */

@SuppressWarnings("deprecation")
public class MyActionListner extends Observable implements ActionListener {
	boolean gotCard = false;

	MFrame frame;
	MainControll controll;
	List<String> Phases;
	String currentPhase;
	int players = 0;
	public int currentPlayer = 0;
	Country attackCountry1, attackCountry2;
	Country fortifyCountry1, fortifyCountry2;
	public int cardsSelected = 0;
	List<CardTypes> cardTypesList = new ArrayList<CardTypes>();

	public MyActionListner(MainControll controll) {
		this.controll = controll;

		Phases = new ArrayList<>();
		Phases.add("Finish Reinforcement");
		Phases.add("Finish Attack");
		Phases.add("Finish Fortification");
		currentPhase = Phases.get(0);
		players = controll.PlayerNo();
	}

	public void playerUpdate() {
		try {
			if (controll.PlayerNo2() > 1) {
				if (currentPlayer >= controll.PlayerNo2() - 1)
					currentPlayer = 0;
				else
					currentPlayer++;
				if (!controll.files.playerId2.containsKey(currentPlayer)) {
					playerUpdate();
				}
			} else {
				controll.frame.error("YOU WON");
				String[] args = { "" };

				StartUpWindow.main(args);
				controll.frame.dispose();

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
		// controll.AddArmies(currentPlayer);
		String message = controll.playerObjet(currentPlayer).addarmies(country);
		controll.frame.noArmiesLeft = controll.playerObjet(currentPlayer).getPlayerArmiesNotDeployed();
		changed();
		if (!message.equals(""))
			controll.frame.error(message);
	}

	/**
	 * This method display number of armies player can deploy
	 */
	public void ReinforcementPhase() {
		changed();
		controll.frame.ActivateAll();
		controll.OnlyNeeded(controll.playerObjet(currentPlayer).getTotalCountriesOccupied());
		controll.playerObjet(currentPlayer).calculateReinforcementArmies(controll.playerObjet(currentPlayer));
	}

	/**
	 * This method
	 */
	public void FortificationPhase() {
		AttackController.card = false;
		changed();
		controll.frame.ActivateAll();
		controll.OnlyNeeded(controll.playerObjet(currentPlayer).getTotalCountriesOccupied());
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

		if (fortifyCountry1 == null) {
			fortifyCountry1 = country;
			controll.frame.CCC = controll.NeighboursList(country);
			changed();
			controll.frame.error("Select One More Country You Want to move your Armies to");
		} else if (fortifyCountry2 == null) {
			fortifyCountry2 = country;
			if (fortifyCountry1.equals(fortifyCountry2)) {
				controll.frame.error("SAME COUNTRY SELECTED");
				fortifyCountry1 = null;
				fortifyCountry2 = null;

			} else {
				try {
					String test1 = controll.frame.popupText(fortifyCountry1.getNoOfArmies() - 1);
					String message = controll.playerObjet(currentPlayer).moveArmies(fortifyCountry1, fortifyCountry2,
							Integer.parseInt(test1));
					if (!message.equals("")) {
						controll.frame.error(message);
						fortifyCountry1 = null;
						fortifyCountry2 = null;
					} else {
						controll.RefreshButtons();
						currentPhase = "Finish Reinforcement";
						controll.frame.nextAction.setText("Finish Reinforcement");
						// playerUpdate();
						fortifyCountry1 = null;
						fortifyCountry2 = null;
						controll.frame.ActivateAll();
						ReinforcementPhase();
					}
				} catch (Exception e) {
					// TODO: handle exception
					controll.frame.error("Invalid Number");
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

		changed();
		if (attackCountry1 == null) {
			attackCountry1 = country;
			controll.frame.ActivateAll();
			List<Country> abc = controll.playerObjet(currentPlayer).getMyNeighborsForAttack(country);
			if (abc.size() < 1) {
				controll.frame.ActivateAll();
				attackCountry1 = null;
				attackCountry2 = null;
				controll.frame.error("No Neighbours to attack");
				controll.OnlyNeeded(controll.playerObjet(currentPlayer).getTotalCountriesOccupied());
				controll.RefreshButtons();
			} else {
				controll.frame.OnlyNeeded(abc);
				controll.RefreshButtons();
			}
		} else if (attackCountry2 == null) {
			attackCountry2 = country;
			int dice1 = 0;
			int dice2 = 0;
			boolean allout = false;

			try {
				allout = controll.frame.Allout();
				if (allout == true) {

				} else {
					dice1 = Integer.parseInt(
							controll.frame.popupTextNew("Enter No of Dices for player 1 --Minimum: 1 Maximum: "
									+ controll.playerObjet(currentPlayer).setNoOfDice(attackCountry1, 'A')));
					dice2 = Integer.parseInt(
							controll.frame.popupTextNew("Enter No of Dices for player 2 --Minimum: 1 Maximum: "
									+ controll.playerObjet(currentPlayer).setNoOfDice(attackCountry2, 'D')));
				}
			} catch (Exception e) {
				controll.frame.error("Invalid Entry Try again");
				controll.frame.ActivateAll();
				attackCountry1 = null;
				attackCountry2 = null;
				controll.OnlyNeeded(controll.playerObjet(currentPlayer).getTotalCountriesOccupied());
				controll.RefreshButtons();
			}

			String reply = controll.attackController.attackButton(attackCountry1, attackCountry2, dice1, dice2, allout);
			System.out.println(reply);
			if (reply.equals("Player won")) {
				controll.frame.error(reply);
				StartUpWindow startUpWindow = new StartUpWindow();
				String args[] = { "" };
				startUpWindow.main(args);
			} else if (!reply.equals("")) {
				controll.frame.error(reply);
			}

			controll.frame.AAA = controll.attackController.attackerdicerolloutput.toString();
			controll.frame.BBB = controll.attackController.defenderdicerolloutput.toString();
			changed();
			controll.attackController.attackerdicerolloutput.clear();
			controll.attackController.defenderdicerolloutput.clear();
			controll.frame.ActivateAll();
			attackCountry1 = null;
			attackCountry2 = null;
			controll.OnlyNeeded(controll.playerObjet(currentPlayer).getTotalCountriesOccupied());
			controll.RefreshButtons();
			controll.PaintCountries();
			boolean result = controll.playerObjet(currentPlayer).canAttack(controll.playerObjet(currentPlayer));
			if (!result) {
				controll.frame.buttonCard4.setEnabled(false);
				changed();
				currentPhase = "Finish Fortification";
				controll.frame.nextAction.setText("Finish Fortification");
				fortifyCountry1 = null;
				fortifyCountry2 = null;
				FortificationPhase();
			}

		} else {
			attackCountry1 = null;
			attackCountry2 = null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (Phases.contains(e.getActionCommand())) {
			if (e.getActionCommand() == "Finish Reinforcement") {
				if (controll.playerObjet(currentPlayer).getPlayerArmiesNotDeployed() > 0) {
					controll.frame.error("Connot End Reinforcement Untill All armies are deployed");
				} else {
					controll.frame.buttonCard4.setEnabled(false);
					currentPhase = "Finish Attack";
					controll.frame.nextAction.setText("Finish Attack");
					changed();
					attackCountry1 = null;
					attackCountry2 = null;
				}

			} else if (e.getActionCommand() == "Finish Attack") {
				controll.frame.buttonCard4.setEnabled(false);
				changed();
				currentPhase = "Finish Fortification";
				controll.frame.nextAction.setText("Finish Fortification");
				fortifyCountry1 = null;
				fortifyCountry2 = null;
				FortificationPhase();
			} else if (e.getActionCommand() == "Finish Fortification") {
				controll.frame.buttonCard4.setEnabled(true);
				changed();
				currentPhase = "Finish Reinforcement";
				controll.frame.nextAction.setText("Finish Reinforcement");
				ReinforcementPhase();
			}
		} else if (e.getActionCommand().split(" ")[0].equals("Infantry")) {
			int no = Integer.parseInt(e.getActionCommand().split(" ")[1]);
			if (no > 0) {
				cardTypesList.add(CardTypes.Infantry);
				controll.frame.buttonCard1.setText("Infantry " + (no - 1));
				controll.frame.jLabeCardl.setText(cardTypesList.toString());

			} else {
				controll.frame.error("No Card Of this Type");
			}

		} else if (e.getActionCommand().split(" ")[0].equals("Cavalry")) {
			int no = Integer.parseInt(e.getActionCommand().split(" ")[1]);
			if (no > 0) {
				cardTypesList.add(CardTypes.Cavalry);
				controll.frame.buttonCard3.setText("Cavalry " + (no - 1));
				controll.frame.jLabeCardl.setText(cardTypesList.toString());
			} else {
				controll.frame.error("No Card Of this Type");
			}
		} else if (e.getActionCommand().split(" ")[0].equals("Artillery")) {
			int no = Integer.parseInt(e.getActionCommand().split(" ")[1]);
			if (no > 0) {
				cardTypesList.add(CardTypes.Artillery);
				controll.frame.buttonCard2.setText("Artillery " + (no - 1));
				controll.frame.jLabeCardl.setText(cardTypesList.toString());

			} else {
				controll.frame.error("No Card Of this Type");
			}
		} else if (e.getActionCommand().equals("Exchange Cards")) {
			String answer = controll.playerObjet(currentPlayer).exchangeCards(cardTypesList,
					controll.playerObjet(currentPlayer));
			if (answer == "") {
				cardTypesList.clear();
				controll.frame.jLabeCardl.setText(cardTypesList.toString());
				controll.frame.noArmiesLeft = controll.playerObjet(currentPlayer).getPlayerArmiesNotDeployed();

			} else {
				controll.frame.error("Invalid Cards Selected");
				cardTypesList.clear();
				controll.frame.jLabeCardl.setText(cardTypesList.toString());

			}
			changed();

		} else {
			controll.frame.noArmiesLeft = controll.playerObjet(currentPlayer).getPlayerArmiesNotDeployed();
			String Cname = e.getActionCommand().split("\\|")[0].trim();
			Country temp2 = controll.countryObjects().get(Cname);
			if (currentPhase.equals("Finish Reinforcement")) {
				if (controll.playerObjet(currentPlayer).getPlayerCards().size() >= 5) {
					controll.frame.error("First Exchange Cards");

				} else {
					ReinforcementPhase2(temp2);
					try {
						controll.RefreshButtons();
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
		List<CardTypes> type = controll.playerObjet(currentPlayer).getPlayerCards();
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
		List<CardTypes> type = controll.playerObjet(currentPlayer).getPlayerCards();
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
		List<CardTypes> type = controll.playerObjet(currentPlayer).getPlayerCards();

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
		return controll.CountriesPercentage();
	}

	public ArrayList<String> ContinentsOccupied() {
		return controll.ContinentsOccupied();
	}

	public int getArmiesPerPlayer() {
		return controll.attackController.getTotalCountries(controll.playerObjet(currentPlayer));
	}

	public void changed() {
		setChanged();
		notifyObservers();
	}

}
