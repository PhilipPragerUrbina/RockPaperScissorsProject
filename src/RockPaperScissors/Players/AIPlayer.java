package RockPaperScissors.Players;

import RockPaperScissors.Player;
import RockPaperScissors.RPS;
import basicneuralnetwork.NeuralNetwork;
import basicneuralnetwork.activationfunctions.ActivationFunction;

import java.util.ArrayList;

//https://github.com/kim-marcel/basic_neural_network
//Use this random library to make neural networks for rock paper scissors
//I'm using this NN library because it's exactly what I need and all my own implementations are in c++ and im too lazy to write a java one.
public class AIPlayer implements Player {
    private ArrayList<Integer> last_moves; //store last moves to make predictions and train
    private NeuralNetwork neural_network; //actual network
    private int num_to_save; //how many moves to save for predictions

    //create a player that uses NN to predict opponent moves
    public AIPlayer(int memory, int num_layers, int nodes){
        num_to_save = memory;
        last_moves = new ArrayList<>();
        //inputs: last couple of moves
        //outputs, how confident it is in the next opponents move
        neural_network = new NeuralNetwork(memory, num_layers,nodes,3);
        neural_network.setActivationFunction(ActivationFunction.TANH);

        //pre-train with random data for better starting performance, at least 1/3
        RandomPlayer random_player = new RandomPlayer();
        for (int i = 0; i < 500; i++) {
            saveLastRoundData(0,random_player.getMove(),0); //only opponent move is necessary for training
        }
        //todo adjust learning rate
        //todo try with genetic algorithms
    }

    //default values
    public AIPlayer(int memory) {
        this(memory, 1, (memory + 3) / 2);//take average nodes
    }
    public AIPlayer(int memory, int layers) {
        this(memory, layers, (memory + 3) / 2);//take average nodes
    }

    @Override
    public int getMove() {
        //create input array
        double[] input = new double[last_moves.size()];
        for (int i = 0; i < last_moves.size(); i++) {
            input[i] = last_moves.get(i);
        }
        //make prediction
        double[] move_output = neural_network.guess(input); //index corresponds to move
        //greatest move is chosen
        //parse prediction(get the greatest one)
        int opponent_move_prediction = 0;
        if(move_output[1] > move_output[0] && move_output[1] > move_output[2]){
            opponent_move_prediction = 1;
        }
        if(move_output[2] > move_output[0] && move_output[2] > move_output[1]){
            opponent_move_prediction = 2;
        }
        //return move to beat predicted move
        return getMoveToBeat(opponent_move_prediction);
    }

    //class to store data point for training
    private class DataPoint {
        double[] inputs;
        double[] expected = new double[3];
        void addInputs(ArrayList<Integer> moves){
            inputs = new double[moves.size()-1]; //ignore last since it is the answer
            for (int i = 0; i < moves.size()-1; i++) {
                inputs[i] = (double)moves.get(i)/ 2.0; //convert move to double. 0,1,2 should become 0,1/2,1
            }
        }
        void addExpected(int move){ //convert move to multiple doubles(index corresponds to move
            for (int i = 0; i < 3; i++) {
                expected[i] = 0;
                if(i == move){
                    expected[i] = 1;
                }
            }
        }
        //create data point from list of moves
        DataPoint(ArrayList<Integer> moves){
            addInputs(moves);
            addExpected(moves.get(moves.size()-1)); //last one is the expected value
        }
    }

    //train the model on a data point
    private void train(DataPoint d) {
        //convert to arrays
        double[] inputs = new double[num_to_save];
        double[] outputs = new double[3];
            for (int j = 0; j < 3; j++) {
                outputs[j] = d.expected[j];
            }
            for (int j = 0; j < num_to_save; j++) {
                inputs[j] = d.inputs[j];
            }
        neural_network.train(inputs,outputs); //add to network
    }

    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
       last_moves.add(opponentMove); //keep track of last n moves
       if(last_moves.size() > num_to_save){
           //enough data to add new data point
           DataPoint data_point = new DataPoint(last_moves);
           train(data_point);
           //remove old  data
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
