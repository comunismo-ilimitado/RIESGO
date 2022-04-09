package view.editorFrames;

import controller.editor.SaveCreatedMap;
import model.Continent;
import model.Country;
import view.menuFrames.SelectMapType;
import view.menuFrames.SelectNoOfPlayers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Class dedicated for creating a custom map file.
 */
public class CreateMap {

    private JPanel assign_countries_panel, assign_neighbours_panel;
    private JFrame assign_countries_frame, assign_neighbours_frame, create_map_frame2, get_number_frame;
    private JLabel no_of_countries_label, no_of_continents_label, continent_contol_value_label;
    private JTextField no_of_countries_text_field, no_of_continents_text_field, control_value_text_field;
    private JButton generate_map_button, add_continent_name_button;
    private JButton add_to_country_button, add_to_continent_button, next_button, done_button;
    private JComboBox continents_combo, countries_combo;
    private int NoOfCountries, NoOfContinents;
    private final List<Continent> continents = new ArrayList<Continent>();
    private final JCheckBox[] check_boxes = new JCheckBox[150];
    private final JCheckBox[] check_boxes1 = new JCheckBox[150];
    DefaultListModel<String> countries_to_continent_list;
    private List<Continent> ContinentsObjectList = new ArrayList<>();
    private List<Country> CountriesObjectList = new ArrayList<>();

    private static JButton EditButton;

    private static final int Index1 = 0;

    public CreateMap() {
        GetNumbers();
    }

    /**
     * This method shows UI for the "Get Numbers" phase,
     */
    private void GetNumbers() {
        get_number_frame = new JFrame("Create Map");
        get_number_frame.setSize(500, 500);
        no_of_countries_label = new JLabel("No. of Countries");
        no_of_countries_label.setBounds(200, 27, 150, 50);
        no_of_continents_label = new JLabel("No of continents");
        no_of_continents_label.setBounds(10, 27, 150, 50);
        no_of_countries_text_field = new JTextField("3");
        no_of_countries_text_field.setBounds(300, 43, 50, 20);
        no_of_continents_text_field = new JTextField("2");
        no_of_continents_text_field.setBounds(110, 43, 50, 20);
        generate_map_button = new JButton("Generate Map");
        generate_map_button.setBounds(200, 90, 150, 30);
        get_number_frame.add(no_of_continents_label);
        get_number_frame.add(no_of_countries_label);
        get_number_frame.add(no_of_countries_text_field);
        get_number_frame.add(no_of_continents_text_field);
        get_number_frame.add(generate_map_button);
        get_number_frame.setLayout(null);
        get_number_frame.setVisible(true);
        PrepareValidationNumbers();
    }

    /**
     * This method checks validations of the map numbers.
     */
    private void PrepareValidationNumbers() {

        generate_map_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                NoOfCountries = Integer.parseInt(no_of_countries_text_field.getText());
                NoOfContinents = Integer.parseInt(no_of_continents_text_field.getText());
                if (NoOfCountries <= 2 || NoOfContinents <= 1) {
                    JOptionPane.showMessageDialog(null, "Values should be greater than 2");
                } else if (NoOfCountries < NoOfContinents) {
                    JOptionPane.showMessageDialog(null, "NO of Countries shuld be greater than continents");
                } else {
                    get_number_frame.dispose();
                    AssignCountriesFrame();
                }

            }
        });

    }

    /**
     * UI for assigning countries to continents
     */
    private void AssignCountriesFrame() {
        assign_countries_frame = new JFrame("Assign Countries To continents");
        assign_countries_panel = new JPanel();
        assign_countries_panel.setBounds(440, 80, 200, 200);
        assign_countries_panel.setBackground(Color.gray);
        assign_countries_panel.setSize(100, 800);
        assign_countries_frame.setSize(1000, 1000);

        continent_contol_value_label = new JLabel("Continent Control Value");
        continent_contol_value_label.setBounds(10, 200, 150, 50);
        add_to_continent_button = new JButton("Add Countries");
        add_to_continent_button.setBounds(575, 500, 150, 30);

        next_button = new JButton("Next");
        next_button.setBounds(700, 700, 150, 30);
        continents_combo = new JComboBox();
        continents_combo.setBounds(10, 150, 200, 20);
        control_value_text_field = new JTextField("0");
        control_value_text_field.setBounds(10, 250, 50, 20);
        countries_to_continent_list = new DefaultListModel<>();

        assign_countries_frame.add(assign_countries_panel);
        assign_countries_frame.add(next_button);
        assign_countries_frame.add(continent_contol_value_label);
        assign_countries_frame.add(control_value_text_field);
        assign_countries_frame.add(add_to_continent_button);
        assign_countries_frame.add(continents_combo);
        assign_countries_frame.setLayout(null);

        for (int i = 1; i <= NoOfContinents; i++) {
            continents_combo.addItem("Continent" + i);
        }
        for (int i = 0, j = 150; i < NoOfCountries; i++, j = j + 30) {
            check_boxes[i] = new JCheckBox("Country" + (i + 1));
            assign_countries_panel.add(check_boxes[i]);
            check_boxes[i].setBounds(300, j, 150, 50);
            check_boxes[i].setVisible(true);
        }
        assign_countries_frame.setVisible(true);
        ActionAddContinent();
        ActionNextButtonFrame();
    }

    /**
     * Action Listener for adding a country to a continent
     */
    private void ActionAddContinent() {
        add_to_continent_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                List<Country> TempCountriesObject = new ArrayList<Country>();

                for (int i = 0; i < NoOfCountries; i++) {
                    if (check_boxes[i].isSelected()) {
                        Country temp = new Country(check_boxes[i].getText());
                        TempCountriesObject.add(temp);
                        check_boxes[i].setVisible(false);
                        assign_countries_panel.updateUI();
                        check_boxes[i].setSelected(false);
                    }
                }
                if(continents_combo.getSelectedItem() != null) {
                    Continent tempc = new Continent(Integer.parseInt(control_value_text_field.getText()),
                            (String) continents_combo.getSelectedItem());
                    tempc.setCountries(TempCountriesObject);
                    if (tempc.getName() != null) {
                        ContinentsObjectList.add(tempc);
                    }
                    continents_combo.removeItem(continents_combo.getSelectedItem());
                }
            }
        });
    }

    /**
     * Validation for the continent information.
     */
    private void ActionNextButtonFrame() {
        next_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                boolean check = false;
                for (int i = 0; i < NoOfCountries; i++) {
                    if (check_boxes[i].isVisible() == true) {
                        check = true;
                    }
                }

                if (continents_combo.getSelectedItem() != null) {
                    JOptionPane.showMessageDialog(null, "Assign all Continents");
                } else if (check == true) {
                    JOptionPane.showMessageDialog(null, "Assign all Countries");
                    assign_countries_frame.dispose();
                    GetNumbers();
                } else {
                    assign_countries_frame.dispose();
                    AssignNeighboursFrame();
                }
            }
        });

    }

    /**
     * UI for assigning neighbors to the countries already selected continents
     */
    private void AssignNeighboursFrame() {

        assign_neighbours_frame = new JFrame("Add Neighbours");
        assign_neighbours_panel = new JPanel();
        assign_neighbours_panel.setBounds(440, 80, 200, 200);
        assign_neighbours_panel.setBackground(Color.gray);
        assign_neighbours_panel.setSize(100, 800);
        assign_neighbours_frame.setSize(1000, 1000);

        add_to_country_button = new JButton("Add Countries");
        add_to_country_button.setBounds(575, 300, 150, 30);
        done_button = new JButton("done");
        done_button.setBounds(700, 700, 150, 30);

        countries_combo = new JComboBox();
        countries_combo.setBounds(10, 150, 200, 20);

        assign_neighbours_frame.add(assign_neighbours_panel);
        assign_neighbours_frame.add(done_button);
        assign_neighbours_frame.add(add_to_country_button);
        assign_neighbours_frame.add(countries_combo);

        assign_neighbours_frame.setLayout(null);

        for (int i = 1; i <= NoOfCountries; i++) {
            countries_combo.addItem("Country" + i);
        }
        for (int i = 0, j = 150; i < NoOfCountries; i++, j = j + 30) {
            check_boxes1[i] = new JCheckBox("Country" + (i + 1));
            assign_neighbours_panel.add(check_boxes1[i]);
            check_boxes1[i].setBounds(300, j, 150, 50);
            check_boxes1[i].setVisible(true);
        }
        assign_neighbours_frame.setVisible(true);
        ActionAssignNeighbours();

    }

    /**
     * Method for handling listeners buttons in the "neighbour" choosing phase
     */
    private void ActionAssignNeighbours() {
        add_to_country_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                List<Country> TempCountriesObject2 = new ArrayList<Country>();

                for (int i = 0; i < NoOfCountries; i++) {
                    if (check_boxes1[i].isSelected()) {
                        Country temp = new Country(check_boxes1[i].getText());
                        TempCountriesObject2.add(temp);
                        check_boxes1[i].setSelected(false);
                    }
                }
                if (countries_combo.getSelectedItem() != null) {
                    Country tempct = new Country((String) countries_combo.getSelectedItem());
                    tempct.setNeighbors(TempCountriesObject2);
                    CountriesObjectList.add(tempct);
                    countries_combo.removeItem(countries_combo.getSelectedItem());
                    if (countries_combo.getSelectedItem() == null) {
                        for (int j = 0; j < NoOfCountries; j++) {
                            check_boxes1[j].setVisible(false);
                            assign_neighbours_panel.updateUI();
                        }
                    }
                }
            }
        });

        CreateMap mapper = this;
        done_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (countries_combo.getSelectedItem() != null) {
                    JOptionPane.showMessageDialog(null, "Assign neighbours to all Countries");
                } else {
                    JOptionPane.showMessageDialog(null, "Map successfully Created");
                    PrepareCountryAndContinentData();

                    DisplayDebug(); // uncomment if debugging

                    assign_neighbours_frame.dispose();
                    SelectNoOfPlayers.assignCountries();
                    SaveCreatedMap SaveMapObejct = new SaveCreatedMap(mapper);
                }
            }
        });

    }

    /**
    *  Method for connecting country and continent lists
    */
    private void PrepareCountryAndContinentData() {
        int i = 1;
        for (Country cc : CountriesObjectList) {
            cc.setCountryId(i);
            i++;
        }
        i = 1;
        for (Continent cc : ContinentsObjectList) {
            cc.setContinentId(i);
            i++;
        }
        for (Continent tt : ContinentsObjectList) {
            for (Country cc : tt.getCountries()) {
                for (Country ss : CountriesObjectList) {
                    if (ss.getName().compareTo(cc.getName()) == 0) {
                        cc.setNeighbors(ss.getNeighbors());
                        ss.setContinent(tt);
                    }
                }
            }
        }

    }

    /**
     * Display map information, only for debugging purposes.
     **/
    private void DisplayDebug() {
        for (Continent tt : ContinentsObjectList) {
            System.out.println("Continent:" + tt.getName());
            for (Country cc : tt.getCountries()) {
                System.out.println("Country:" + cc.getName());
                for (Country ss : cc.getNeighbors()) {
                    System.out.println("Neighbour:" + ss.getName());
                }
            }
        }
    }

    public List<Continent> getContinentsObjectList() {
        return ContinentsObjectList;
    }

    public List<Country> getCountriesObjectList() {
        return CountriesObjectList;
    }
}
