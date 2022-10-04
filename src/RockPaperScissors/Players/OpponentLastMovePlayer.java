package RockPaperScissors.Players;

import RockPaperScissors.Player;
import RockPaperScissors.RPS;

//play opponents last move
public class OpponentLastMovePlayer implements Player {
    int last_move = RPS.ROCK; //start with rock

    @Override
    public int getMove() {
        return last_move;
    } //play last move

    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
        last_move = opponentMove;
    }//get opponents last move
}
