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
import java.lang.ModuleLayer.Controller;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javafx.scene.layout.Border;

public class MFrame extends JFrame {
	private JButton[] button;
	JPanel jPanel2;
	JButton nextAction;
	String Phases[] = { "Finish Reinforcement", "Finish Attack", "Finish Fortification" };
	HashMap<String, JButton> hashButton;
	MyActionListner myActionListner;
	int playerTurn, noArmiesLeft, AAA, BBB, CCC;

	/*
	 * ReadingFiles files; Reinforcement reinforcement; MainControll controll;
	 */
	public MFrame(MyActionListner myActionListner) {
		// TODO Auto-generated constructor stub
		super("PAzim");
		this.myActionListner = myActionListner;
	}

	public void fun() throws IOException {
		/*
		 * files = new ReadingFiles(); reinforcement = new Reinforcement();
		 * files.Reads(); controll = new MainControll();
		 */ hashButton = new HashMap<>();
		FlowLayout flowLayout = new FlowLayout();
		BorderLayout borderLayout = new BorderLayout();
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		// Pannel 1
		JPanel jPanel = new JPanel(new GridLayout(2, 1));
		jPanel.setSize(new Dimension(500, 1000));
		jPanel.setBackground(Color.red);
		BufferedImage image = ImageIO.read(new File("Resources/Asia.bmp"));
		JLabel label = new JLabel(new ImageIcon(image));
		JScrollPane scroller = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jPanel.add(scroller);
		JPanel jPanel3 = new JPanel(new GridLayout(2, 1));
		JPanel jPanel4 = new JPanel(new GridLayout(0, 1));
		jPanel4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel jPanel5 = new JPanel();
		nextAction = new JButton("Finish Reinforcement");
		nextAction.addActionListener(myActionListner);
		jPanel5.add(nextAction);

		JLabel jLabel = new JLabel("Turn For:- " + playerTurn);
		// jLabel.setSize(MAXIMIZED_HORIZ, MAXIMIZED_VERT);
		JLabel jLabel2 = new JLabel("Armies Left:- " + noArmiesLeft);
		JLabel jLabel3 = new JLabel("asd.asdmk:- " + CCC);
		JLabel jLabel4 = new JLabel("HIIIIIII:- " + AAA);
		JLabel jLabel5 = new JLabel("HIIIIIII:- " + BBB);
		jPanel3.add(jPanel4);
		jPanel4.setBackground(Color.WHITE);
		jPanel3.add(jPanel5);
		jPanel4.add(jLabel);
		jPanel4.add(jLabel2);
		jPanel4.add(jLabel3);
		jPanel4.add(jLabel4);
		jPanel4.add(jLabel5);
		jPanel.add(jPanel3);
		// Pannel 2
		jPanel2 = new JPanel(new GridLayout(0, 5));
		jPanel2.setBackground(Color.BLACK);
		jPanel2.setSize(new Dimension(500, 1000));
		add(mainPanel);
		mainPanel.add(jPanel);
		mainPanel.add(jPanel2);
		Random random = new Random();

		setTitle("PAZIM");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void SetButtons(HashMap<String, Country> countryObjects) throws IOException {
		List<String> count = new ArrayList<>(countryObjects.keySet());
		button = new JButton[count.size()];
		Random random = new Random();

		for (int i = 0; i < count.size(); i++) {
			button[i] = new JButton(count.get(i) + " | " + countryObjects.get(count.get(i)).getNoOfArmies() + " | "
					+ countryObjects.get(count.get(i)).getContinent().getName());
			// button[i].setBackground(arrayList.get(random.nextInt(4)));
			hashButton.put((String) count.get(i), button[i]);
			jPanel2.add(button[i]);
			/*
			 * JButton butto= new JButton("1"); butto.setEnabled(false);
			 * butto.setBackground(Color.RED);
			 * 
			 */
			button[i].addActionListener(myActionListner);
		}

	}

	/*
	 * public void SetColorToAll(List<Country> countries, Player player) { for (int
	 * i = 0; i < countries.size(); i++) {
	 * hashButton.get(countries.get(i).getName()).setBackground(player.
	 * getPlayerColor()); }
	 * 
	 * }
	 */
	public void SetColorToAll(HashMap<String, Country> countryObjects) {
		List<String> count = new ArrayList<>(countryObjects.keySet());
		for (int i = 0; i < count.size(); i++) {
			hashButton.get(count.get(i)).setBackground(countryObjects.get(count.get(i)).getOwner().getPlayerColor());
		}

	}

	public void Refresh(HashMap<String, Country> countryObjects) throws IOException {
		List<String> count = new ArrayList<>(countryObjects.keySet());
		for (int i = 0; i < count.size(); i++) {
			button[i].setText(count.get(i) + " | " + countryObjects.get(count.get(i)).getNoOfArmies() + " | "
					+ countryObjects.get(count.get(i)).getContinent().getName());
			button[i].setName(count.get(i));

		}
	}

	public void RepaintAndRevalidate() {
		revalidate();
		repaint();
	}

	public void OnlyNeeded(List<Country> arrayList) {

		List<String> temp = new ArrayList<>(hashButton.keySet());
		for (int i = 0; i < arrayList.size(); i++) {
			temp.remove(arrayList.get(i).getName());
		}
		for (int i = 0; i < temp.size(); i++) {
			JButton tempb = hashButton.get(temp.get(i));
			tempb.setEnabled(false);
		}

	}

}