package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import controller.ReadingFiles;
import model.Country;


/**
 * This class edit the continent user view
 * @author pazim
 * @version 1.0
 *
 */
public class EditContinents {
	private static JFrame MapFrame;
	private JLabel HeaderLabel;
	private JLabel HeaderLabel2;
	private static JButton SelectButton;
	private static JButton SetButton;
	private static JButton NextButton;

	private static JComboBox ContinentsCombo;

	int len = 0;
	private static String ContinentSelected = "";
	private static String CountrySelected = "";
	private JCheckBox[] CheckBoxes = new JCheckBox[150];
	private JLabel[] CountriesCheck = new JLabel[150];

	static List<String> MapFiles = new ArrayList<String>();
	ReadingFiles ReadFile;
	static List<String> Countries = new ArrayList<String>();
	static List<String> Continents = new ArrayList<String>();

	/**
	 * Constructor
	 */
	public EditContinents() {
		MFrame2 frame2 = new MFrame2();
		ReadFile = new ReadingFiles(frame2);
		String address = "Resources/" + SelectMap.MapSelected + ".map";

		if (SelectMapType.MapType == 4)
			address = "Resources/" + SelectMap.MapSelected + ".map";
		else if (SelectMapType.MapType == 5)
			address = "Resources/LoadedMap.map";
		else if (SelectMapType.MapType == 6)
			address = "Resources/UserMap.map";
		try {
			ReadFile.Reads(address,SelectNoOfPlayers.NumberOfPlayers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ReadingFiles.CountryNameObject != null)
			System.out.println("Initial list of elements: " + ReadingFiles.CountriesNames);
		System.out.println("Initial list of elements: " + ReadingFiles.ContinentNames);

		setUp();
		getSelectedContinent();
	}

	/**
	 * Set the Continents in the view
	 */
	public void setUp() {

		if (ReadingFiles.CountryNameObject != null)
			MapFrame = new JFrame("Edit Map");
		MapFrame.setSize(900, 900);

		HeaderLabel = new JLabel("Continents");
		HeaderLabel.setBounds(50, 50, 150, 50);

		HeaderLabel2 = new JLabel("Contains");
		HeaderLabel2.setBounds(600, 20, 150, 50);

		String Maps[] = ReadingFiles.ContinentNames.toArray(new String[0]);
		ContinentsCombo = new JComboBox(Maps);
		ContinentsCombo.setBounds(120, 150, 200, 20);

		for (int i = 0, k = 0, j = 30; i < ReadingFiles.CountriesNames.size(); i++, j = j + 30) {
			CheckBoxes[i] = new JCheckBox(ReadingFiles.CountriesNames.get(i));
		}
		SelectButton = new JButton("SELECT");
		SelectButton.setBounds(175, 200, 90, 20);

		SetButton = new JButton("SET");
		SetButton.setBounds(700, 700, 90, 20);

		NextButton = new JButton("NEXT");
		NextButton.setBounds(1200, 800, 90, 20);

		NextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MapFrame.dispose();
				EditCountries e = new EditCountries();
			}
		});

		SetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Country> countries = new ArrayList<>();
				for (int i = 0; i < ReadingFiles.CountriesNames.size(); i++) {
					if (CheckBoxes[i].isSelected()) {
						countries.add(ReadingFiles.CountryNameObject.get(CheckBoxes[i].getText()));
						System.out.println(
								"caught:" + ReadingFiles.CountryNameObject.get(CheckBoxes[i].getText()).getName());
						ReadingFiles.CountryNameObject.get(CheckBoxes[i].getText())
								.setContinent(ReadingFiles.ContinentNameObject.get(ContinentSelected));
						System.out.println("cont:"
								+ ReadingFiles.CountryNameObject.get(CheckBoxes[i].getText()).getContinent().getName());
						CheckBoxes[i].setSelected(false);
						JOptionPane.showMessageDialog(null, CheckBoxes[i].getText() + " added to " + ContinentSelected);
					}
				}
				ReadingFiles.ContinentNameObject.get(ContinentSelected).setCountries(countries);
			}
		});

		MapFrame.add(HeaderLabel);
		MapFrame.add(HeaderLabel2);
		MapFrame.add(ContinentsCombo);

		MapFrame.add(SelectButton);
		MapFrame.add(SetButton);

		MapFrame.add(NextButton);
		HeaderLabel.setVisible(true);
		HeaderLabel2.setVisible(true);
		ContinentsCombo.setVisible(true);
		SetButton.setVisible(true);

		SelectButton.setVisible(true);
		NextButton.setVisible(true);
		MapFrame.setLayout(null);
		MapFrame.setVisible(true);
		MapFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		MapFrame.setResizable(true);
		MapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Gets selected continents
	 * 
	 * @return ContinentSelected: String of continent selected
	 */
	public String getSelectedContinent() {

		SelectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int m = 0; m < len; m++) {
					CountriesCheck[m].setVisible(false);
					MapFrame.remove(CountriesCheck[m]);
				}
				len = 0;
				// TODO Auto-generated method stub
				ContinentSelected = (String) ContinentsCombo.getSelectedItem();
				System.out.print(ContinentSelected);
				for (int i = 0, k = 0, j = 30; i < ReadingFiles.CountriesNames.size(); i++, j = j + 30) {
					for (model.Country s : ReadingFiles.ContinentNameObject.get(ContinentSelected).getCountries()) {
						if (s.getName().compareTo(ReadingFiles.CountriesNames.get(i)) == 0) {
							CountriesCheck[k] = new JLabel("yes");

							MapFrame.add(CountriesCheck[k]);
							CountriesCheck[k].setBounds(600, j, 150, 50);
							CountriesCheck[k].setVisible(true);
							len++;
							k++;
						}
					}
					MapFrame.add(CheckBoxes[i]);
					CheckBoxes[i].setBounds(400, j, 150, 50);
					CheckBoxes[i].setVisible(true);
				}
			}

		});

		return ContinentSelected;
	}

}
