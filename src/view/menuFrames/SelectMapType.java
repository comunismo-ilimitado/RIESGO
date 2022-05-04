package view.menuFrames;

import controller.editor.ReadingFiles;
import view.editorFrames.CreateMap;

import javax.swing.*;

/**
 * Continuation of the start up phase
 */
public class SelectMapType {

    private JFrame window;
    private JLabel HeaderLabel;
    private JButton select1, select2, select3;


    /**
     * Constructor. It calls the setup() method.
     */
    public SelectMapType() {
        setup();
    }

    /**
     * This method implements the second user interface of the game.
     */
    public void setup() {
        window = new JFrame("Start-up phase");
        window.setSize(500, 700);
        HeaderLabel = new JLabel("Choose the map");
        HeaderLabel.setBounds(120, 100, 150, 50);
        select1 = new JButton("Select from List");
        select1.setBounds(100, 200, 200, 50);
        select2 = new JButton("Load Map");
        select2.setBounds(100, 300, 200, 50);
        select3 = new JButton("Create New Map");
        select3.setBounds(100, 400, 200, 50);
        window.add(HeaderLabel);
        window.add(select1);
        window.add(select2);
        window.add(select3);
        HeaderLabel.setVisible(true);
        //"Select from list"
        select1.addActionListener(event -> {
                window.dispose();
                ReadingFiles.MapType = 1;
                SelectMap Map = new SelectMap();
        });
        //"Load Map"
        select2.addActionListener(event -> {
                ReadingFiles.MapType = 2;
                window.dispose();
                BrowseMapFile Map = new BrowseMapFile();
        });
        //"Create New Map"
        select3.addActionListener(event -> {
                ReadingFiles.MapType = 3;
                window.dispose();
                CreateMap CreatedMap = new CreateMap();
        });

        select1.setVisible(true);
        select2.setVisible(true);
        select3.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setVisible(true);
    }
}
