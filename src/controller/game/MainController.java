package controller.game;

import controller.controllers.AttackController;
import controller.controllers.BoardFacade;
import controller.controllers.FortificationController;
import controller.controllers.ReinforcementController;
import controller.editor.MapValidation;
import controller.editor.ReadingFiles;
import controller.net.Board;
import model.*;
import view.gameFrames.BoardController;
import view.gameFrames.GameUIController;
import view.gameFrames.MFrame;
import view.menuFrames.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import model.CardTypes;
import model.Country;


/**
 * start up phase of the game
 *
 * @author pazim
 * @version 1.1
 */
public class MainController extends Observable{
    ReadingFiles files;
    MFrame frame;
    BoardController boardController;
    String phase;
    MyActionListener myactionlistner;
    AttackController attackController;
    ReinforcementController reinforcementController;
    public FortificationController fortificationController;
    MapValidation mapValidation;
    private boolean resume = false;
    private List<String> phaseList;
    private String currentPhase;
    private int currentPlayer = 0;
    private Country attackCountry1;
    private Country attackCountry2;
    private Country fortifyCountry1, fortifyCountry2;
    List<CardTypes> cardTypesList = new ArrayList<>();
    private volatile Board board;
    private BoardFacade boardFacade;



    /**
     *
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void Function() throws Exception {
        try {

            board = new Board();
            boardFacade = new BoardFacade(this);

            boardController = new GameUIController();
            files = new ReadingFiles(boardController);
            //Resume Game
            FileReader fileReader;
            BufferedReader bufferedReader = null;
            File tempFile = new File("Resources/SaveGame.txt");
            boolean exists = tempFile.exists();
            if (exists) {
                fileReader = new FileReader("Resources/SaveGame.txt");
                bufferedReader = new BufferedReader(fileReader);
            }
            if (isResume()) {
                files.Reads(bufferedReader.readLine(), Integer.parseInt(bufferedReader.readLine()));
            } else { //Ruta de guardado de la partida (al crear una nueva)
                String address = "Resources/World.map";
                if (SelectMapType.MapType == 1)
                    address = "Resources/" + SelectMap.getSingleModeSelectedMap() + ".map";
                else if (SelectMapType.MapType == 2)
                    address = "Resources/LoadedMap.map";
                else if (SelectMapType.MapType == 3)
                    address = "Resources/UserMap.map";
                if (SelectMapType.MapType < 4) {
                    files.Reads(address, SelectNoOfPlayers.NumberOfPlayers);
                }
            }
            //Comprobación mapa
            mapValidation = new MapValidation(ReadingFiles.getCountryNameObject(), ReadingFiles.getContinentNameObject());
            mapValidation.CallAllMethods();
            if (!files.isErrors() && !mapValidation.isError()) { //Booleanos que dicen si ha habido un error
                myactionlistner = new MyActionListener(this);
                frame = new MFrame(myactionlistner, ReadingFiles.getImage());
                reinforcementController = new ReinforcementController();
                attackController = new AttackController();
                fortificationController = new FortificationController();
                frame.noArmiesLeft = playerObjet(0).getPlayerArmiesNotDeployed();
                addObserver(frame);
                if (isResume()) {
                    int no = Integer.parseInt(bufferedReader.readLine());
                    setCurrentPlayer(no);
                    phase = bufferedReader.readLine();
                }
                //Se muestra la interfaz de juego
                frame.fun();
                if (isResume())
                    LoadSavedGame(bufferedReader);
                //Cargar estrategias (Con playerId2) (es un clon de playerId)
                for (int i = 0; i < ReadingFiles.getPlayerId2().size(); i++) {
                    ReadingFiles.getPlayerId2().get(i).setStrategy(SelectPlayerStrategies.getStrategies().get(i));
                }
                //Se crean los botones
                SetButtons();
                PaintCountries();
                SetDominationView();
                //Se resume la fase del juego cargado o
                // se empieza por la fase de fortificación en un juego nuevo.
                if (isResume()) {
                    phaseResume(phase);
                } else {
                    selectTypeOfPlayer();
                }
                repaintAndRevalidate();
            }
        } catch (Exception e) {
            System.out.println("ERROR IN MAP Reading. Cant Use This Map File. Please Restart \n" + e);
            e.printStackTrace();
            boardController.boardError("ERROR IN MAP Reading. Cant Use This Map File. Please Restart \n" + e);
        }
    }

    /**
     * Set domination View
     */
    public void SetDominationView() {
        frame.SetDominationView(ReadingFiles.getPlayers().size());
        updateDominationView();
    }

    /**
     * this method loads the saved game
     *
     * @param bufferedReader
     */
    public void LoadSavedGame(BufferedReader bufferedReader) {
        try {
            String temp = "";
            String Everything = "";
            while ((temp = bufferedReader.readLine()) != null) {
                Everything = Everything + temp + "\n";
            }
            ReadingFiles.getPlayerId2().clear();
            SelectPlayerStrategies.strategy_selected.clear();
            String[] PlayersLis = Everything.trim().split("----PLAYER----");
            frame.area.setText(PlayersLis[0]);
            for (int i = 1; i < PlayersLis.length; i++) {
                String[] cards = PlayersLis[i].trim().split("----CARDS----");
                String[] countryandarmies = cards[0].trim().split("\n");
                Player tempPlayer = ReadingFiles.getPlayerId().get(Integer.parseInt(countryandarmies[0]));
                ReadingFiles.getPlayerId2().put(Integer.parseInt(countryandarmies[0]), tempPlayer);
                tempPlayer.setStrategy(countryandarmies[1]);
                SelectPlayerStrategies.strategy_selected.add(countryandarmies[1]);
                System.out.println(tempPlayer);
                tempPlayer.ClearArmies();
                for (int j = 2; j < countryandarmies.length; j++) {
                    String[] country = countryandarmies[j].trim().split("\\*\\*\\*");
                    Country tempCountry = ReadingFiles.getCountryNameObject().get(country[0].trim());
                    tempPlayer.addCountriesOccupied(tempCountry);
                    tempCountry.setNoOfArmies(Integer.parseInt(country[1]));
                    tempCountry.setPlayer(tempPlayer);
                }
                String[] arrlis = cards[1].substring(2, cards[1].length() - 1).split(",");
                ArrayList<CardTypes> arrayList = new ArrayList<>();
                for (int k = 0; k < arrlis.length; k++) {
                    if (arrlis[k].trim().equals(""))
                        break;
                    arrayList.add(CardTypes.valueOf(arrlis[k].trim()));
                }
                // player.setPlayerCards(arrayList);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Update changes in the domination view
     */
    public void updateDominationView() {
        frame.UpdateGameDominationViewPercentage(CountriesPercentage());
        frame.UpdateGameDominationViewContinentOccupied(ContinentsOccupied());
    }

    /**
     * PENDIENTE
     * @param armies
     */
    public void AddArmies(int armies) {
        OnlyNeeded(neighbours(armies));
    }

    /**
     * Set Button of country object
     *
     * @throws IOException
     */
    public void SetButtons() throws IOException {
        frame.SetButtons(countryObjects());
    }

    /**
     * Refresh
     *
     * @throws IOException
     */
    public void RefreshButtons() throws IOException {
        frame.Refresh(countryObjects());
        PaintCountries();
        repaintAndRevalidate();
    }

    /**
     * List of country names
     *
     * @return
     */
    public List<String> countriesNames() {
        return ReadingFiles.getCountriesNames();
    }

    /**
     * Calls repaint and re-validate
     */
    public void repaintAndRevalidate() {
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Total countries occupied by a player
     * @param id
     * @return
     */
    public List<Country> neighbours(Integer id) {
        return ReadingFiles.getPlayerId().get(id).getTotalCountriesOccupied();
    }

    /**
     * Returns country object
     * @return HashMap
     */
    public HashMap<String, Country> countryObjects() {
        return ReadingFiles.getCountryNameObject();
    }

    /**
     * Give color to all countries
     */
    public void PaintCountries() {
        frame.SetColorToAll(countryObjects());
    }

    /**
     * Frame for countries that are to be viewed
     * @param country
     */
    public void OnlyNeeded(List<Country> country) {
        frame.OnlyNeeded(country);
    }

    /**
     * Player Number
     * @return
     */
    public int PlayerNo() {
        return ReadingFiles.getPlayerId().size();
    }

    public int PlayerNo2() {
        return ReadingFiles.getPlayerId2().size();
    }

    /**
     * Returns player object
     * @param id: id of the player
     * @return
     */
    public Player playerObjet(int id) {
        return ReadingFiles.getPlayerId().get(id);
    }

    /**
     * Changes player of the country
     * @param countryname: name of the country
     * @throws IOException
     */
    public void ChangePlayerCountry(String countryname) throws IOException {
        Country country = countryObjects().get(countryname);
        country.setPlayer(ReadingFiles.getPlayerId().get(0));
        RefreshButtons();

    }

    /**
     * Calculates Size of each country button
     * @param pl
     * @return value in float
     */
    public float calculations(Player pl) {
        try {
            float total = ReadingFiles.getCountryNameObject().size();
            float player_have = pl.getTotalCountriesOccupied().size();
            return (player_have / total) * 100;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Giving values to each country button
     * @return
     */
    public ArrayList<Float> CountriesPercentage() {
        ArrayList<Float> arrayList = new ArrayList<>();
        for (int i = 0; i < PlayerNo(); i++) {
            arrayList.add(calculations(playerObjet(i)));
        }
        return arrayList;
    }

    //Ahora hay dos métodos para pasar la información de una partida al archivo de guardado de mapa.

    /**
     * Converts user input into map file
     * @param list: list of continents
     * @return occu: String of continents
     */
    public String ListToStringContinent(List<Continent> list) {
        String occu = "";
        try {
            for (int i = 0; i < list.size(); i++) {
                occu = occu + ", " + list.get(i).getName();
            }
            if (occu == "") {
                occu = "No Continents";
            }
            return occu;
        } catch (Exception e) {
            e.printStackTrace();
            return "None";
        }
    }

    /**
     * Converts user input into map file
     *
     * @param list: list of countries
     * @return occu: String of countries
     */
    public String ListToStringCountries(List<Country> list) {
        String occu = "";
        try {
            for (int i = 0; i < list.size(); i++) {
                occu = occu + ", " + list.get(i).getName();
            }
            return occu;
        } catch (Exception e) {
            e.printStackTrace();
            return "NONE";
        }
    }

    /**
     * Returns list of countries that are occupied
     * @return list
     */
    public ArrayList<String> ContinentsOccupied() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < PlayerNo(); i++) {
            arrayList.add(ListToStringContinent(playerObjet(i).playerOwnsContinent()));
        }
        return arrayList;
    }


    /**
     * Set the player cards to a player (not used)
     */
    public void temp() {
        List<CardTypes> cardTypes = new ArrayList<>();
        playerObjet(0).setPlayerCards(cardTypes);
    }

    //Métodos de myActionListener

    /**
     * This method changes the turn of the players
     *
     */
    void playerUpdate() {
        try {
            if (PlayerNo2() > 1) { //Si el numero de jugadores es mayor que 1 se sigue jugando
                ArrayList<Integer> arrayList = new ArrayList<>(ReadingFiles.getPlayerId2().keySet());
                int index = arrayList.indexOf(getCurrentPlayer());
                index = index + 1;
                if (index >= arrayList.size()) {
                    setCurrentPlayer(ReadingFiles.getPlayerId2().get(arrayList.get(0)).getPlayerId()); //Le vuelve a tocar al primero
                } else {
                    setCurrentPlayer(ReadingFiles.getPlayerId2().get(arrayList.get(index)).getPlayerId());
                }
            } else { //Si solo queda un jugador ha ganado ese jugador
                frame.error("Player :- " + ((int) ReadingFiles.getPlayerId2().keySet().toArray()[0] + 1) + " Wins");
                frame.dispose();
                System.exit(0);

            }
        } catch (Exception e) {
            playerUpdate();
        }
    }


    /**
     * This method refresh the armies that are not deployed and the armies in a country
     *
     * @param country          : object
     */
    void armiesNotDeployed(Country country ) {
        cardTypesList.clear();
        frame.jLabeCardl.setText(cardTypesList.toString());
        String message = getReinforcementController().addArmies(country);
        frame.noArmiesLeft = playerObjet(getCurrentPlayer()).getPlayerArmiesNotDeployed();
        changed();
        if (!message.equals(""))
            frame.error(message);
    }

    /**
     * This method switches between phases when the user loads a game
     *
     * @param phase
     */
    public void phaseResume(String phase) {
        if (phase.equals("Finish Reinforcement")) {
            selectTypeOfPlayer();
        } else if (phase.equals("Finish Attack")) {
            frame.ActivateAll();
            OnlyNeeded(playerObjet(getCurrentPlayer()).getTotalCountriesOccupied());
            finishReinforcement();
        } else if (phase.equals("Finish Fortification")) {
            frame.ActivateAll();
            OnlyNeeded(playerObjet(getCurrentPlayer()).getTotalCountriesOccupied());
            finishattack();
        }
    }

    /**
     * This method checks if someone doesnt have any country occupied and give his cards to the attacker player
     *
     * @param attacker
     */
    void elimination(Player attacker) {
        ArrayList<Integer> arrayList = new ArrayList<>(ReadingFiles.getPlayerId2().keySet());
        for (int i = 0; i < arrayList.size(); i++) {
            Player temp = ReadingFiles.getPlayerId2().get(arrayList.get(i));
            if (temp.getTotalCountriesOccupied().size() == 0) {
                List<CardTypes> defcards = temp.getPlayerCards(); //Lista de cartas del jugador eliminado
                List<CardTypes> attcards = attacker.getPlayerCards(); //Lista de cartas del jugador que elimina
                attcards.addAll(defcards);  //Se juntan ambas listas
                attacker.setPlayerCards(attcards);  //Esta linea y la siguiente hacen lo mismo???
                ReadingFiles.getPlayerId().get(attacker.getPlayerId()).setPlayerCards(attcards);
                ReadingFiles.getPlayerId2().remove(temp.getPlayerId());
                ReadingFiles.getPlayers().remove((Integer) temp.getPlayerId());
            }

        }
        if (ReadingFiles.getPlayerId2().size() <= 1) { //Si despues de la eliminacion solo queda un jugador ha ganado
            frame.error("Player :- " + ((int) ReadingFiles.getPlayerId2().keySet().toArray()[0] + 1) + " Wins");
            frame.dispose();
            System.exit(0);

        }
    }

    /**
     * This method checks if the player is human or not and the difficulty of the CPU
     *
     */
    public void selectTypeOfPlayer() {
        try {
            RefreshButtons();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Player player = playerObjet(getCurrentPlayer());
        System.out.println("---------------------------------");
        System.out.println("Current Player " + (getCurrentPlayer() + 1));
        textarea("---------------------------------");
        textarea("Player Playing :- " + (currentPlayer + 1));
        String strategy = player.getStrategyType().trim(); //Elimina los caracteres blancos iniciales y finales
        if (strategy.equals("Human")) {
            reinforcementController.reinforcementPhase(this);
        } else {
            textarea("Currently in Reinforcement Mode for " + player.getStrategyType() + " player");
            player.getStrategy().reinforce(player);
            textarea("Currently in Attack Mode for " + player.getStrategyType() + " player");
            player.getStrategy().attack(player);
            System.out.println("Attack Finished");
            elimination(player);
            textarea("Currently in Fortification Mode for " + player.getStrategyType() + " player");
            player.getStrategy().fortify(player);
            finishCPU();
        }
    }

    /**
     * This method prepares the game for attack phase
     *
     */
    void finishReinforcement() {
        cardTypesList.clear();
        buttonCards(false);
        setCurrentPhase("Finish Attack");
        frame.nextAction.setText("Finish Attack");
        changed();
        setAttackCountry1(null);
        attackCountry2 = null;
        cardTypesList.clear();
        frame.jLabeCardl.setText(cardTypesList.toString());
        cardTypesList.clear();
        frame.jLabeCardl.setText(cardTypesList.toString());

    }

    /**
     * This method prepares the game for fortification phase
     *
     */
    public void finishattack() {
        buttonCards(false);
        changed();
        setCurrentPhase("Finish Fortification");
        frame.nextAction.setText("Finish Fortification");
        fortifyCountry1 = null;
        fortifyCountry2 = null;
        cardTypesList.clear();
        frame.jLabeCardl.setText(cardTypesList.toString());
        //fortificationPhase();
        textarea("Currently in Fortification Mode");
        AttackController.setCard(false);
        changed();
        frame.ActivateAll();
        OnlyNeeded(playerObjet(getCurrentPlayer()).getTotalCountriesOccupied());
        //playerUpdate(); // El jugador actual se cambia antes de fortificar??
        cardTypesList.clear();
        frame.jLabeCardl.setText(cardTypesList.toString());

    }

    /**
     * This method prepares the game for the next player (Human mode)
     *
     */
    void finishFortification() {
        buttonCards(true);
        changed();
        setCurrentPhase("Finish Reinforcement");
        frame.nextAction.setText("Finish Reinforcement");
        cardTypesList.clear();
        frame.jLabeCardl.setText(cardTypesList.toString());
        playerUpdate();
        selectTypeOfPlayer();
        cardTypesList.clear();
        frame.jLabeCardl.setText(cardTypesList.toString());
    }

    /**
     * This method prepares the game for the next player (CPU mode)
     *
     */
    private void finishCPU() {
        playerUpdate();
        changed();
        selectTypeOfPlayer();
    }

    /**
     * This method enables or disables some buttons
     *
     * @param bool
     */
    public void buttonCards(boolean bool) {
        frame.buttonCard4.setEnabled(bool);
        frame.buttonCard3.setEnabled(bool);
        frame.buttonCard2.setEnabled(bool);
        frame.buttonCard1.setEnabled(bool);
    }

    /**
     * @return Number of infantry cards of the current player
     */
    public String getInfantryCards() {
        int cont = 0;
        List<CardTypes> type = playerObjet(getCurrentPlayer()).getPlayerCards();
        for (int i = 0; i < type.size(); i++) {
            int x = type.get(i).compareTo(CardTypes.Infantry);
            if (x == 0) {
                cont += 1;
            }

        }

        return "" + cont;
    }

    /**
     * @return Number of cavalry cards of the current player
     */
    public String getCavalryCards() {
        int cont = 0;
        List<CardTypes> type = playerObjet(getCurrentPlayer()).getPlayerCards();
        for (int i = 0; i < type.size(); i++) {
            int x = type.get(i).compareTo(CardTypes.Cavalry);
            if (x == 0) {
                cont += 1;
            }

        }

        return "" + cont;
    }

    /**
     * @return Number of artillery cards of the current player
     */
    public String getArtilleryCards() {
        int cont = 0;
        List<CardTypes> type = playerObjet(getCurrentPlayer()).getPlayerCards();
        for (int i = 0; i < type.size(); i++) {
            int x = type.get(i).compareTo(CardTypes.Artillery);
            if (x == 0) {
                cont += 1;
            }
        }
        return "" + cont;
    }

    //Metodos de controller para MFrame ???
    public ArrayList<Float> countriesPercentage() {
        return CountriesPercentage();
    }

    public ArrayList<String> continentsOccupied() {
        return ContinentsOccupied();
    }

    public int getArmiesPerPlayer() {
        return attackController.getTotalCountries(playerObjet(getCurrentPlayer()));
    }

    /**
     * This method saves the game in SaveGame.txt
     *
     */
    public void saveGameOnExit() {
        try {
            File file = new File("Resources/SaveGame.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(ReadingFiles.getAddress() + "\n");
            writer.write(ReadingFiles.getPlayerId().size() + "\n");
            writer.write(getCurrentPlayer() + "\n");
            writer.write(getCurrentPhase() + "\n");
            writer.write(frame.area.getText());
            for (int i = 0; i < PlayerNo2(); i++) {
                Player tempPlayer = ReadingFiles.getPlayerId2().get(ReadingFiles.getPlayerId2().keySet().toArray()[i]);
                writer.write("----PLAYER----\n");
                System.out.println("Error Report :-" + tempPlayer + "---" + PlayerNo2());
                writer.write(tempPlayer.getPlayerId() + "\n");
                System.out.println();
                writer.write(tempPlayer.getStrategy() + "\n");
                for (int j = 0; j < tempPlayer.getTotalCountriesOccupied().size(); j++) {
                    Country tempCountry = tempPlayer.getTotalCountriesOccupied().get(j);
                    writer.write(tempCountry.getName() + "***" + tempCountry.getNoOfArmies() + "\n");
                }
                writer.write("----CARDS----\n");
                writer.write(tempPlayer.getPlayerCards().toString() + "\n");
            }
            writer.close();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method writes on the graphic interface
     *
     * @param string
     */
    public void textarea(String string) {
        frame.area.append("\n" + string);
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    //Getters and setters
    public AttackController getAttackController() {
        return attackController;
    }
    public ReinforcementController getReinforcementController() {
        return reinforcementController;
    }
    public FortificationController getFortificationController() {
        return fortificationController;
    }
    public boolean isResume() {
        return resume;
    }
    public void setResume(boolean resume) {
        this.resume = resume;
    }
    public List<String> getPhaseList() {
        return phaseList;
    }
    public void setPhaseList(List<String> phaseList) {
        this.phaseList = phaseList;
    }
    public String getCurrentPhase() {
        return currentPhase;
    }
    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public Country getAttackCountry1() {
        return attackCountry1;
    }
    public void setAttackCountry1(Country attackCountry1) {
        this.attackCountry1 = attackCountry1;
    }
    public void setAttackCountry2(Country attackCountry2) {
        this.attackCountry2 = attackCountry2;
    }
    public Country getAttackCountry2() {
        return attackCountry2;
    }
    public MFrame getFrame() {
        return frame;
    }
    public List<CardTypes> getCardTypesList() {
        return cardTypesList;
    }
    public void setFortifyCountry1(Country fortifyCountry1) {
        this.fortifyCountry1 = fortifyCountry1;
    }
    public Country getFortifyCountry1() {
        return fortifyCountry1;
    }
    public Country getFortifyCountry2() {
        return fortifyCountry2;
    }
    public void setFortifyCountry2(Country fortifyCountry2) {
        this.fortifyCountry2 = fortifyCountry2;
    }

    public BoardFacade getBoardFacade() {
        return boardFacade;
    }

    public Board getBoard() {
        return board;
    }

    /*
    /**
     * This method
     *
    private void fortificationPhase() {
        textarea("Currently in Fortification Mode");
        AttackController.card = false;
        changed();
        controller.frame.ActivateAll();
        controller.OnlyNeeded(controller.playerObjet(currentPlayer).getTotalCountriesOccupied());
        playerUpdate(); // El jugador actual se cambia antes de fortificar??
    }
*/
}