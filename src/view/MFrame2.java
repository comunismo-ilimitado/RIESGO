package view;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Class to display errors
 * 
 * @author pazim
 *
 */
public class MFrame2 {

	JFrame frame;
	JLabel jLabel;

	/**
	 * default constructor
	 */
	public MFrame2() {
	}

	/**
	 * throws error and restarts game
	 * 
	 * @param message
	 */
	public void error(String message) {
		JOptionPane.showMessageDialog(null, message);
		String args[] = new String[0];
		GameStartWindow.main(args);

	}
}
