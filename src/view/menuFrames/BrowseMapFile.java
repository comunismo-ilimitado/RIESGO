package view.menuFrames;

import controller.editor.ReadingFiles;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * This class browse or edit map files
 *
 * @author pazim
 * @version 1.0
 */
public class BrowseMapFile {

    private static JFrame Window;
    private JLabel header_label;
    private static JButton Browse;
    private JButton load;
    private static JLabel Loc = null;
    private static JButton EditButton;

    public BrowseMapFile() {
        setUp();
    }

    /**
     * This method is used to select, load or edit map files.
     */
    private void setUp() {
        Window = new JFrame("Start-up phase");
        Window.setSize(500, 500);
        header_label = new JLabel("Choose the map");
        header_label.setBounds(120, 100, 150, 50);
        Loc = new JLabel("");
        Loc.setBounds(100, 150, 300, 30);
        Browse = new JButton("Browse");
        Browse.setBounds(170, 200, 100, 30);
        load = new JButton("Load Map");
        load.setBounds(100, 300, 100, 30);
        EditButton = new JButton("EDIT");
        EditButton.setBounds(275, 200, 90, 20);
        EditButton.addActionListener(actionEvent -> {  //Boton edit
            if (ReadingFiles.Location == null)
                JOptionPane.showMessageDialog(null, "No file Selected!");
            else {
                Window.dispose();
                ReadingFiles.MapType = 5;
            }
        });
        Browse.addActionListener(actionEvent -> {  //Boton browse
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(Paths.get("").toFile());
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                Loc.setText(f.getAbsolutePath());
                ReadingFiles.Location = Loc.getText();
                try {
                    Path source = Paths.get(ReadingFiles.Location);
                    String dest = "Resources/OldResources/LoadedMap.map";
                    File fResource = new File(dest);
                    fResource.createNewFile();
                    OutputStream fos = new FileOutputStream(fResource);
                    Files.copy(source, fos);
                } catch (IOException e) {
                    // TODO Auto-generated catch bLock
                    e.printStackTrace();
                }
                ReadingFiles.FileName = f.getName();
            }

        });
        Window.add(header_label);
        Window.add(Browse);
        Window.add(load);
        Window.add(Loc);
        Window.add(EditButton);
        header_label.setVisible(true);
        load.addActionListener(actionEvent -> {  //Boton load
            if (ReadingFiles.Location == null)
                JOptionPane.showMessageDialog(null, "No file Selected!");
            else {
                Window.dispose();
                SelectNoOfPlayers.assignCountries();
            }
        });
        Browse.setVisible(true);
        load.setVisible(true);
        Loc.setVisible(true);
        Window.setLayout(null);
        Window.setVisible(true);
    }
}
