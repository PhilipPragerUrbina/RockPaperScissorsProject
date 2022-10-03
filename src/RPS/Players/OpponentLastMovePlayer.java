package RPS.Players;

import RPS.Player;
import RPS.RPS;

public class OpponentLastMovePlayer implements Player {
    int last_move = RPS.ROCK;

    @Override
    public int getMove() {
        return last_move;
    }

    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
        last_move = opponentMove;
    }
}
