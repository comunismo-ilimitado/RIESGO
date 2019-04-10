package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import controller.MyActionListner;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import model.CardTypes;
import model.Country;

/**
 * This class represents user interface for the game play
 * 
 * @author pazim
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class MFrame extends JFrame implements Observer {
	private JButton[] button;
	public JTextArea area;
	public JButton buttonCard1, buttonCard2, buttonCard3, buttonCard4;
	JPanel jPanel2, jPanel6, jPanel7;
	public JLabel jLabeCardl, jLabelCard2, jLabelCard3;
	public JButton nextAction;
	String Phases[] = { "Finish Reinforcement", "Finish Attack", "Finish Fortification" };
	HashMap<String, JButton> hashButton;
	JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel16, jLabel26, jLabel36, jLabel46, jLabel56,
			jLabel66, jLabel17, jLabel27, jLabel37, jLabel47, jLabel57, jLabel67;
	ArrayList<JLabel> jLabels, jLabels2;
	MyActionListner myActionListner;
	public int playerTurn;
	public int noArmiesLeft;
	public String BBB;
	public String AAA;
	public String CCC;
	public String MapImage = "noimage.bmp";

	public MFrame(MyActionListner myActionListner, String MapImage) {

		super("PAzim");
		this.myActionListner = myActionListner;
		this.MapImage = MapImage;
	}

	/**
	 * This method displays the user interface
	 * 
	 * @throws Exception
	 */
	public void fun() throws Exception {
		hashButton = new HashMap<>();
		FlowLayout flowLayout = new FlowLayout();
		BorderLayout borderLayout = new BorderLayout();
		// main pannel

		JPanel mainPanel = new JPanel(new GridLayout(1, 2));

		JPanel jPanel = new JPanel(new GridLayout(2, 1));
		// jPanel.setSize(new Dimension(500, 1000));
		// jPanel.setBackground(Color.red);
		// image pannel
		BufferedImage image;
		try {
			image = ImageIO.read(new File("Resources/" + MapImage));
		} catch (Exception e) {
			image = ImageIO.read(new File("Resources/noimage.bmp"));

		}
		JLabel label = new JLabel(new ImageIcon(image));

		JScrollPane scroller = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		jPanel.add(scroller);
		area = new JTextArea(100, 100);

		
		JScrollPane scroller2 = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		jPanel.add(scroller2);

		// startup view
		JPanel jPanel3 = new JPanel(new GridLayout(0, 1));

		jPanel.add(jPanel3);

		// upper text
		JPanel jPanel4 = new JPanel(new GridLayout(0, 1));
		jPanel4.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		// jPanel4.setBackground(Color.RED);
		jLabel1 = new JLabel("");
		jLabel2 = new JLabel("");
		jLabel3 = new JLabel("");
		jLabel4 = new JLabel("");
		jLabel5 = new JLabel("");
		jLabel6 = new JLabel("");
		jPanel4.add(jLabel1);
		jPanel4.add(jLabel6);
		jPanel4.add(jLabel2);
		jPanel4.add(jLabel3);
		jPanel4.add(jLabel4);
		jPanel4.add(jLabel5);

		jPanel3.add(jPanel4);

		// lower text
		JPanel jPanel5 = new JPanel(new GridLayout(2, 4));
		nextAction = new JButton("Finish Reinforcement");
		nextAction.addActionListener(myActionListner);
		buttonCard1 = new JButton("Infantry");
		buttonCard2 = new JButton("Cavalry");
		buttonCard3 = new JButton("Artillery");
		buttonCard4 = new JButton("Exchange Cards");
		jLabeCardl = new JLabel("");
		jLabelCard2 = new JLabel("");
		jLabelCard3 = new JLabel("");
		buttonCard1.addActionListener(myActionListner);
		buttonCard2.addActionListener(myActionListner);
		buttonCard3.addActionListener(myActionListner);
		buttonCard4.addActionListener(myActionListner);

		jPanel5.add(nextAction);
		jPanel5.add(jLabeCardl);
		jPanel5.add(jLabelCard2);
		jPanel5.add(jLabelCard3);
		jPanel5.add(buttonCard1);
		jPanel5.add(buttonCard2);
		jPanel5.add(buttonCard3);
		jPanel5.add(buttonCard4);

		// jPanel5.setBackground(Color.GREEN);

		jPanel3.add(jPanel5);
		jPanel6 = new JPanel(new GridLayout(0, 1));
		jPanel6.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		jPanel6.add(new JLabel("Percentage Of Countries Occupied By Player"));
		// jPanel6.setBackground(Color.YELLOW);
		jLabel16 = new JLabel("Player 1:- " + playerTurn);
		jLabel26 = new JLabel("Player 2:- " + noArmiesLeft);
		jLabel36 = new JLabel("Player 3 :- " + CCC);
		jLabel46 = new JLabel("Player 4:- " + AAA);
		jLabel56 = new JLabel("Player 5:- " + BBB);
		jLabel66 = new JLabel("Player 6:- " + BBB);
		jLabels = new ArrayList<>();
		jLabels.add(jLabel16);
		jLabels.add(jLabel26);
		jLabels.add(jLabel36);
		jLabels.add(jLabel46);
		jLabels.add(jLabel56);
		jLabels.add(jLabel66);

		jPanel3.add(jPanel6);

		jPanel7 = new JPanel(new GridLayout(0, 1));

		jPanel7.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		jPanel7.setToolTipText("CONTINENTS");
		jPanel7.add(new JLabel("Continent Occupied By Player"));
		jLabel17 = new JLabel("Player 1:- " + playerTurn);
		jLabel27 = new JLabel("Player 2:- " + noArmiesLeft);
		jLabel37 = new JLabel("Player 3 :- " + CCC);
		jLabel47 = new JLabel("Player 4:- " + AAA);
		jLabel57 = new JLabel("Player 5:- " + BBB);
		jLabel67 = new JLabel("Player 6:- " + BBB);
		jLabels2 = new ArrayList<>();
		jLabels2.add(jLabel17);
		jLabels2.add(jLabel27);
		jLabels2.add(jLabel37);
		jLabels2.add(jLabel47);
		jLabels2.add(jLabel57);
		jLabels2.add(jLabel67);

		jPanel3.add(jPanel7);

		jPanel2 = new JPanel(new GridLayout(0, 5));
		// jPanel2.setBackground(Color.BLACK);
		jPanel2.setSize(new Dimension(500, 1000));

		add(mainPanel);
		mainPanel.add(jPanel);
		mainPanel.add(jPanel2);

		setTitle("PAZIMs Risk Game");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Ask for confirmation before terminating the program.
				int option = JOptionPane.showConfirmDialog(null,
						"Do You Want to Save the Game. \n NOTE:- Saving the game will overwrite your previously saved games",
						"Close Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.NO_OPTION) {
					System.exit(0);
				} else if (option == JOptionPane.YES_OPTION) {
					Exit_Option();

				}

			}
		});
		setVisible(true);

	}

	/**
	 * This method update the view
	 */

	public void cards() {

		buttonCard1.setText("Infantry " + myActionListner.getCardsType1());
		buttonCard2.setText("Artillery " + myActionListner.getCardsType2());
		buttonCard3.setText("Cavalry " + myActionListner.getCardsType3());
	}

	/**
	 * This method update the view
	 */

	public void NotifyAll() {
		jLabel1.setText("Turn For Player :-" + (myActionListner.currentPlayer + 1));
		jLabel2.setText("Armies Left:- " + noArmiesLeft);
		jLabel3.setText("Total Armies :- " + myActionListner.getArmiesPerPlayer());
		jLabel4.setText("DICE 1:- " + AAA);
		jLabel5.setText("DICE 2:- " + BBB);
		jLabel6.setText("Current Phase :-" + nextAction.getText().split(" ")[1] + " Phase");
	}

	/**
	 * This method update the view
	 */

	public void SetDominationView(int NoOfPlayers) {
		for (int i = 0; i < NoOfPlayers; i++) {
			jPanel6.add(jLabels.get(i));
			jPanel7.add(jLabels2.get(i));
		}
	}

	/**
	 * This method update the view
	 */

	public void UpdateGameDominationViewPercentage(ArrayList<Float> percent) {
		for (int i = 0; i < percent.size(); i++) {
			jLabels.get(i).setText("Player " + (i + 1) + " :- " + percent.get(i));
		}
	}

	public void UpdateGameDominationViewContinentOccupied(ArrayList<String> occupies) {
		for (int i = 0; i < occupies.size(); i++) {
			jLabels2.get(i).setText("Player " + (i + 1) + " :- " + occupies.get(i));
		}
	}

	/**
	 * This method assigns button to each country in UI
	 * 
	 * @param countryObjects
	 * @throws IOException
	 */

	public void SetButtons(HashMap<String, Country> countryObjects) throws IOException {
		List<String> count = new ArrayList<>(countryObjects.keySet());
		button = new JButton[count.size()];
		Random random = new Random();
		for (int i = 0; i < count.size(); i++) {
			button[i] = new JButton(count.get(i) + " | " + countryObjects.get(count.get(i)).getNoOfArmies() + " | "
					+ countryObjects.get(count.get(i)).getContinent().getName());
			hashButton.put((String) count.get(i), button[i]);
			jPanel2.add(button[i]);
			button[i].addActionListener(myActionListner);
		}

	}

	/**
	 * This method give color to the countries
	 * 
	 * @param countryObjects
	 */
	public void SetColorToAll(HashMap<String, Country> countryObjects) {
		List<String> count = new ArrayList<>(countryObjects.keySet());
		for (int i = 0; i < count.size(); i++) {
			hashButton.get(count.get(i)).setBackground(countryObjects.get(count.get(i)).getOwner().getPlayerColor());
		}
	}

	/**
	 * This method runs after every button click
	 * 
	 * @param countryObjects
	 * @throws IOException
	 */
	public void Refresh(HashMap<String, Country> countryObjects) throws IOException {
		List<String> count = new ArrayList<>(countryObjects.keySet());
		for (int i = 0; i < count.size(); i++) {
			button[i].setText(count.get(i) + " | " + countryObjects.get(count.get(i)).getNoOfArmies() + " | "
					+ countryObjects.get(count.get(i)).getContinent().getName());
			button[i].setName(count.get(i));

		}
	}

	public void RepaintAndRevalidate() {
		revalidate();
	}

	public void error(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public void ActivateAll() {
		for (int i = 0; i < button.length; i++) {
			button[i].setEnabled(true);
		}
	}

	public String popupText(int a) {
		return JOptionPane.showInputDialog("Enter Armies You wana Move Between, Maximum :-" + a);
	}

	public String popupTextNew(String a) {
		String ans = JOptionPane.showInputDialog(a);
		return ans;
	}

	public boolean Allout() {
		int n = JOptionPane.showConfirmDialog(null, "Press Yes For 'All Out' and no for 'Single Attack'",
				"An Inane Question", JOptionPane.YES_NO_OPTION);
		if (n == 0)
			return true;
		else
			return false;
	}

	public void OnlyNeeded(List<Country> arrayList) {
		List<String> temp = new ArrayList<>(hashButton.keySet());
		for (int i = 0; i < arrayList.size(); i++) {
			temp.remove(arrayList.get(i).getName());
		}
		for (int i = 0; i < temp.size(); i++) {
			JButton tempb = hashButton.get(temp.get(i));
			tempb.setEnabled(false);

		}

	}

	public void Exit_Option() {
		myActionListner.SaveGameOnExit();

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stu
		ArrayList<Float> percent = ((MyActionListner) arg0).CountriesPercentage();
		ArrayList<String> listinh = ((MyActionListner) arg0).ContinentsOccupied();
		UpdateGameDominationViewPercentage(percent);
		UpdateGameDominationViewContinentOccupied(listinh);
		cards();
		NotifyAll();

	}

}
