package RPS;

import RPS.Players.BeatLastMovePlayer;
import RPS.Players.HumanPlayer;
import RPS.Players.OpponentLastMovePlayer;
import RPS.Players.WeightedRandomPlayer;

public class RunMe {
	private static final int TOTAL_GAMES = 1000;

	public static void main(String[] args) {
		RPS game = new RPS(); 							// create the game object

		Player p1 = new HumanPlayer(); 	// create two players
		Player p2 = new OpponentLastMovePlayer();

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
