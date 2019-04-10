package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.MainController;
import controller.Tournament;

/**
 * This class implements user interface for player strategies
 * 
 * @author pazim
 * @version 1.0
 */
public class SelectPlayerStrategies {

	private JButton ok_button;
	private JFrame frame;
	public static ArrayList<String> strategy_selected = new ArrayList<>();
	ArrayList<JLabel> jlabel1 = new ArrayList<>();
	ArrayList<JComboBox> jcombo_array = new ArrayList<>();

	String[] single = { "Human", "Agressive", "Benevolent", "Random", "Cheater" };
	String[] tournament = { "Agressive", "Benevolent", "Random", "Cheater" };

	/**
	 * Select player strategies
	 */
	public SelectPlayerStrategies() {
		setup();
		GetSelectedValue();
	}

	/**
	 * implements user interface for different player strategies
	 */
	public void setup() {
		frame = new JFrame("Players Strategies");
		frame.setSize(500, SelectNoOfPlayers.NumberOfPlayers * 70 + 100);

		String[] list = null;
		if (GameStartWindow.GameMode == 2)
			list = tournament;
		else
			list = single;

		for (int i = 0, j = 10; i < SelectNoOfPlayers.NumberOfPlayers; i++, j += 50) {
			jlabel1.add(new JLabel("Player" + (i + 1) + ":"));
			jlabel1.get(i).setBounds(10, j, 250, 50);
			jcombo_array.add(new JComboBox(list));
			jcombo_array.get(i).setBounds(100, j + 15, 200, 20);
			frame.add(jcombo_array.get(i));
			frame.add(jlabel1.get(i));
			jcombo_array.get(i).setVisible(true);
			jlabel1.get(i).setVisible(true);
		}
		ok_button = new JButton("OK");
		ok_button.setBounds(290, SelectNoOfPlayers.NumberOfPlayers * 60, 100, 30);
		frame.add(ok_button);
		ok_button.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setVisible(true);
	}

	public void GetSelectedValue() {
		ok_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.print("returns" + NumberOfPlayers);
				for (int i = 0, j = 10; i < SelectNoOfPlayers.NumberOfPlayers; i++, j += 50) {
					strategy_selected.add((String) jcombo_array.get(i).getSelectedItem());// = Integer.parseInt((String)
																							// //
																							// PNumberJCombo.getSelectedItem());
				}
				if (GameStartWindow.GameMode == 2) {
					frame.dispose();
					Tournament temp = new Tournament();
				} else {
					try {
						if (strategy_selected.contains("Human")) {
							frame.dispose();
							MainController controll = new MainController();
							controll.Function();
						} else {
							JOptionPane.showMessageDialog(null,
									"There Should be Atleast One Human Player. \n For all Computer Select Tournament Mode \n Select Atleast One Player");
							strategy_selected.clear();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public static ArrayList<String> getStrategies() {
		return strategy_selected;
	}

}
