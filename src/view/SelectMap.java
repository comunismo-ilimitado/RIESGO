package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.*;



/**
 * This class implements Select map
 * @author pazim
 *
 */
public class SelectMap {

	private static JFrame frame;
	private JLabel HeaderLabel;
	private JLabel HeaderLabel2;
	private static JButton ok_button;
	private static JComboBox MapCombobox;
	private static JButton EditButton;
	private ArrayList<JCheckBox> check_boxes= new ArrayList<>();

	public static String MapSelected = "";
	public static List<String> TourMapList = new ArrayList<String>();
	public static int NoOfGames=0;
	public static int NoOfTurns=0;
	private JComboBox no_of_games_combo_box;
	private JTextField D_field;
	private JLabel no_of_games_label;
	private JLabel D_label;

	static List<String> JComboMaps = new ArrayList<String>();

	/**
	 * Setting up frame components
	 */
	public SelectMap() {
		
		if(GameStartWindow.GameMode==2)
			tourframe();
	else 
		singlemodeframe();
	}
	public void tourframe() {
		
		setUpTourScreen();
		
		
	}
	public void setUpTourScreen() {
		ArrayList<String> maps= new ArrayList<>();
		maps.addAll(Arrays.asList("Montreal","India","World","Europe","Asia"));//add more maps
		
		frame = new JFrame("S");
		frame.setSize(700, maps.size()*90);

		HeaderLabel = new JLabel("Choose Maps");
		HeaderLabel.setBounds(20, 20, 150, 50);
		

		
		for (int i = 0, j = 50; i < maps.size(); i++, j += 50) {
			check_boxes.add(new JCheckBox(maps.get(i)));
			check_boxes.get(i).setBounds(100, j + 15, 100, 20);
			frame.add(check_boxes.get(i));
			check_boxes.get(i).setVisible(true);
		}
		
		String[] temp= {"1","2","3","4","5"};
		no_of_games_combo_box = new JComboBox(temp);
		no_of_games_combo_box.setBounds(320, 60, 50, 30);
		
		no_of_games_label = new JLabel("No of games");
		no_of_games_label.setBounds(320, 30, 90, 20);
		
		D_field = new JTextField("10");
		D_field.setBounds(400, 60, 60, 30);
		
		D_label = new JLabel("No of Turns (10-50)");
		D_label.setBounds(400, 30, 90, 20);
		
		
		ok_button = new JButton("OK"); 
		ok_button.setBounds(175, maps.size()*60, 90, 20);
		
		ok_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < maps.size(); i++) {
					if (check_boxes.get(i).isSelected()) {
						TourMapList.add(check_boxes.get(i).getText());
						System.out.println("caught:" + check_boxes.get(i).getText());
					}
				}
				NoOfGames=Integer.parseInt((String) no_of_games_combo_box.getSelectedItem());
				NoOfTurns=Integer.parseInt(D_field.getText());
				frame.dispose();
				SelectNoOfPlayers.assignCountries();
			}
		});

		frame.add(HeaderLabel);
		frame.add(ok_button);
		frame.add(D_label);
		frame.add(D_field);
		frame.add(no_of_games_label);
		frame.add(no_of_games_combo_box);
		HeaderLabel.setVisible(true);
		ok_button.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public void singlemodeframe() {
		
		addMap("Montreal");
		addMap("India");
		addMap("World");
		addMap("Europe");
		addMap("Asia");
		setUpSingleScreen();
		getSingleModeSelectedMap();
	}

	public void setUpSingleScreen() {
		
		frame = new JFrame("S");
		frame.setSize(500, 500);

		HeaderLabel = new JLabel("Choose the map");
		HeaderLabel.setBounds(120, 100, 150, 50);

		HeaderLabel2 = new JLabel("STEP 1: MAP SELECTION ");
		HeaderLabel2.setBounds(90, 40, 150, 50);

		String Maps[] = JComboMaps.toArray(new String[0]);
		MapCombobox = new JComboBox(Maps);
		MapCombobox.setBounds(120, 150, 200, 20);

		ok_button = new JButton("OK"); 
		ok_button.setBounds(175, 200, 90, 20);

		EditButton = new JButton("EDIT");
		EditButton.setBounds(275, 200, 90, 20);
		
		EditButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				MapSelected = (String) MapCombobox.getSelectedItem();
				SelectMapType.MapType=4;
				EditContinents obj= new EditContinents();
			}
		});

		frame.add(HeaderLabel);
		frame.add(HeaderLabel2);
		frame.add(MapCombobox);
		frame.add(ok_button);
		frame.add(EditButton);
		HeaderLabel.setVisible(true);
		HeaderLabel2.setVisible(true);
		MapCombobox.setVisible(true);
		ok_button.setVisible(true);
		EditButton.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setVisible(true);

	}

	/**
	 * 
	 * @param item
	 */
	public static void addMap(String item) {
		JComboMaps.add(item);
	}

	/**
	 * This method returns the user input i.e. the map selected This is the action
	 * performed for SelectButton
	 * 
	 * @return MapSelected
	 */
	public static String getSingleModeSelectedMap() {

		ok_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MapSelected = (String) MapCombobox.getSelectedItem();
			//	System.out.print(MapSelected);
				// invoke next screen here i.e Load Maps
				frame.dispose();
				SelectNoOfPlayers.assignCountries();
				// jumps to new Window
			}
		});
		return MapSelected;
	}


}
