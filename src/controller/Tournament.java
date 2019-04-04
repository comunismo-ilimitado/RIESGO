
package controller;

import java.io.IOException;

import model.Player;
import view.MFrame2;
import view.SelectMap;
import view.SelectPlayerStrategies;

public class Tournament {

	public Tournament() {
		function();
	}

	public void function() {
		MFrame2 frame2 = new MFrame2();
		AggressiveStratergy ag = new AggressiveStratergy();
		CheaterStrategy ch = new CheaterStrategy();
		RandomStrategy rn = new RandomStrategy();
		BenevolentStrategy bn = new BenevolentStrategy();
		ReadingFiles ReadFile = new ReadingFiles(frame2);
		System.out.println("\nNo of Games:"+ SelectMap.NoOfGames);
		for (int gameno = 0; gameno < SelectMap.NoOfGames; gameno++) {
			System.out.println("\nGame:"+ gameno);
			for (int mapno = 0; mapno < SelectMap.TourMapList.size(); mapno++) {
				// game

				System.out.println("\nMap:"+ SelectMap.TourMapList.get(mapno));
				try {
					ReadFile.Reads("Resources/"+SelectMap.TourMapList.get(mapno)+".map" );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int m = 0; m < ReadingFiles.playerId.size(); m++) {
					ReadingFiles.playerId.get(m).setStratergy(SelectPlayerStrategies.getStrategies().get(m));
				}
				for (int turnno = 0; turnno < SelectMap.NoOfTurns; turnno++) {

					for (int playerindex = 0; playerindex < ReadingFiles.playerId.size(); playerindex++) {
						Player p;
						p = ReadingFiles.playerId.get(playerindex);
						System.out.println("turn:"+playerindex+" Player:"+ p.getPlayerId()+" Strat:"+p.getStatergy() 
						+" total countries:"+ p.getTotalCountries(p));
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

					}//player
				}//turn

			} // game
		}
	}
	
	public void results() {
		
	}

}
