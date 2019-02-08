import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javafx.scene.layout.Border;

public class MFrame extends JFrame implements ActionListener {
	private JButton[] button;
	HashMap<String, JButton> hashButton;
	ReadingFiles files;
Reinforcement reinforcement;
	public MFrame() {
		// TODO Auto-generated constructor stub
		super("PAzim");
	}

	public void fun() throws IOException {
		files = new ReadingFiles();
		reinforcement=new Reinforcement();
		files.Reads();
		hashButton = new HashMap<>();
		FlowLayout flowLayout = new FlowLayout();
		BorderLayout borderLayout = new BorderLayout();
		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		// Pannel 1
		JPanel jPanel = new JPanel(new GridLayout(2, 1));
		jPanel.setSize(new Dimension(500, 1000));
		jPanel.setBackground(Color.red);
		BufferedImage image = ImageIO.read(new File("Resources/Map.jpg"));
		JLabel label = new JLabel(new ImageIcon(image));
		JScrollPane scroller = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jPanel.add(scroller);
		JPanel jPanel3 = new JPanel(flowLayout);
		JLabel jLabel = new JLabel("DICES");
		jLabel.setSize(MAXIMIZED_HORIZ,MAXIMIZED_VERT);
		jLabel.setBackground(Color.WHITE);
		JLabel jLabel2 = new JLabel("HIIIIIII");
		jLabel.setBackground(Color.BLACK);
		JLabel jLabel3 = new JLabel("HIIIIIII");
		jLabel.setBackground(Color.WHITE);
		JLabel jLabel4 = new JLabel("HIIIIIII");
		jLabel.setBackground(Color.BLACK);
		JLabel jLabel5 = new JLabel("HIIIIIII");
		jLabel.setBackground(Color.WHITE);
		jPanel3.add(jLabel);
		jPanel3.add(jLabel2);
		jPanel3.add(jLabel3);
		jPanel3.add(jLabel4);
		jPanel3.add(jLabel5);
		jPanel.add(jPanel3);
		// Pannel 2
		JPanel jPanel2 = new JPanel(new GridLayout(0, 5));
		jPanel2.setBackground(Color.BLACK);
		jPanel2.setSize(new Dimension(500, 1000));
		add(mainPanel);
		mainPanel.add(jPanel);
		mainPanel.add(jPanel2);
		Random random = new Random();
		ArrayList<String> count = (ArrayList<String>) files.CountriesNames;
		button = new JButton[count.size()];
		for (int i = 0; i < count.size(); i++) {
			button[i] = new JButton(count.get(i) + "\n" + random.nextInt(4));
//			button[i].setBackground(arrayList.get(random.nextInt(4)));
			hashButton.put(count.get(i), button[i]);
			jPanel2.add(button[i]);
			button[i].addActionListener(this);
		}
		setTitle("PAZIM");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		// TODO Auto-generated method stub
		JButton b = hashButton.get(e.getActionCommand().split("\n")[0]);
		String abc = e.getActionCommand().split("\n")[0].trim();
		List<Country> cntries = reinforcement.playerPlaying.getTotalCountriesOccupied();
		String[] name = b.getText().split("\n");
		b.setText(name[0]+""+(Integer.parseInt(name[1])+1));

	}
}