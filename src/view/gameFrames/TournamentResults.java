package view.gameFrames;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TournamentResults {

    private JFrame window;
    private JPanel panel;
    private JLabel HeaderLabel;
    private JTextArea field;

    public TournamentResults() {
//		Tournament t= new Tournament();
        // TODO Auto-generated constructor stub
    }

    public void setup(int a) {
        window = new JFrame("Tournament Results");
        window.setSize(500, 700);
        panel = new JPanel(new GridLayout(0, a));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        HeaderLabel = new JLabel("Results");
        HeaderLabel.setBounds(120, 100, 150, 50);
        HeaderLabel.setVisible(true);
    }

    public void adding(String aaa) {
        JLabel jLabel = new JLabel(aaa);
        jLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(jLabel);
    }

    public void show() {
        window.add(panel);
        window.setVisible(true);
    }

}
