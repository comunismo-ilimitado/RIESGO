package view;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

import controller.MFrame;
import model.Country;
import model.Player;

public class Reinforcement {
	MFrame frame;
	Player playerPlaying;

	public void clicking() throws IOException {

		frame = new MFrame();
		frame.fun();

		Collection<JButton> a = frame.hashButton.values();
		for (int i = 0; i < 3; i++) {
			Player player = frame.files.playerId.get(i);
//			System.out.println(player);
			List<Country> alist = frame.files.playerId.get(i).getTotalCountriesOccupied();
	//		System.out.println(alist);
			for (int j = 0; j < alist.size(); j++) {
				frame.hashButton.get(alist.get(j).getName()).setBackground(player.getPlayerColor());
				frame.hashButton.get(alist.get(j).getName()).setEnabled(true);
			}
			
			
			playerPlaying=frame.files.playerId.get(1);
			
		}
	}

}
