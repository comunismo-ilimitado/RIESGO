package view.editorFrames;

import controller.editor.ReadingFiles;
import model.Country;
import view.gameFrames.BoardController;
import view.gameFrames.GameUIController;
import view.gameFrames.MFrame2;
import view.menuFrames.SelectMap;
import view.menuFrames.SelectMapType;
import view.menuFrames.SelectNoOfPlayers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class edit the continent user view
 *
 * @author pazim
 * @version 1.0
 */
public class EditContinents {
    private static JFrame MapFrame;
    private JLabel HeaderLabel;
    private JLabel HeaderLabel2;
    private static JButton SelectButton;
    private static JButton SetButton;
    private static JButton NextButton;

    private static JComboBox ContinentsCombo;

    int len = 0;
    private static String ContinentSelected = "";
    private static final String CountrySelected = "";
    private final JCheckBox[] CheckBoxes = new JCheckBox[150];
    private final JLabel[] CountriesCheck = new JLabel[150];

    static List<String> MapFiles = new ArrayList<String>();
    ReadingFiles ReadFile;
    static List<String> Countries = new ArrayList<String>();
    static List<String> Continents = new ArrayList<String>();

    /**
     * Constructor, creates UI elements and loads a Map
     */
    public EditContinents() {
        BoardController controller = new GameUIController();
        ReadFile = new ReadingFiles(controller);
        String address = "Resources/" + SelectMap.MapSelected + ".map";

        if (SelectMapType.MapType == 4)
            address = "Resources/" + SelectMap.MapSelected + ".map";
        else if (SelectMapType.MapType == 5)
            address = "Resources/LoadedMap.map";
        else if (SelectMapType.MapType == 6)
            address = "Resources/UserMap.map";
        try {
            ReadFile.Reads(address, SelectNoOfPlayers.NumberOfPlayers);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (ReadingFiles.getCountryNameObject() != null)
            System.out.println("Initial list of elements: " + ReadingFiles.getCountriesNames());
        System.out.println("Initial list of elements: " + ReadingFiles.getContinentNames());

        setUp();
        getSelectedContinent();
    }

    /**
     * Set the Continents in the view
     */
    private void setUp() {

        if (ReadingFiles.getCountryNameObject() != null)
            MapFrame = new JFrame("Edit Map");
        MapFrame.setSize(900, 900);

        HeaderLabel = new JLabel("Continents");
        HeaderLabel.setBounds(50, 50, 150, 50);

        HeaderLabel2 = new JLabel("Contains");
        HeaderLabel2.setBounds(600, 20, 150, 50);

        String[] Maps = ReadingFiles.getContinentNames().toArray(new String[0]);
        ContinentsCombo = new JComboBox(Maps);
        ContinentsCombo.setBounds(120, 150, 200, 20);

        for (int i = 0, k = 0, j = 30; i < ReadingFiles.getCountriesNames().size(); i++, j = j + 30) {
            CheckBoxes[i] = new JCheckBox(ReadingFiles.getCountriesNames().get(i));
        }
        SelectButton = new JButton("SELECT");
        SelectButton.setBounds(175, 200, 90, 20);

        SetButton = new JButton("SET");
        SetButton.setBounds(700, 700, 90, 20);

        NextButton = new JButton("NEXT");
        NextButton.setBounds(1000, 700, 90, 20);

        NextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                MapFrame.dispose();
                EditCountries e = new EditCountries();
            }
        });

        SetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ArrayList<Country> countries = new ArrayList<>();
                for (int i = 0; i < ReadingFiles.getCountriesNames().size(); i++) {
                    if (CheckBoxes[i].isSelected()) {
                        countries.add(ReadingFiles.getCountryNameObject().get(CheckBoxes[i].getText()));
                        System.out.println(
                                "caught:" + ReadingFiles.getCountryNameObject().get(CheckBoxes[i].getText()).getName());
                        ReadingFiles.getCountryNameObject().get(CheckBoxes[i].getText())
                                .setContinent(ReadingFiles.getContinentNameObject().get(ContinentSelected));
                        System.out.println("cont:"
                                + ReadingFiles.getCountryNameObject().get(CheckBoxes[i].getText()).getContinent().getName());
                        CheckBoxes[i].setSelected(false);
                        JOptionPane.showMessageDialog(null, CheckBoxes[i].getText() + " added to " + ContinentSelected);
                    }
                }
                ReadingFiles.getContinentNameObject().get(ContinentSelected).setCountries(countries);
            }
        });

        MapFrame.add(HeaderLabel);
        MapFrame.add(HeaderLabel2);
        MapFrame.add(ContinentsCombo);

        MapFrame.add(SelectButton);
        MapFrame.add(SetButton);

        MapFrame.add(NextButton);
        HeaderLabel.setVisible(true);
        HeaderLabel2.setVisible(true);
        ContinentsCombo.setVisible(true);
        SetButton.setVisible(true);

        SelectButton.setVisible(true);
        NextButton.setVisible(true);
        MapFrame.setLayout(null);
        MapFrame.setVisible(true);
        MapFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        MapFrame.setResizable(true);
        MapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * Gets selected continent action listener ready
     *
     */
    private void getSelectedContinent() {

        SelectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                for (int m = 0; m < len; m++) {
                    CountriesCheck[m].setVisible(false);
                    MapFrame.remove(CountriesCheck[m]);
                }
                len = 0;
                // TODO Auto-generated method stub
                ContinentSelected = (String) ContinentsCombo.getSelectedItem();
                System.out.print(ContinentSelected);
                for (int i = 0, k = 0, j = 30; i < ReadingFiles.getCountriesNames().size(); i++, j = j + 30) {
                    for (model.Country s : ReadingFiles.getContinentNameObject().get(ContinentSelected).getCountries()) {
                        if (s.getName().compareTo(ReadingFiles.getCountriesNames().get(i)) == 0) {
                            CountriesCheck[k] = new JLabel("yes");

                            MapFrame.add(CountriesCheck[k]);
                            CountriesCheck[k].setBounds(600, j, 150, 50);
                            CountriesCheck[k].setVisible(true);
                            len++;
                            k++;
                        }
                    }
                    MapFrame.add(CheckBoxes[i]);
                    CheckBoxes[i].setBounds(400, j, 150, 50);
                    CheckBoxes[i].setVisible(true);
                }
            }

        });
    }

}
