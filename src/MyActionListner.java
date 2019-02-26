import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

public class MyActionListner implements ActionListener {
	MFrame frame;
	MainControll controll;

	public MyActionListner(MainControll controll) throws IOException {
		this.controll = controll;

	}
	/*
	 * Player playerPlaying;
	 */

	public void dosomething(JButton button) throws IOException {
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		if (e.getSource() == "") {
		} else {
			try {

				String Cname = e.getActionCommand().split(" | ")[0];
				JButton temp = controll.frame.hashButton.get(Cname);
				dosomething(temp);
				
				controll.ChangePlayerCountry(Cname);
				

				
				
				
				
				
				
				
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		// System.out.println(country.getOwner().getPlayerId());
		// System.out.println(controll.countryObjects());
		/*
		 * Country abc =
		 * controll.files.playerId.get(0).getTotalCountriesOccupied().get(0);
		 * abc.setPlayer(controll.files.playerId.get(1));
		 */// controll.files.playerId.get(0).getTotalCountriesOccupied().remove(abc);
			// controll.files.playerId.get(1).getTotalCountriesOccupied().add(abc);

		// controll.frame.hashButton.get(e.getActionCommand().split("\n")[0]).setEnabled(false);

	}

}
