package controller.game;

import controller.editor.ReadingFiles;
import controller.strategies.AggressiveStrategy;
import controller.strategies.BenevolentStrategy;
import controller.strategies.CheaterStrategy;
import controller.strategies.RandomStrategy;
import model.Player;
import view.gameFrames.MFrame2;
import view.menuFrames.SelectMap;
import view.menuFrames.SelectNoOfPlayers;
import view.menuFrames.SelectPlayerStrategies;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this class implements the Tournament Mode
 *
 * @author Bhargav
 * @version 1.0
 */
public class Tournament {
    boolean win = false;
    static String table = "";
    ArrayList<ArrayList> tablelist = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    //Antigua TournamentResults
    private JFrame window;
    private JPanel panel;
    private JLabel HeaderLabel;
    private JTextArea field;


    /**
     * Constructor for Tournament
     */
    public Tournament() {
        function();
        results();
        System.out.println(table);
    }

    /**
     * Function implements Rules of Tournament Mode
     */
    public void function() {
        MFrame2 frame2 = new MFrame2();
        AggressiveStrategy ag = new AggressiveStrategy();
        CheaterStrategy ch = new CheaterStrategy();
        RandomStrategy rn = new RandomStrategy();
        BenevolentStrategy bn = new BenevolentStrategy();
        ReadingFiles ReadFile = new ReadingFiles(frame2);
        System.out.println("\nNo of Games:" + SelectMap.NoOfGames);
        //T odo esto escribe por pantalla como van sucediendo los juegos
        //For para cada juego
        for (int gameno = 0; gameno < SelectMap.NoOfGames; gameno++) {
            System.out.println("\nGame:" + (gameno + 1));
            list = new ArrayList<>();
            //For para cada mapa por juego
            for (int mapno = 0; mapno < SelectMap.TourMapList.size(); mapno++) {
                //Juego
                win = false;
                try {
                    ReadFile.Reads("Resources/" + SelectMap.TourMapList.get(mapno) + ".map",
                            SelectNoOfPlayers.NumberOfPlayers);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("\n\nMap:" + SelectMap.TourMapList.get(mapno) + "\nTotal No of countries:"
                        + ReadingFiles.getCountriesNames().size() + "\nNo of turns:" + SelectMap.NoOfTurns);
                for (int m = 0; m < ReadingFiles.getPlayerId().size(); m++) {
                    ReadingFiles.getPlayerId().get(m).setStrategy(SelectPlayerStrategies.getStrategies().get(m));
                }
                //For para cada turno (dentro de un mapa dentro de un juego)
                for (int turnno = 0; turnno < SelectMap.NoOfTurns; turnno++) {
                    System.out.println("\n turn:" + (turnno + 1));
                    for (int playerindex = 0; playerindex < ReadingFiles.getPlayerId().size(); playerindex++) {
                        Player p;
                        p = ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[playerindex]);
                        switch (p.getStrategyType()) {
                            case "Agressive":
                                ag.reinforce(p);
                                ag.attack(p);
                                ag.fortify(p);
                                removeLostPlayer();
                                break;
                            case "Benevolent":
                                bn.reinforce(p);
                                bn.attack(p);
                                bn.fortify(p);
                                removeLostPlayer();
                                break;
                            case "Random":
                                rn.reinforce(p);
                                rn.attack(p);
                                rn.fortify(p);
                                removeLostPlayer();
                                break;
                            case "Cheater":
                                ch.reinforce(p);
                                ch.attack(p);
                                ch.fortify(p);
                                removeLostPlayer();
                                break;
                            default:
                                break;
                        }
                        removeLostPlayer();
                        //Nota: Es al empezar el turno cuando se mira si alguien ha ganado.
                        //removeLostPlayer() va quitando jugadores, y win==true cuando todas las countries son del ganador.
                        if (p.getMyCountries(p).size() == ReadingFiles.getCountriesNames().size()) {
                            win = true;
                            break;
                        }
                        if (win == true)
                            break;
                    }
                    //For de cada jugador en cada turno
                    for (int l = 0; l < ReadingFiles.getPlayerId().size(); l++) {
                        Player p = ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[l]);
                        System.out.print("\nturn:" + (turnno + 1) + " Player:" + (p.getPlayerId() + 1) + "  "
                                + p.getStrategy() + " total countries:" + p.getMyCountries(p).size());
                        //Se anuncia victoria
                        if (p.getMyCountries(p).size() == ReadingFiles.getCountriesNames().size()) {
                            System.out.println("\n****Player " + (p.getPlayerId() + 1) + " " + p.getStrategy() + " wins!");
                            //Se añade a list
                            list.add(p.getStrategyType());
                            System.out.println("added" + p.getStrategy());
                            win = true;
                            break;
                        }
                        if (p.getMyCountries(p).size() == 0) {
                            removeLostPlayer();
                            System.out.print("(Player:" + (p.getPlayerId() + 1) + "Lost!)");
                        }
                    }
                    if (win == true)
                        break;

                }
                //Caso empate
                if (win == false) {
                    System.out.print("\n\n****No one wins!");
                    list.add("Draw");
                    System.out.println("added" + "Draw");
                }
                System.out.println("\nend of game!");
                //Siguiente mapa
            }
            //Por cada juego se añade la lista de ganadores de los distintos mapas (de ese juego) a tablelist.
            tablelist.add(list);
        }
        System.out.print("\n\n****END OF TOURNAMENT*****");
    }

    /**
     * This function is used to remove eliminated players from the game or announce winners.
     */
    public void removeLostPlayer() {
        //Se mira si ha ganado o debe ser eliminado para cada jugador.
        for (int i = 0; i < ReadingFiles.getPlayerId().size(); i++) {
            Player p = ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[i]);
            //Caso ganador.
            if (ReadingFiles.getPlayerId().size() == 1) {
                System.out.println("\n****Player "
                        + (ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[0]).getPlayerId() + 1)
                        + " " + ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[0]).getStrategy()
                        + " wins!");
                list.add(ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[0]).getStrategyType());
                System.out.println("added" + ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[0]).getStrategy());
                ReadingFiles.getPlayerId().remove(ReadingFiles.getPlayerId().keySet().toArray()[0]);
            }
            //Te quedas sin países, eres eliminado.
            if (p.getMyCountries(p).size() == 0) {
                System.out.println("player " + p.getStrategy() + " removed");
                ReadingFiles.getPlayerId().remove(ReadingFiles.getPlayerId().keySet().toArray()[i]);
                if (ReadingFiles.getPlayerId().size() == 1) {
                    System.out.println("\n****Player "
                            + (ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[0]).getPlayerId() + 1)
                            + " " + ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[0]).getStrategy()
                            + " wins!");
                    //Se añade a la lista de ganadores
                    list.add(ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[0]).getStrategyType());
                    System.out.println("added" + ReadingFiles.getPlayerId().get(ReadingFiles.getPlayerId().keySet().toArray()[0]).getStrategy());
                    ReadingFiles.getPlayerId().remove(ReadingFiles.getPlayerId().keySet().toArray()[0]);
                }
            }
        }
    }

    /**
     * This method prints and shows the result's table of the tournament
     */
    public void results() {
        window = new JFrame("Tournament Results");
        window.setSize(500, 700);
        panel = new JPanel(new GridLayout(0, SelectMap.TourMapList.size()+1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        HeaderLabel = new JLabel("Results");
        HeaderLabel.setBounds(120, 100, 150, 50);
        HeaderLabel.setVisible(true);
        table += String.format("%-16s", "");
        JLabel jLabel1 = new JLabel("");
        jLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(jLabel1);
        for (int j = 0; j < SelectMap.TourMapList.size(); j++) {
            table += String.format("%-16s", "|   Map" + (j + 1));
            JLabel jLabel2 = new JLabel("Map " + (j + 1));
            jLabel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(jLabel2);
        }
        table += "\n";
        for (int i = 0; i < SelectMap.NoOfGames; i++) {
            table += String.format("%-16s", "   Game" + (i + 1));
            JLabel jLabel3 = new JLabel("Game"+(i+1));
            jLabel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(jLabel3);
            //For para escribir por pantalla los resultados
            for (int j = 0; j < SelectMap.TourMapList.size(); j++) {
                table += String.format("%-16s", "|" + tablelist.get(i).get(j));
                System.out.println(tablelist.get(i).get(j));
                JLabel jLabel4 = new JLabel("" + tablelist.get(i).get(j));
                jLabel4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel.add(jLabel4);
            }
            table += "\n";
        }
        //Escribe en interfaz (tabla) los resultados
        window.add(panel);
        window.setVisible(true);
    }

}
