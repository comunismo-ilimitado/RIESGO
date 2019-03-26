package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import model.CardTypes;
import model.Country;

/**
 * This class represents user interface for the game play
 * 
 * @author pazim
 * @version 1.0
 */
public class MFrame3 extends JFrame {
	private JButton[] button;
	JPanel jPanel2;
	JButton nextAction;
	JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel16, jLabel26, jLabel36, jLabel46, jLabel56, jLabel66,
			jLabel17, jLabel27, jLabel37, jLabel47, jLabel57, jLabel67;
	ArrayList<JLabel> jLabels, jLabels2;
	
	public MFrame3() {
		super("PazimGoyal");
	}

	/**
	 * This method displays the user interface
	 * @param list 
	 * 
	 *
	 */
	public void fun(List<CardTypes> list)  {
		FlowLayout flowLayout = new FlowLayout();
		JPanel mainPanel = new JPanel(flowLayout);
		jPanel2 = new JPanel(new GridLayout(0, 5));
		jPanel2.setSize(new Dimension(500, 1000));
		add(mainPanel);
		mainPanel.add(jPanel2);
		setTitle("PAZIMs Card Selection");
		setResizable(false);
		pack();
		SetButtons(list);
		JButton button=new JButton("NEXT");
		mainPanel.add(button);
		setVisible(true);

	}

	/**
	 * This method update the view
	 */
	

	/**
	 * This method assigns button to each country in UI
	 * 
	 * 
	 */
	public void SetButtons(List<CardTypes> cards)  {
		button = new JButton[cards.size()];
		Random random = new Random();

		for (int i = 0; i < cards.size(); i++) {
			button[i] = new JButton(cards.get(i).name());
			jPanel2.add(button[i]);
			//button[i].addActionListener(this);
		}

	}

	/**
	 * This method give color to the countries
	 * 
	 *
	 */

	public void error(String string) {
		JOptionPane.showMessageDialog(null, string);
	}
	public void ActivateAll() {
		for (int i = 0; i < button.length; i++) {
			button[i].setEnabled(true);
		}
	}

	public String popupText(int a) {
		return JOptionPane.showInputDialog("Enter Armies You wana Move Between, Maximum :-" + a);
	}
}
