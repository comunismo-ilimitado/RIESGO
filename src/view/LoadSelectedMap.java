package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class LoadSelectedMap extends JFrame {

	private static JFrame Frame1;
	public static String MapImage = "noimage.bmp";
	static BufferedImage image;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        LoadSelectedMap temp= new LoadSelectedMap();
        LoadSelectedMap.loadMap();
        
	}
	public static void loadMap() throws IOException {
	
	    Frame1= new JFrame("Map");
		Frame1.setSize(1000, 1000);
		
		JLabel Label1= new JLabel("MAP LOADED");
		Label1.setBounds(90,40,150,50);

		JButton NextButton= new JButton("Continue");
		NextButton.setBounds(790,770,100,30);
		
		//Display selected map here

		MapImage="Canada";
		MapImage=MapSelection.getSelectedMap();
		if(MapImage!=null)		System.out.print(MapImage);
				
					image = ImageIO.read(new File("Resources/"+ MapImage+".bmp"));
				
				JLabel piclabel = new JLabel(new ImageIcon(image));

				
				piclabel.setBounds(90,100,image.getTileWidth(),image.getTileHeight());
		
		NextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//jumps to new window here
				   Frame1.dispose();
                   AssignCountries.assignCountries();				
			}
			
		});
		Frame1.add(Label1);
		Frame1.add(piclabel);
		Frame1.add(NextButton);
		Frame1.setExtendedState(JFrame.MAXIMIZED_BOTH);

	   	NextButton.setVisible(true);
	   	Label1.setVisible(true);
	   	Frame1.setLayout(null);
	   	Frame1.setVisible(true);
	   	piclabel.setVisible(true);
	   	//setVisible(true);
	   	 
	   	
		 
		
	}

}
