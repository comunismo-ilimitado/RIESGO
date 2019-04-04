package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/**
*This class loads the selected map.
*@author Greeshma
*@version 1.0
*/

public class LoadSelectedMap {

	private static JFrame Frame1;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        LoadSelectedMap temp= new LoadSelectedMap();
        temp.loadMap();
	}
	public static void loadMap() {
	
		JFrame Frame1= new JFrame("Map");
		Frame1.setSize(500, 500);
		
		JLabel Label1= new JLabel("MAP LOADED");
		Label1.setBounds(90,40,150,50);
		
		/**
		*Display selected map here
		*/
		JButton NextButton= new JButton("Continue");
		NextButton.setBounds(290,370,100,30);
		
		//Display selected map here

		NextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//jumps to new window here
				   Frame1.dispose();
                   SelectNoOfPlayers.assignCountries();				
			}
			
		});
		Frame1.add(Label1);
		Frame1.add(NextButton);
	   	
	   	NextButton.setVisible(true);
	   	Label1.setVisible(true);
	   	Frame1.setLayout(null);
	   	Frame1.setVisible(true);
	   	 
	   	
		 
		
	}

}
