\package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * First Paga of Start up phase
 * Start here
 *
 *
 */
public class UserSelectedOrLoad {

	private static JFrame window;
	private JLabel HeaderLabel;
	private JButton select1, select2;

	public UserSelectedOrLoad() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
      UserSelectedOrLoad temp= new UserSelectedOrLoad();
      temp.setup();
      
	}

	 public void setup() {
		window = new JFrame("Start-up phase");
		window.setSize(500, 500);

		HeaderLabel = new JLabel("Choose the map");
		HeaderLabel.setBounds(120, 100, 150, 50);

		select1 = new JButton("Select from List");
		select1.setBounds(100, 200, 200, 50);

		select2 = new JButton("Load Map");
		select2.setBounds(100, 300, 200, 50);

		window.add(HeaderLabel);
		window.add(select1);
		window.add(select2);

		HeaderLabel.setVisible(true);
		
		select1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				window.dispose();
 				MapSelection Map= new MapSelection();
			}});
		select2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				window.dispose();
				BrowseMapFile Map= new BrowseMapFile();
			}});

		select1.setVisible(true);
		select2.setVisible(true);


		window.setLayout(null);
		window.setVisible(true);
	}

}
