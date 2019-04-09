package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.MainController;

/**
 * First Paga of Start up phase Start here
 *
 *
 */
public class GameStartWindow {

	private static JFrame window;
	private JButton single_game_button, tournament_button, resume_button;
	public static  int GameMode=0;

	public GameStartWindow() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		GameStartWindow temp = new GameStartWindow();
		temp.setup();
	}

	public void setup() {
		window = new JFrame("RISK");
		window.setSize(500, 700);

		single_game_button = new JButton("Single Game Mode");
		single_game_button.setBounds(100, 200, 200, 50);

		tournament_button = new JButton("Tournament Mode");
		tournament_button.setBounds(100, 300, 200, 50);

		resume_button = new JButton("Resume Game");
		resume_button.setBounds(100, 400, 200, 50);

		window.add(single_game_button);
		window.add(tournament_button);
		window.add(resume_button);

		single_game_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				window.dispose();
				GameMode=1;
				SelectMapType temp = new SelectMapType();

			}
		});
		tournament_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				window.dispose();
				GameMode=2;
				SelectMap Map = new SelectMap();			}
		});

		resume_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainController controller=new MainController();
				controller.resume=true;
				try {
					controller.Function();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				window.dispose();
				
			}
		});

		single_game_button.setVisible(true);
		tournament_button.setVisible(true);
		File tempFile = new File( "Resources/SaveGame.txt" ); 
		boolean exists = tempFile.exists();
		resume_button.setVisible(true);
		if(!exists) {
			resume_button.setEnabled(false);
		}else {
			resume_button.setEnabled(true);
		}
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		window.setVisible(true);
	}

}
