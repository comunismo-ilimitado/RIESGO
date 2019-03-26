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
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import controller.MyActionListner;
import model.CardTypes;
import model.Country;

/**
 * This class represents user interface for the game play
 * 
 * @author pazim
 * @version 1.0
 */
public class MFrame4 extends JFrame implements ActionListener {
	JPanel jPanel2;
	JTextField field, field2;
	JButton button, button2;
	public static int no1, no2 = 1;
	public static boolean allout = false;
	MyActionListner actionListner;

	public MFrame4() {
		super("Dices and All out ");
		this.actionListner = actionListner;
		}

	/**
	 * This method displays the user interface
	
	 */
	public void fun() {
		FlowLayout flowLayout = new FlowLayout();
		JPanel mainPanel = new JPanel(new GridLayout(0, 1));
		setMinimumSize(new Dimension(500, 500));
		add(mainPanel);
		mainPanel.add(new JLabel("Enter Your Dice Rolls"));
		field = new JTextField();
		field.setSize(300, 300);
		field2 = new JTextField();
		button = new JButton("All Out");
		button2 = new JButton("Single Attack");
		setTitle("PAZIMs Card Selection");
		button.addActionListener(this);
		button2.addActionListener(this);
		mainPanel.add(field);
		mainPanel.add(new JLabel("Enter Opponent Dice Rolls"));
		mainPanel.add(field2);
		mainPanel.add(button);
		mainPanel.add(button2);
		setResizable(false);
		pack();
		setVisible(true);

	}

	/**
	 * This method update the view
	 */

	/**
	 * This method assigns button to each country in UI
	 * 
	 * 
	 *
	 */

	/**
	 * This method give color to the countries
	 * 
	 *
	 */

	public void popupText(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			no1 = Integer.parseInt(field.getText());
			no2 = Integer.parseInt(field2.getText());

			if (arg0.getActionCommand() == "All Out") {
				allout = true;
			} else {
				allout = false;
			}
			System.out.println("" + no1 + no2 + allout);
			actionListner.notify();
			dispose();
		}

		catch (Exception e) {
			// TODO: handle exception
			popupText("Enter Valid Values");
		}

	}

}
