
package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Player;
import view.MFrame2;
import view.SelectMap;
import view.SelectNoOfPlayers;
import view.SelectPlayerStrategies;
import view.TournamentResults;

/**
 * this class implements the Tournament Mode
 * 
 * @author Bhargav
 * @version 1.0
 */
public class Tournament {
	boolean win = false;
	public static String table = "";
   ArrayList<ArrayList> tablelist= new ArrayList<>();
   ArrayList<String> list= new ArrayList<>();
//   String[][] list=
	public Tournament() {
		function();
		results();
		System.out.println(table);
//		TournamentResults t= new TournamentResults();
	}

	/**
	 * Function implements Rules of Tournament Mode
	 */
	public void function() {
		MFrame2 frame2 = new MFrame2();
		AggressiveStratery ag = new AggressiveStratery();
		CheaterStrategy ch = new CheaterStrategy();
		RandomStrategy rn = new RandomStrategy();
		BenevolentStrategy bn = new BenevolentStrategy();
		ReadingFiles ReadFile = new ReadingFiles(frame2);
		System.out.println("\nNo of Games:" + SelectMap.NoOfGames);
		for (int gameno = 0; gameno < SelectMap.NoOfGames; gameno++) {
			System.out.println("\nGame:" + (gameno + 1));
			list= new ArrayList<>();
	
			for (int mapno = 0; mapno < SelectMap.TourMapList.size(); mapno++) {
				
				// game
				win = false;
				try {
					ReadFile.Reads("Resources/" + SelectMap.TourMapList.get(mapno) + ".map",
							SelectNoOfPlayers.NumberOfPlayers);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("\n\nMap:" + SelectMap.TourMapList.get(mapno) + "\nTotalNo of countries:"
						+ ReadingFiles.CountriesNames.size() + "\nNo of turns:" + SelectMap.NoOfTurns);
				for (int m = 0; m < ReadingFiles.playerId.size(); m++) {
					ReadingFiles.playerId.get(m).setStratergy(SelectPlayerStrategies.getStrategies().get(m));
				}
				for (int turnno = 0; turnno < SelectMap.NoOfTurns; turnno++) {
					System.out.println("\n turn:" + (turnno + 1));

					for (int playerindex = 0; playerindex < ReadingFiles.playerId.size(); playerindex++) {
						Player p;
						p = ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[playerindex]);

						switch (p.getStatergy()) {
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

						if (p.getMyCountries(p).size() == ReadingFiles.CountriesNames.size()) {
							win = true;
							break;
						}
						if (win == true)
							break;

					}

					for (int l = 0; l < ReadingFiles.playerId.size(); l++) {

						Player p = ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[l]);

						System.out.print("\nturn:" + (turnno + 1) + " Player:" + (p.getPlayerId() + 1) + "  "
								+ p.getStatergy() + " total countries:" + p.getMyCountries(p).size());
						if (p.getMyCountries(p).size() == ReadingFiles.CountriesNames.size()) {
							System.out.println(
									"\n****Player " + (p.getPlayerId() + 1) + " " + p.getStatergy() + " wins!");
							list.add(p.getStatergy());
							System.out.println("added"+ p.getStatergy());
							
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
				if (win == false) {
					System.out.print("\n\n****No one wins!");
					list.add("Draw");
					System.out.println("added"+ "Draw");
					
				}
				System.out.println("\nend of game!");
				

			}
			tablelist.add(list);
		}
		System.out.print("\n\n****END OF TOURNAMENT*****");
	}

	public void removeLostPlayer() {
		for (int i = 0; i < ReadingFiles.playerId.size(); i++) {
			Player p = ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[i]);
			if (ReadingFiles.playerId.size() == 1) {
				System.out.println("\n****Player "
						+ (ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[0]).getPlayerId() + 1)
						+ " " + ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[0]).getStatergy()
						+ " wins!");
				list.add(ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[0]).getStatergy());
				System.out.println("added"+ ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[0]).getStatergy());
				ReadingFiles.playerId.remove(ReadingFiles.playerId.keySet().toArray()[0]);
			}
			if (p.getMyCountries(p).size() == 0) {
				System.out.println("player " + p.getStatergy() + " removed");
				ReadingFiles.playerId.remove(ReadingFiles.playerId.keySet().toArray()[i]);
				if (ReadingFiles.playerId.size() == 1) {
					System.out.println("\n****Player "
							+ (ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[0]).getPlayerId() + 1)
							+ " " + ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[0]).getStatergy()
							+ " wins!");
					list.add(ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[0]).getStatergy());
					System.out.println("added"+ ReadingFiles.playerId.get(ReadingFiles.playerId.keySet().toArray()[0]).getStatergy());
					ReadingFiles.playerId.remove(ReadingFiles.playerId.keySet().toArray()[0]);
				}
			}
		}
	}
//	public static void main(String[] args) {
////		SelectMap.NoOfGames
////		SelectMap.TourMapList.size()
//		Tournament t= new Tournament();t.results();
//		System.out.println(t.table);
//	}

	public void results() {
		TournamentResults results=new TournamentResults();
		results.setup(SelectMap.TourMapList.size()+1);
		table+=String.format("%-16s","" );
		results.adding("");
		
		
		
		for (int j = 0; j < SelectMap.TourMapList.size(); j++) {
			table+=String.format("%-16s","|   Map"+(j+1) );
			results.adding("Map "+(j+1));
		}
		table+="\n";
		for (int i = 0; i < SelectMap.NoOfGames; i++) {
			table+=String.format("%-16s","   Game"+(i+1) );
			results.adding("Game"+(i+1));
			for (int j = 0; j < SelectMap.TourMapList.size(); j++) {
				table+=String.format("%-16s","|"+tablelist.get(i).get(j) );
				System.out.println(tablelist.get(i).get(j));
				results.adding(""+tablelist.get(i).get(j));
				
			}
			table+="\n";
		}
		results.show();
		

	}

}
