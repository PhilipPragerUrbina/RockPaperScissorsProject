package RockPaperScissors.Players;

import RockPaperScissors.Player;
import RockPaperScissors.RPS;

import java.util.ArrayList;

public class PlayToBeatInfrequentPlays implements Player {
    //past opponent moves
    ArrayList<Integer> past_moves = new ArrayList<>();
    private int n; //number of moves to keep track of

    public PlayToBeatInfrequentPlays(int memory){
        n = memory;
    }

    @Override
    public int getMove() {
        int least_frequent_move = RPS.SCISSORS; //start with scissors

        //get counts
        int rock_frequency = 0;
        int paper_frequency = 0;
        int scissor_frequency = 0;
        for (Integer move : past_moves) {
            if(move == RPS.ROCK){
                rock_frequency++;
            } else if(move == RPS.PAPER){
                paper_frequency++;
            }else {
                scissor_frequency++;
            }
        }

        if(rock_frequency <= paper_frequency && rock_frequency <= scissor_frequency){
            least_frequent_move = RPS.ROCK; //rock is the least frequent
        }
        if(paper_frequency <= rock_frequency && paper_frequency <= scissor_frequency){
            least_frequent_move = RPS.PAPER; //paper is the least frequent
        }
        //tie or paper is least frequent or not enough data

        return getMoveToBeat(least_frequent_move); //return move to beat the least frequent
    }


    //get the correct move to beat
    private int getMoveToBeat(int prev) {
        if (prev == RPS.ROCK) return RPS.PAPER;
        if (prev == RPS.SCISSORS) return RPS.ROCK;
        return RPS.SCISSORS;
    }

    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
        past_moves.add(opponentMove);
        if(past_moves.size() >= n){
            past_moves.remove(0);
        }
    }
}
