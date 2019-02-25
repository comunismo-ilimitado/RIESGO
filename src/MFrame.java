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
	JPanel jPanel2;
	HashMap<String, JButton> hashButton;
	Reinforcement reinforcement;

	/*	ReadingFiles files;
	Reinforcement reinforcement;
	MainControll controll;
*/
	public MFrame(Reinforcement reinforcement) {
		// TODO Auto-generated constructor stub
		super("PAzim");
		this.reinforcement=reinforcement;
	}

	public void fun() throws IOException {
/*		 files = new ReadingFiles();
		 reinforcement = new Reinforcement();
		 files.Reads();
		controll = new MainControll();
*/		hashButton = new HashMap<>();
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
		JPanel jPanel3 = new JPanel(flowLayout);
		JLabel jLabel = new JLabel("DICES");
		jLabel.setSize(MAXIMIZED_HORIZ, MAXIMIZED_VERT);
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
		 jPanel2 = new JPanel(new GridLayout(0, 5));
		jPanel2.setBackground(Color.BLACK);
		jPanel2.setSize(new Dimension(500, 1000));
		add(mainPanel);
		mainPanel.add(jPanel);
		mainPanel.add(jPanel2);
		Random random = new Random();

		// ArrayList<String> count = (ArrayList<String>) files.CountriesNames;

		
		/*
		 * for (int i = 0; i < count.size(); i++) { button[i] = new JButton(count.get(i)
		 * + "\n" + random.nextInt(4)); //
		 * button[i].setBackground(arrayList.get(random.nextInt(4)));
		 * hashButton.put(count.get(i), button[i]); jPanel2.add(button[i]);
		 * button[i].addActionListener(this); }
		 */
		setTitle("PAZIM");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	public void SetButtons(ArrayList<String>  count) throws IOException {
/*		ArrayList<String> count = files.ContinentNames;
*/		button = new JButton[count.size()];
		Random random=new Random();
		
		for (int i = 0; i < count.size(); i++) {
			button[i] = new JButton(count.get(i) + "\n" + random.nextInt(4));
			// button[i].setBackground(arrayList.get(random.nextInt(4)));
			hashButton.put((String) count.get(i), button[i]);
			jPanel2.add(button[i]);
			button[i].addActionListener(reinforcement);
		}

	}
	
	public void SetColorToAll(List<Country> countries, Player player) {
		for(int i =0;i<countries.size();i++) {
			hashButton.get(countries.get(i).getName()).setBackground(player.getPlayerColor());	
		}
		
	}
	
	public void RepaintAndRevalidate() {
	    revalidate();
	    repaint();
	}

	public void OnlyNeeded(List<Country> arrayList) {
		/*for(int i=0;i<button.length;i++) {
			button[i].setEnabled(false);
			button[i].setBackground(Color.GRAY);
		}
		*/
		
		List<String> temp=new ArrayList<>(hashButton.keySet());
		for(int i=0;i<arrayList.size();i++) {
			temp.remove(arrayList.get(i).getName());
		}
		for(int i=0;i<temp.size();i++) {
			  JButton tempb= hashButton.get(temp.get(i));
			  tempb.setEnabled(false);
			  tempb.setBackground(Color.GRAY);
		}
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {

/*		// TODO Auto-generated method stub
		JButton b = hashButton.get(e.getActionCommand().split("\n")[0]);
		String abc = e.getActionCommand().split("\n")[0].trim();
		List<Country> cntries = reinforcement.playerPlaying.getTotalCountriesOccupied();
		String[] name = b.getText().split("\n");
		b.setText(name[0] + "" + (Integer.parseInt(name[1]) + 1));
*/
	}

}