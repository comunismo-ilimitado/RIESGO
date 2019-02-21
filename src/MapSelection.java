import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

public class MapSelection {

	private JFrame MapFrame;
	private JLabel HeaderLabel;
	private JLabel HeaderLabel2;
	private static JButton SelectButton;
	private static JComboBox MapCombobox;
	private static String MapSelected;

  	static List<String> MapFiles= new ArrayList<String>() ;

 
 public static void main(String args[]) {
	 MapSelection screen1= new MapSelection();
	// Use following method to add Maps to the Displayed List
	 screen1.addMap("World1.txt");
	 screen1.addMap("World2.txt");
	 screen1.addMap("World3.txt");
	 screen1.addMap("World4.txt");
	 screen1.addMap("World5.txt");
	 screen1.setUpScreen();
	 String temp=screen1.getSelectedMap();
	 
 }

 /**
  *Setting up frame components
  */
 public void setUpScreen() {
	 MapFrame = new JFrame("Start-up phase");
	 MapFrame.setSize(500, 500);
	 
	 HeaderLabel = new JLabel("Choose the map");
	 HeaderLabel.setBounds(120,100,150,50);
	 
	 HeaderLabel2 = new JLabel("STEP 1: MAP SELECTION ");
	 HeaderLabel2.setBounds(90,40,150,50);
   	 
   	 String Maps[]=MapFiles.toArray(new String[0]);
   	MapCombobox= new JComboBox(Maps);
   	MapCombobox.setBounds(120,150,200,20);
   	 
   	 SelectButton= new JButton("OK");
   	 SelectButton.setBounds(175, 200, 90, 20);
   	 
   	MapFrame.add(HeaderLabel);
   	MapFrame.add(HeaderLabel2);
   	MapFrame.add(MapCombobox);
   	MapFrame.add(SelectButton);
   	HeaderLabel.setVisible(true);
   	HeaderLabel2.setVisible(true);
   	MapCombobox.setVisible(true);
   	 SelectButton.setVisible(true);
   	 
   	MapFrame.setLayout(null);
   	MapFrame.setVisible(true);
	 
 }
 /**
  * 
  * @param item
  */
 public static void addMap(String item) {
	 MapFiles.add(item);
 }
 /**
  * This method returns the user input i.e. the map selected 
  * This is the action performed for SelectButton
  * @return MapSelected
  */
 public  static String getSelectedMap() {

	 SelectButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			MapSelected= (String) MapCombobox.getSelectedItem();
			System.out.print(MapSelected);
			//invoke next screen here i.e Load Maps
			//LoadSelectedMap.loadMap();
		}
   	 });
	 return MapSelected;
	 
 }
 
}