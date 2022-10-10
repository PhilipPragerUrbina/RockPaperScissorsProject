package RockPaperScissors.Players;

import RockPaperScissors.Markov.MarkovModel;
import RockPaperScissors.Player;
import RockPaperScissors.RPS;

import java.util.ArrayList;

//use markov model to make predictions about opponent moves
public class MarkovPlayer implements Player {
    private ArrayList<Integer> last_moves; //store last moves to make predictions
    private MarkovModel model; //the markov model
    private int num_to_save; //how many moves to save for the n order model
    public MarkovPlayer(int order){
        num_to_save = order;
        model = new MarkovModel(3,order);
        last_moves = new ArrayList<>();
    }


    @Override
    public int getMove() {
        if(last_moves.size() < num_to_save){
            return RPS.ROCK; //not enough data return rock
        }
        //create move arrays
        int[] last_moves_array = new int[num_to_save];
        for (int i = 0; i < num_to_save; i++) {
            last_moves_array[i] = last_moves.get(i); //copy over
        }
        //make prediction
        int opponent_move_prediction = model.makePrediction(last_moves_array); //todo add option for most probable move
        //return move to beat predicted move
        return getMoveToBeat(opponent_move_prediction);
    }



    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
       model.addDataPoint(opponentMove); //train
       last_moves.add(opponentMove); //keep track of last n moves
       if(last_moves.size() > num_to_save){
           last_moves.remove(0);
       }
    }


    //get the correct move to beat
    private int getMoveToBeat(int prev) {
        if (prev == RPS.ROCK) return RPS.PAPER;
        if (prev == RPS.SCISSORS) return RPS.ROCK;
        return RPS.SCISSORS;
    }
}
