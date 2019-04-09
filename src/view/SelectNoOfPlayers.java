package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.MainController;


/**
 * this class select number of player for the game
 * @author pazim
 * @version 1.0
 * 
 *
 */
public class SelectNoOfPlayers {

	private static JComboBox PNumberJCombo;
	private static JButton OKButton;
	private static JFrame Frame1;
	public static int NumberOfPlayers = 1;

	/**
	 * returns the number of player chooses as integer
	 * 
	 * @return int
	 */
	public static int GetSelectedValue() {
		OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				NumberOfPlayers = Integer.parseInt((String) PNumberJCombo.getSelectedItem());
				// System.out.print("returns" + NumberOfPlayers);
				Frame1.dispose();

				SelectPlayerStrategies p= new  SelectPlayerStrategies();

			}

		});
		return NumberOfPlayers;
	}

	/**
	 * This method is used to select the number of players in a game for user
	 * interface
	 */
	public static void assignCountries() {
		System.out.println(SelectMap.NoOfGames+","+SelectMap.NoOfTurns);
		Frame1 = new JFrame("Map");
		Frame1.setSize(500, 500);

		JLabel Label1 = new JLabel("STEP 2:    Select number of Players ");
		Label1.setBounds(90, 100, 250, 50);
		;

		OKButton = new JButton("OK");
		OKButton.setBounds(290, 190, 100, 30);

		String[] select = { "2", "3", "4", "5", "6" };
		PNumberJCombo = new JComboBox(select);
		PNumberJCombo.setBounds(120, 150, 200, 20);
		Frame1.add(Label1);
		Frame1.add(OKButton);
		Frame1.add(PNumberJCombo);
		OKButton.setVisible(true);
		PNumberJCombo.setVisible(true);
		Label1.setVisible(true);
		Frame1.setLocationRelativeTo(null);
		Frame1.setLayout(null);
		Frame1.setVisible(true);
		NumberOfPlayers = GetSelectedValue();
	}

}
