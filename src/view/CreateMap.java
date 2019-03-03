package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import controller.SaveCreatedMap;
import model.Continent;
import model.Country;

public class CreateMap {

	private JPanel AssignCoumtriesPanel, AssignNeighboursPanel;
	private JFrame AssignCountriesFrame, AssignNeighboursFrame, CreateMapFrame2, GetNumberFrame;
	private JLabel NoOfCountriesLabel, NoOfContinentsLabel, ContinentContolValueLabel;
	private JTextField NoOfCountriesTextField, NoOfContinentsTextField, ControlValueTextField;
	private JButton GenerateMapButton, AddContinentNameButton;
	private JButton AddToCountryButton, AddToContinentButton, NextButton, DoneButton;
	private JComboBox ContinentsCombo, CountriesCombo;
	public static int NoOfCountries, NoOfContinents;
	private List<Continent> Continents = new ArrayList<Continent>();
	private JCheckBox[] checkboxes = new JCheckBox[150];
	private JCheckBox[] checkboxes1 = new JCheckBox[150];
	DefaultListModel<String> CountriestoContinentList;
	public static List<Continent> ContinentsObjectList = new ArrayList<>();
	public static List<Country> CountriesObjectList = new ArrayList<>();

	private static int index1 = 0;

	public CreateMap() {
		GetNumbers();
		StartFrame();

	}

	public void GetNumbers() {
		GetNumberFrame = new JFrame("Create Map");
		GetNumberFrame.setSize(500, 500);
		NoOfCountriesLabel = new JLabel("No. of Countries");
		NoOfCountriesLabel.setBounds(200, 27, 150, 50);
		NoOfContinentsLabel = new JLabel("No of Continents");
		NoOfContinentsLabel.setBounds(10, 27, 150, 50);
		NoOfCountriesTextField = new JTextField("3");
		NoOfCountriesTextField.setBounds(300, 43, 50, 20);
		NoOfContinentsTextField = new JTextField("2");
		NoOfContinentsTextField.setBounds(110, 43, 50, 20);
		GenerateMapButton = new JButton("Generate Map");
		GenerateMapButton.setBounds(200, 90, 150, 30);
		GetNumberFrame.add(NoOfContinentsLabel);
		GetNumberFrame.add(NoOfCountriesLabel);
		GetNumberFrame.add(NoOfCountriesTextField);
		GetNumberFrame.add(NoOfContinentsTextField);
		GetNumberFrame.add(GenerateMapButton);
		GetNumberFrame.setLayout(null);
		GetNumberFrame.setVisible(true);
	}

	public void AssignCountriesFrame() {
		AssignCountriesFrame = new JFrame("Assign Countries To Continents");
		AssignCoumtriesPanel = new JPanel();
		AssignCoumtriesPanel.setBounds(440, 80, 200, 200);
		AssignCoumtriesPanel.setBackground(Color.gray);
		AssignCoumtriesPanel.setSize(100, 800);
		AssignCountriesFrame.setSize(1000, 1000);

		ContinentContolValueLabel = new JLabel("Continent Control Value");
		ContinentContolValueLabel.setBounds(10, 200, 150, 50);
		AddToContinentButton = new JButton("Add Countries");
		AddToContinentButton.setBounds(575, 500, 150, 30);

		NextButton = new JButton("Next");
		NextButton.setBounds(700, 700, 150, 30);
		ContinentsCombo = new JComboBox();// insetr string
		ContinentsCombo.setBounds(10, 150, 200, 20);
		ControlValueTextField = new JTextField("0");
		ControlValueTextField.setBounds(10, 250, 50, 20);
		CountriestoContinentList = new DefaultListModel<>();

		AssignCountriesFrame.add(AssignCoumtriesPanel);
		AssignCountriesFrame.add(NextButton);
		AssignCountriesFrame.add(ContinentContolValueLabel);
		AssignCountriesFrame.add(ControlValueTextField);
		AssignCountriesFrame.add(AddToContinentButton);
		AssignCountriesFrame.add(ContinentsCombo);

		AssignCountriesFrame.setLayout(null);
		AssignCountriesFrame.setVisible(true);
	}

	public void AssignNeighboursFrame() {

		AssignNeighboursFrame = new JFrame("Add Neighbours");
		AssignNeighboursPanel = new JPanel();
		AssignNeighboursPanel.setBounds(440, 80, 200, 200);
		AssignNeighboursPanel.setBackground(Color.gray);
		AssignNeighboursPanel.setSize(100, 800);
		AssignNeighboursFrame.setSize(1000, 1000);

		AddToCountryButton = new JButton("Add Countries");
		AddToCountryButton.setBounds(575, 300, 150, 30);
		DoneButton = new JButton("done");
		DoneButton.setBounds(700, 700, 150, 30);

		CountriesCombo = new JComboBox();// insetr string
		CountriesCombo.setBounds(10, 150, 200, 20);

		AssignNeighboursFrame.add(AssignNeighboursPanel);
		AssignNeighboursFrame.add(DoneButton);
		AssignNeighboursFrame.add(AddToCountryButton);
		AssignNeighboursFrame.add(CountriesCombo);

		AssignNeighboursFrame.setLayout(null);
		AssignNeighboursFrame.setVisible(true);
	}

	public void StartFrame() {

		GenerateMapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				System.out.print("countries%%%%:" + NoOfCountries);
				NoOfCountries = Integer.parseInt(NoOfCountriesTextField.getText());
				NoOfContinents = Integer.parseInt(NoOfContinentsTextField.getText());
				if (NoOfCountries <= 2 || NoOfContinents <= 1) {
					JOptionPane.showMessageDialog(null, "Values should be greater than 2");
				} else if (NoOfCountries < NoOfContinents) {
					JOptionPane.showMessageDialog(null, "NO of Countries shuld be greater than continents");
				} else {
					GetNumberFrame.dispose();
					AssignCountriesFrame();
					for (int i = 1; i <= NoOfContinents; i++) {
						ContinentsCombo.addItem("Continent" + i);
					}
					for (int i = 0, j = 150; i < NoOfCountries; i++, j = j + 30) {
						checkboxes[i] = new JCheckBox("Country" + (i + 1));
						AssignCoumtriesPanel.add(checkboxes[i]);
						checkboxes[i].setBounds(300, j, 150, 50);
						checkboxes[i].setVisible(true);
					}
					ActionAddContinent();
					ActionNextButtonFrame();
				}

			}
		});

	}

	public void ActionAddContinent() {
		AddToContinentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Country> TempCountriesObject = new ArrayList<Country>();

				for (int i = 0; i < NoOfCountries; i++) {
					if (checkboxes[i].isSelected()) {
						Country temp = new Country(checkboxes[i].getText());
						TempCountriesObject.add(temp);
						// AssignCoumtriesPanel.remove(checkboxes[i]);
						checkboxes[i].setVisible(false);
						AssignCoumtriesPanel.updateUI();
						System.out.print(checkboxes[i].isSelected() + "yesss checkbox name:" + checkboxes[i].getText());
						checkboxes[i].setSelected(false);
					}

				}
				System.out.println("selected");
				Continent tempc = new Continent(Integer.parseInt(ControlValueTextField.getText()),
						(String) ContinentsCombo.getSelectedItem());
				tempc.setCountries(TempCountriesObject);
				if (tempc.getName() != null) {
					ContinentsObjectList.add(tempc);
				}
				ContinentsCombo.removeItem((String) ContinentsCombo.getSelectedItem());
				System.out.print("index:" + (String) ContinentsCombo.getSelectedItem());

			}
		});
	}

	public void ActionNextButtonFrame() {
		NextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int check = 0;
				for (int i = 0; i < NoOfCountries; i++) {
					if (checkboxes[i].isVisible() == true) {
						check = 1;
					}
				}

				if (ContinentsCombo.getSelectedItem() != null) {
					JOptionPane.showMessageDialog(null, "Assign all continents");
				} else if (check == 1) {
					JOptionPane.showMessageDialog(null, "Assign all Countries");
					AssignCountriesFrame.dispose();
					GetNumbers();
					StartFrame();
				} else {
					AssignCountriesFrame.dispose();
					AssignNeighboursFrame();

					for (int i = 1; i <= NoOfCountries; i++) {
						CountriesCombo.addItem("Country" + i);
					}
					for (int i = 0, j = 150; i < NoOfCountries; i++, j = j + 30) {
						checkboxes1[i] = new JCheckBox("Country" + (i + 1));
						AssignNeighboursPanel.add(checkboxes1[i]);
						checkboxes1[i].setBounds(300, j, 150, 50);
						checkboxes1[i].setVisible(true);
					}
					ActionAssignNeighbours();
				}
			}
		});

	}

	public void ActionAssignNeighbours() {
		AddToCountryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Country> TempCountriesObject2 = new ArrayList<Country>();

				for (int i = 0; i < NoOfCountries; i++) {
					if (checkboxes1[i].isSelected()) {
						Country temp = new Country(checkboxes1[i].getText());
						TempCountriesObject2.add(temp);
						System.out.print(checkboxes1[i].isSelected() + "yesss");
						checkboxes1[i].setSelected(false);
					}
					System.out.println("selected");
				}
				System.out.print("index1:" + (String) CountriesCombo.getSelectedItem());
				// String index1=(String)CountriesCombo.getSelectedItem()

				Country tempct = new Country((String) CountriesCombo.getSelectedItem());
				tempct.setNeighbors(TempCountriesObject2);
				CountriesObjectList.add(tempct);
				CountriesCombo.removeItem((String) CountriesCombo.getSelectedItem());
				if ((String) CountriesCombo.getSelectedItem() == null) {
					// AssignNeighboursPanel.removeAll();

					for (int j = 0; j < NoOfCountries; j++) {
						checkboxes1[j].setVisible(false);
						AssignNeighboursPanel.updateUI();
					}
				}
			}
		});
		DoneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (CountriesCombo.getSelectedItem() != null) {
					JOptionPane.showMessageDialog(null, "Assign neighbours to all Countries");
				} else {
					JOptionPane.showMessageDialog(null, "Map successfully Created");
					Disp();
					SaveCreatedMap SaveMapObejct = new SaveCreatedMap();
				}
			}
		});

	}

	// Just for checking.. remove later
	public void Disp() {
		List<Country> Neighbours = new ArrayList<Country>();
		System.out.print("end");
		for (Country in : CountriesObjectList) {
			Neighbours = in.getNeighbors();
			System.out.println("\nCountry name: " + in.getName() + "\nNeighbours:\n");
			for (Country it : Neighbours) {
				System.out.println(it.getName());
			}

		}
		List<Country> CountriesinContinent = new ArrayList<Country>();
		for (Continent in : ContinentsObjectList) {
			CountriesinContinent = in.getCountries();
			System.out.println(
					"\nContinent name: " + in.getName() + "\nControl Value:" + in.getControlValue() + "\nCountries:\n");
			int i = 1;
			for (Country cc : CountriesObjectList) {
				cc.setCountryId(i);
				i++;
			}
			i = 1;
			for (Continent cc : ContinentsObjectList) {
				cc.setContinentId(i);
				i++;
			}

			for (Country it : CountriesinContinent) {
				System.out.println(it.getName());
			}

		}
		for (Continent tt : ContinentsObjectList) {
			for (Country cc : tt.getCountries()) {
				for (Country ss : CountriesObjectList) {
					if (ss.getName().compareTo(cc.getName()) == 0) {
						cc.setNeighbors(ss.getNeighbors());
						ss.setContinent(tt);
					}
				}
			}
		}
		for (Continent tt : ContinentsObjectList) {
			System.out.println("Continent:" + tt.getName());
			for (Country cc : tt.getCountries()) {
				System.out.println("Country:" + cc.getName());
				for (Country ss : cc.getNeighbors()) {
					System.out.println("Neighbour:" + ss.getName());
				}
			}
		}

	}
}
