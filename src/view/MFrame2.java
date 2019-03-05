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

public class MFrame2 {
	/**
	 * 
	 */
	JFrame frame;
	JLabel jLabel;

	public MFrame2() {

	/*
	 * frame = new JFrame("samdlknas;"); JPanel jPanel = new JPanel(); JButton
	 * button = new JButton("Start Again"); jLabel = new JLabel("Message");
	 * frame.add(jPanel); jPanel.add(jLabel); button.setSize(100, 100);
	 * jPanel.add(button); frame.setMinimumSize(new Dimension(500, 500));
	 * frame.setVisible(false);
	 * 
	 * button.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent arg0) { try {
	 * Runtime.getRuntime().exec("java StartUpWindow"); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } System.exit(0); } });
	 * }
	 */}
	public void error(String message) {
		/*
		 * System.out.println(message); jLabel.setText(message); frame.setVisible(true);
		 */ JOptionPane.showMessageDialog(null, message);

	}
}
