package view.menuFrames;

import controller.game.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Start up phase starts here
 */
public class GameStartWindow {

    private static JFrame window;
    private JButton single_game_button, tournament_button, resume_button;
    public static int GameMode = 0;

    /**
     * Auto-Generated Constructor
     */
    public GameStartWindow() {}

    public static void main(String[] args) {
        GameStartWindow temp = new GameStartWindow();
        temp.setup();
    }

    /**
     * This method implements the initial user interface of the game.
     */
    public void setup() {
        window = new JFrame("RISK");
        window.setSize(500, 700);
        single_game_button = new JButton("Single Game Mode");
        single_game_button.setBounds(100, 200, 200, 50);
        tournament_button = new JButton("Tournament Mode");
        tournament_button.setBounds(100, 300, 200, 50);
        resume_button = new JButton("Resume Game");
        resume_button.setBounds(100, 400, 200, 50);
        window.add(single_game_button);
        window.add(tournament_button);
        window.add(resume_button);

        single_game_button.addActionListener(event -> {
                window.dispose();
                GameMode = 1;
                SelectMapType temp = new SelectMapType();
        });
        tournament_button.addActionListener(event -> {
                window.dispose();
                GameMode = 2;
                SelectMap Map = new SelectMap();
        });

        resume_button.addActionListener(event -> {
                MainController controller = new MainController();
                controller.resume = true;
                try {
                    controller.Function();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                window.dispose();
        });

        single_game_button.setVisible(true);
        tournament_button.setVisible(true);
        File tempFile = new File("Resources/SaveGame.txt");
        boolean exists = tempFile.exists();
        resume_button.setVisible(true);
        resume_button.setEnabled(exists);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setVisible(true);
    }
}
