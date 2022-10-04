package RockPaperScissors.Players;

import RockPaperScissors.Player;
import RockPaperScissors.RPS;

//beat the last winning move
public class BeatLastWinningMovePlayer implements Player {
    int last_winning_move = RPS.ROCK; //start with rock

    @Override
    public int getMove() {
        return getMoveToBeat(last_winning_move);
    } //beat the last move

    //get the correct move to beat
    private int getMoveToBeat(int prev) {
        if (prev == RPS.ROCK) return RPS.PAPER;
        if (prev == RPS.SCISSORS) return RPS.ROCK;
        return RPS.SCISSORS;
    }

    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
        //get last winning move
        if(outcome == RPS.YOU){
            last_winning_move = yourMove;
        }else{
            last_winning_move = opponentMove; //opponent won or tie
        }
    }
}
