import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;

public class MyActionListner implements ActionListener {
	MFrame frame;
	MainControll controll;
	List<String> Phases;
	String currentPhase;
	int players = 6;
	int tem=10;
	int currentPlayer = 0;

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

	public void ReinforcementPhase() {
		controll.AddArmies(currentPlayer);
	}

	public void FortificationPhase() {
		playerUpdate();

	}

	public void AttackPhase() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.toString());
		// TODO Auto-generated method stub
		if (Phases.contains(e.getActionCommand())) {
			if (e.getActionCommand() == "Finish Reinforcement") {
				currentPhase = "Finish Attack";
				AttackPhase();
			} else if (e.getActionCommand() == "Finish Attack") {
				currentPhase = "Finish Fortification";
				FortificationPhase();
			} else if (e.getActionCommand() == "Finish Fortification") {
				currentPhase = "Finish Reinforcement";
				ReinforcementPhase();
			}

		} else {
			String[] Cname2 = e.getActionCommand().split("\\|");
			String Cname = e.getActionCommand().split("\\|")[0].trim();
			JButton temp = controll.frame.hashButton.get(Cname);
			Country temp2 = controll.countryObjects().get(Cname);
			if (currentPhase.equals("Finish Reinforcement")) {
				temp2.setNoOfArmies(temp2.getNoOfArmies()+1);
				try {
					controll.RefreshButtons();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}

			// controll.OnlyNeeded(controll.attackController.getMyNeighborsForAttack(temp2));

		}

	}

}
