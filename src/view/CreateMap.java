import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CreateMap {

	private JFrame CreateMapFrame;
	private JLabel NoOfCountriesLabel, NoOfContinentsLabel, ContinentContolValueLabel;
	private JTextField NoOfCountriesTextField, NoOfContinentsTextField, ControlValueTextField;
	private JButton GenerateMapButton, BrowseMapButton, UpdateControlValueButton;
	private JButton AddToContinentButton, AddToCountryButton, RemoveFromContinentButton, RemoveFromCountryButton;
	private JComboBox ContinentsCombo, CountriesCombo;
	private int NoOfCountries, NoOfContinents;

	public CreateMap() {
		this.setUpScreen();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateMap CreatedMap = new CreateMap();

	}

	public void setUpScreen() {
		CreateMapFrame = new JFrame("Create Map");
		CreateMapFrame.setSize(900, 900);
		// JLabels
		NoOfCountriesLabel = new JLabel("No. of Countries");
		NoOfCountriesLabel.setBounds(200, 10, 150, 50);
		NoOfContinentsLabel = new JLabel("No of Continents");
		NoOfContinentsLabel.setBounds(10, 10, 150, 50);

		ContinentContolValueLabel = new JLabel("Continent Control Value");
		ContinentContolValueLabel.setBounds(10, 200, 150, 50);

		// JButtons
		GenerateMapButton = new JButton("Generate Map");
		GenerateMapButton.setBounds(400, 27, 150, 30);
		BrowseMapButton = new JButton("Browse");
		BrowseMapButton.setBounds(175, 200, 90, 20);
		AddToContinentButton = new JButton("Add");
		AddToContinentButton.setBounds(175, 200, 90, 20);
		AddToCountryButton = new JButton("Add");
		AddToCountryButton.setBounds(175, 200, 90, 20);
		RemoveFromContinentButton = new JButton("Remove");
		RemoveFromContinentButton.setBounds(175, 200, 90, 20);
		RemoveFromCountryButton = new JButton("Remove");
		RemoveFromCountryButton.setBounds(175, 200, 90, 20);
		UpdateControlValueButton = new JButton("Update");
		UpdateControlValueButton.setBounds(80, 250, 90, 20);

		// JComboBpxes
		ContinentsCombo = new JComboBox();// insetr string
		ContinentsCombo.setBounds(120, 150, 200, 20);
		CountriesCombo = new JComboBox();
		CountriesCombo.setBounds(120, 150, 200, 20);

		// JTextFields
		NoOfCountriesTextField = new JTextField();
		NoOfCountriesTextField.setBounds(300, 27, 50, 20);
		NoOfContinentsTextField = new JTextField();
		NoOfContinentsTextField.setBounds(110, 27, 50, 20);
		ControlValueTextField = new JTextField();
		ControlValueTextField.setBounds(10, 250, 50, 20);

		GenerateMapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				List<>Continent
				int NoOfCountries = Integer.parseInt(NoOfCountriesTextField.getText());
				int NoOfContinents = Integer.parseInt(NoOfContinentsTextField.getText());
				for(int i=0;i<NoOfCountries;i++) {
					
				}

				System.out.print("no od countries:" + NoOfCountries);
			}
		});

		// Add components to frame
		CreateMapFrame.add(NoOfCountriesLabel);
		CreateMapFrame.add(NoOfContinentsLabel);

		CreateMapFrame.add(ContinentContolValueLabel);

		CreateMapFrame.add(GenerateMapButton);
		CreateMapFrame.add(BrowseMapButton);
		CreateMapFrame.add(AddToContinentButton);
		CreateMapFrame.add(AddToCountryButton);
		CreateMapFrame.add(RemoveFromContinentButton);
		CreateMapFrame.add(RemoveFromCountryButton);
		CreateMapFrame.add(UpdateControlValueButton);

		CreateMapFrame.add(ContinentsCombo);
		CreateMapFrame.add(CountriesCombo);

		CreateMapFrame.add(NoOfCountriesTextField);
		CreateMapFrame.add(NoOfContinentsTextField);
		CreateMapFrame.add(ControlValueTextField);

		CreateMapFrame.setLayout(null);
		CreateMapFrame.setVisible(true);

	}

}
