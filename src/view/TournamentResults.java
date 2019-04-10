package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Tournament;

public class TournamentResults {

	private JFrame window;
	private JPanel panel;
	private JLabel HeaderLabel;
	private JTextArea field;
	
	public TournamentResults() {
//		Tournament t= new Tournament();
		setup();
		// TODO Auto-generated constructor stub
	}
	public void setup() {
		window = new JFrame("Tournament Results");
		window.setSize(500, 700);
		
//		panel= new JPanel(new Dimension(500,500));
				
		field=new JTextArea(500,500);
		field.setText(Tournament.table);

		HeaderLabel = new JLabel("Results");
		HeaderLabel.setBounds(120, 100, 150, 50);

//		panel.add(HeaderLabel);
//		panel.add(field);
		
		HeaderLabel.setVisible(true);
		field.setVisible(true);
		window.add(field);
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		window.setVisible(true);
	}

}
