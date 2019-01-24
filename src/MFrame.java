
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MFrame extends JFrame implements ActionListener {
	private  JButton button;
	private  JButton button2;
	public MFrame() {
		// TODO Auto-generated constructor stub
		super("PAzim");
	}

	public void fun() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		JPanel jPanel = new JPanel();
		jPanel.setSize(new Dimension(100, 100));
		JPanel jPanel2 = new JPanel();
		jPanel2.setSize(new Dimension(300, 200));		
		jPanel.setBackground(Color.BLACK);
		jPanel2.setBackground(Color.RED);
		jPanel2.setMaximumSize(new Dimension(1000, 400));
		add(mainPanel);
		mainPanel.add(jPanel);
		mainPanel.add(jPanel2);
		button = new JButton("sffda");
		button2 = new JButton("sffda");
		button2.setEnabled(false);
		button.addActionListener(this);
		button2.addActionListener(this);

		jPanel.add(button);
		jPanel2.add(button2);

		setTitle("PAZIM");

		setSize(new Dimension(2000, 1000));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == button) {
			button2.setEnabled(true);

		}
		if (arg0.getSource() == button2) {
			button2.setEnabled(false);

		}

	}
}
