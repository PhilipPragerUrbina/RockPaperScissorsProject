package RockPaperScissors;

import RockPaperScissors.Players.*;

public class RunMe {
	private static final int TOTAL_GAMES = 100000;

	public static void main(String[] args) {
		RPS game = new RPS(); 							// create the game object

//todo store all human player data into file, for markov and ai training
		//	Player p1 = new SendingNetworkedPlayer(new HumanPlayer(), 5000);
		//	Player p2 = new ReceivingNetworkedPlayer(args[0], 4900);
	Player p1 = new AIPlayer(10,3);
	Player p2 = new AIPlayer(1,1);



		for (int i = 0; i < TOTAL_GAMES; i++) { 	// play many games together
			int p1move = p1.getMove(); 				// get the moves from the players
			int p2move = p2.getMove();

			int winner = game.playRound(p1move, p2move); // play the round

			// Display game stats
			System.out.println("Game " + i + ": P1 (" + game.getP1Percent()
					+ "%): " + RPS.intToString(p1move) + "\tP2("
					+ game.getP2Percent() + "%): " + RPS.intToString(p2move)
					+ "\tWinner: " + RPS.getWinnerString(winner));

			p1.saveLastRoundData(p1move, p2move, RPS.getWinnerFor(1, winner)); // update
																		// the
																		// players
																		// with
																		// the
																		// results
			p2.saveLastRoundData(p2move, p1move, RPS.getWinnerFor(2, winner));
		}

		// Display ending statistics
		System.out.println("Results:\n\tplayer 1 wins: " + game.getP1Percent()
				+ "%\tplayer2 wins: " + game.getP2Percent() + "%\tties: "
				+ game.getTiesPercent() + "%");
	}
}


/*
		//multiplayer demo
		//create player variables
		Player p1;
		Player p2;
		//check arguments
		if(args.length != 3) {
			System.err.println("Not correct arguments");
			return;
		}
		//arguments are: ip_send_to ip_send_from player_a_or_b
		//example with localhost: "127.0.0.1 127.0.0.1 a" and "127.0.0.1 127.0.0.1 b"
		//ports 5000 and 4900 are used
		if(args[2].equals("a")){
			p1 = new SendingNetworkedPlayer(new HumanPlayer(), 5000); //create player a
			p2 = new ReceivingNetworkedPlayer(args[0], 4900);
		}else {
			//switch order for correct connection orders
			p1 = new ReceivingNetworkedPlayer(args[1], 5000); //create player b
			p2 = new SendingNetworkedPlayer(new HumanPlayer(), 4900);
		}

*/
