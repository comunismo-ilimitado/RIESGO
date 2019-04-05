
package controller;

import java.io.IOException;

import model.Player;
import view.MFrame2;
import view.SelectMap;
import view.SelectNoOfPlayers;
import view.SelectPlayerStrategies;

public class Tournament {
	boolean win = false;

	public Tournament() {
		function();
	}

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

			for (int mapno = 0; mapno < SelectMap.TourMapList.size(); mapno++) {
				// game
				win = false;
				try {
					ReadFile.Reads("Resources/"+SelectMap.TourMapList.get(mapno)+".map",SelectNoOfPlayers.NumberOfPlayers );
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
					System.out.print("\n");

					for (int playerindex = 0; playerindex < ReadingFiles.playerId.size(); playerindex++) {
						Player p;
						p = ReadingFiles.playerId.get(playerindex);

						switch (p.getStatergy()) {
						case "Agressive":
							ag.reinforce(p);
							ag.attack(p);
							ag.fortify(p);
							break;
						case "Benevolent":
							bn.reinforce(p);
							bn.attack(p);
							bn.fortify(p);
							break;
						case "Random":
							rn.reinforce(p);
							rn.attack(p);
							rn.fortify(p);
							break;
						case "Cheater":
							ch.reinforce(p);
							ch.attack(p);
							ch.fortify(p);
							break;
						default:
							break;
						}

						if (p.getMyCountries(p).size() == ReadingFiles.CountriesNames.size()) {
							win = true;
							break;
						}
						if (win == true)
							break;

					} // player
					for (int l = 0; l < ReadingFiles.playerId.size(); l++) {
						Player p = ReadingFiles.playerId.get(l);
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}

						System.out.print("\nturn:" + (turnno + 1) + " Player:" + (p.getPlayerId() + 1) + "  "
								+ p.getStatergy() + " total countries:" + p.getMyCountries(p).size());
						if (p.getMyCountries(p).size() == ReadingFiles.CountriesNames.size()) {
							System.out.println("\n****Player " + (p.getPlayerId() + 1)+" "+ p.getStatergy() + " wins!");
//							System.exit(0);
							win = true;
							break;
						}
						if (p.getMyCountries(p).size() ==0) {
//							ReadingFiles.playerId.remove(p);
						System.out.print("(Player:" + (p.getPlayerId() + 1)+"Lost!)");
						}

					}
					if (win == true)
						break;

				} // turn
				if (win == false)
					System.out.print("\n\n****No one wins!");

			} // map
		} // game
		System.out.print("\n\n****END OF TOURNAMENT*****");
	}

	public void results() {

	}

}
