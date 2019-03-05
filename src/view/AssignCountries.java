package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AssignCountries {

	private static JComboBox PNumberJCombo;
	private static JButton OKButton;
	private static JFrame Frame1;
	public static void main(String[] args) {
		AssignCountries temp= new AssignCountries();
		temp.assignCountries();
		NumberOfPlayers=temp.getNumberOfPlayer();
	}
	public static int NumberOfPlayers=2;

	/**
	 * returns the number of player chooses as integer
	 * @return int
	 */
	public static int getNumberOfPlayer() {
		OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 NumberOfPlayers = Integer.parseInt((String) PNumberJCombo.getSelectedItem());	
				 System.out.print("returns" + NumberOfPlayers);
				 Frame1.dispose();
				
				try {
					 MainControll controll = new MainControll();
					controll.Function();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
					
			} 
			
		});
		return NumberOfPlayers;
	}
	public static void assignCountries() {
		Frame1= new JFrame("Map");
		Frame1.setSize(500, 500);
		
		JLabel Label1 = new JLabel("STEP 2:    Select number of Players ");
		Label1.setBounds(90,100,250,50);;

		OKButton= new JButton("OK");
		OKButton.setBounds(290,190,100,30);
		
String[] select= {"2", "3", "4","5", "6"};

		PNumberJCombo= new JComboBox(select);
		PNumberJCombo.setBounds(120,150,200,20);
	   	
		
		Frame1.add(Label1);
		Frame1.add(OKButton);
		Frame1.add(PNumberJCombo);
	   	
	   	OKButton.setVisible(true);
	   	PNumberJCombo.setVisible(true);
	   	Label1.setVisible(true);
	   	Frame1.setLayout(null);
	   	Frame1.setVisible(true);
	   	NumberOfPlayers=getNumberOfPlayer();
	}
	
	

}