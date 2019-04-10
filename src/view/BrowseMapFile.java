package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;


/**
 * This class browse the map file
 * @author pazim
 *@version 1.0
 */
public class BrowseMapFile {

	private static JFrame Window;
	private JLabel header_label;
	private static JButton Browse;
	private JButton load;
	private  static JLabel Loc=null;
	private  static String Location;
	private static String FileName;
	private static JButton EditButton;


	public BrowseMapFile() {
		
      setUp();
	}
	/**
     * Returns the path of the mapfile Browsed
     * @return
     */
	public static String getLocation() {		
		return Location;
	}
	/**
	 * Returns the name of the mapfile selected
	 * @return
	 */
	public static String getFileName() {
		return FileName;
	}
	/**
	 * this method is used to select and load the map file.
	 */
	public void setUp() {
		Window = new JFrame("Start-up phase");
		Window.setSize(500, 500);

		header_label = new JLabel("Choose the map");
		header_label.setBounds(120, 100, 150, 50);

		Loc = new JLabel("");
		Loc.setBounds(100, 150, 300, 30);

		Browse = new JButton("Browse");
		Browse.setBounds(170, 200, 100, 30);

		load = new JButton("Load Map");
		load.setBounds(100, 300, 100, 30);
		
		EditButton = new JButton("EDIT");
		EditButton.setBounds(275, 200, 90, 20);
		
		EditButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(Location==null)
					JOptionPane.showMessageDialog(null,"No file Selected!");
				else
				{
					Window.dispose();
					SelectMapType.MapType=5;
					EditContinents obj= new EditContinents();
				}
			}
		});

		Browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File f = fc.getSelectedFile();
					Loc.setText(f.getAbsolutePath());
					Location = Loc.getText(); 
					
					try {
						Path source = Paths.get(Location);
						String dest="Resources/LoadedMap.map";
						File fResource= new File(dest);
						fResource.createNewFile();
						OutputStream fos = new FileOutputStream(fResource);
				//		System.out.print("File created");
						Files.copy(source, fos);// FILE copied to resources
					} catch (IOException e) {
						// TODO Auto-generated catch bLock
						e.printStackTrace();
					}
					
					
					FileName=f.getName();
//					System.out.println("File name selected is:"+getFileName());
	//				System.out.println("File Location selected is:"+getLocation());
					
				}
				
			}
		});
		
        Window.add(header_label);
		Window.add(Browse);
		Window.add(load);
		Window.add(Loc);
		Window.add(EditButton);


		header_label.setVisible(true);
		/**
		 *this listener is used if no map file selected by user else will assign countries.
		 */

		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// MapSelection Map= new MapSelection();
				if(Location==null)
					JOptionPane.showMessageDialog(null,"No file Selected!");
				else
				{
					Window.dispose();
					SelectNoOfPlayers.assignCountries();
				}
					
			}
		});

		Browse.setVisible(true);
		load.setVisible(true);
		Loc.setVisible(true);

		Window.setLayout(null);
		Window.setVisible(true);

	}
    
}
