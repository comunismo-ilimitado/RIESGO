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

public class BrowseMapFile {

	private static JFrame window;
	private JLabel HeaderLabel;
	private static JButton browse;
	private JButton load;
	private  static JLabel loc=null;
	private  static String location;
	private static String Filename;

	public BrowseMapFile() {
		
      setup();
	}

	public static void main(String[] args) {
		BrowseMapFile temp = new BrowseMapFile();
		temp.setup();

	}

	/**
     * Returns the path of the mapfile browsed
     * @return
     */
	public static String getLocation() {		
		return location;
	}
	/**
	 * Returns the name of the mapfile selected
	 * @return
	 */
	public static String getFileName() {
		return Filename;
	}
	
	public void setup() {
		window = new JFrame("Start-up phase");
		window.setSize(500, 500);

		HeaderLabel = new JLabel("Choose the map");
		HeaderLabel.setBounds(120, 100, 150, 50);

		loc = new JLabel("");
		loc.setBounds(100, 150, 300, 30);

		browse = new JButton("Browse");
		browse.setBounds(170, 200, 100, 30);

		load = new JButton("Load Map");
		load.setBounds(100, 300, 100, 30);

		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File f = fc.getSelectedFile();
					loc.setText(f.getAbsolutePath());
					location = loc.getText(); 
					
					try {
						Path source = Paths.get(location);
						String dest="Resources/"+f.getName();
						File fResource= new File(dest);
						fResource.createNewFile();
						OutputStream fos = new FileOutputStream(fResource);
						System.out.print("File created");
						Files.copy(source, fos);// FILE copied to resources
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					Filename=f.getName();
					System.out.println("File name selected is:"+getFileName());
					System.out.println("File location selected is:"+getLocation());
					
				}
				
			}
		});
		
        window.add(HeaderLabel);
		window.add(browse);
		window.add(load);
		window.add(loc);

		HeaderLabel.setVisible(true);

		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// MapSelection Map= new MapSelection();
				if(location==null)
					JOptionPane.showMessageDialog(null,"No file Selected!");
				else
				{
					LoadSelectedMap.loadMap();
				}
					
			}
		});

		browse.setVisible(true);
		load.setVisible(true);
		loc.setVisible(true);

		window.setLayout(null);
		window.setVisible(true);

	}
    
}
