package view.gameFrames;

import view.menuFrames.GameStartWindow;

import javax.swing.*;

/**
 * Class to display errors
 *
 * @author pazim
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
        String[] args = new String[0];
        GameStartWindow.main(args);

    }
}
