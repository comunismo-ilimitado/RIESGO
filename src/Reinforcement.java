import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

public class Reinforcement implements ActionListener{
	MFrame frame;
	MainControll controll;
	public Reinforcement(MainControll controll) throws IOException {
		this.controll=controll;

	}
	/*	Player playerPlaying;
*/ 
	

	public void clicking() throws IOException {
		int phase=0;

/*		frame = new MFrame();
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
*/	}
	public void dosomething(JButton button) {
		button.setEnabled(false);
		 controll.frame.OnlyNeeded(controll.neighbours(0));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton temp=controll.frame.hashButton.get(e.getActionCommand().split("\n")[0]);
		dosomething(temp);
		//controll.frame.hashButton.get(e.getActionCommand().split("\n")[0]).setEnabled(false);
		
	}

}
