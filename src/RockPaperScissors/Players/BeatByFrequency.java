package RockPaperScissors.Players;

import RockPaperScissors.Player;
import RockPaperScissors.RPS;

public class BeatByFrequency implements Player {
    //initialize probabilities/frequencies to equal values
    private int r_c = 1; //rock count
    private int s_c = 1; //scissors count
    private int p_c =1; //paper count
    private int num_plays = 3; //number of rounds

    @Override
    public int getMove() {
        //get probability of playing different moves
        double r_p = (double)s_c / (double)num_plays; //rock beats scissors
        double s_p = (double)p_c / (double)num_plays; //scissors beats paper
        double p_p = (double)r_c / (double)num_plays; //paper beats rock
        Player random_player = new WeightedRandomPlayer(r_p,p_p); //create random player with probabilities
        return random_player.getMove(); //return correctly weighted random move
    }

    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
        if(opponentMove == RPS.ROCK){
            r_c++;
        }else if(opponentMove == RPS.SCISSORS){
            s_c++;
        }else{
            p_c++;
        }
        num_plays++;
    }
}
