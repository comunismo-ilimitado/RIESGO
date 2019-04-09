package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import controller.ReadingFiles;
import model.Country;


/**
 * This class edit the countries user view
 * @author pazim
 * @version 1.0
 *
 */
public class EditCountries {
	private static JFrame MapFrame;
	private JLabel HeaderLabel;
	private JLabel HeaderLabel2;
	private static JButton SelectButton;
	private static JButton SetButton;
	private static JButton NextButton;
	private static JComboBox ContinentsCombo;
	private static String CountrySelected = "";
	private JCheckBox[] check_boxes = new JCheckBox[150];

	static List<String> MapFiles = new ArrayList<String>();
	static List<String> Countries = new ArrayList<String>();
	static List<String> Continents = new ArrayList<String>();
	private JLabel[] countriesinside = new JLabel[150];
	int len = 0;

	/**
	 * constructor
	 */
	public EditCountries() {
		MFrame2 frame2 = new MFrame2();
		String address = "Resources/" + SelectMap.MapSelected + ".map";
		if (ReadingFiles.CountryNameObject != null)
			System.out.println("Initial list of elements: " + ReadingFiles.CountriesNames);
		System.out.println("Initial list of elements: " + ReadingFiles.ContinentNames);
		setUp();
		getSelectedContinent();
	}
	
	/**
	 * set up user view
	 */
	public void setUp() {
		if (ReadingFiles.CountryNameObject != null)
			MapFrame = new JFrame("Edit Map");
		MapFrame.setSize(900, 900);

		HeaderLabel = new JLabel("Countries");
		HeaderLabel.setBounds(50, 50, 150, 50);

		String Maps[] = ReadingFiles.CountriesNames.toArray(new String[0]);
		ContinentsCombo = new JComboBox(Maps);
		ContinentsCombo.setBounds(120, 150, 200, 20);

		SelectButton = new JButton("SELECT");
		SelectButton.setBounds(175, 200, 90, 20);

		SetButton = new JButton("SET");
		SetButton.setBounds(700, 700, 90, 20);

		NextButton = new JButton("NEXT");
		NextButton.setBounds(1200, 800, 90, 20);
		
		for (int i = 0, k = 0, j = 30; i < ReadingFiles.CountriesNames.size(); i++, j = j + 30) {
			check_boxes[i] = new JCheckBox(ReadingFiles.CountriesNames.get(i));
		}

		SetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Country> neighbours= new ArrayList<>();

				for (int i = 0; i < ReadingFiles.CountriesNames.size(); i++) {
					if (check_boxes[i].isSelected()) {
						Country temp = ReadingFiles.CountryNameObject.get(check_boxes[i].getText());
						neighbours.add(temp);

						System.out.println("caught:" + temp.getName());
						check_boxes[i].setSelected(false);
						JOptionPane.showMessageDialog(null, check_boxes[i].getText()+ " added to "+CountrySelected);
					}
				}
				ReadingFiles.CountryNameObject.get(CountrySelected).setNeighbors(neighbours);

			}
		});
		
		NextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MapFrame.dispose();
				SelectNoOfPlayers.assignCountries();
			}
		});

		MapFrame.add(HeaderLabel);
		MapFrame.add(ContinentsCombo);

		MapFrame.add(SelectButton);
		MapFrame.add(SetButton);
		MapFrame.add(NextButton);

		HeaderLabel.setVisible(true);
		ContinentsCombo.setVisible(true);
		NextButton.setVisible(true);
		SelectButton.setVisible(true);
		SetButton.setVisible(true);
		MapFrame.setLayout(null);
		MapFrame.setVisible(true);
		MapFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		MapFrame.setResizable(true);

	}


	public String getSelectedContinent() {

		SelectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for (int m = 0; m < len; m++) {
					countriesinside[m].setVisible(false);
					MapFrame.remove(countriesinside[m]);
				}
				len = 0;
				CountrySelected = (String) ContinentsCombo.getSelectedItem();
				System.out.print(CountrySelected);
				for (int i = 0, k = 0, j = 30; i < ReadingFiles.CountriesNames.size(); i++, j = j + 30) {

					for (model.Country s : ReadingFiles.CountryNameObject.get(CountrySelected).getNeighbors()) {
						if (s.getName().compareTo(ReadingFiles.CountriesNames.get(i)) == 0) {
							countriesinside[k] = new JLabel("yes");

							MapFrame.add(countriesinside[k]);
							countriesinside[k].setBounds(600, j, 150, 50);
							countriesinside[k].setVisible(true);
							len++;
							k++;
						}
					}
					MapFrame.add(check_boxes[i]);
					check_boxes[i].setBounds(400, j, 150, 50);
					check_boxes[i].setVisible(true);
				}
			}
		});
		return CountrySelected;

	}

}
