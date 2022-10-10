package RockPaperScissors.Markov;

import java.util.ArrayList;

// a general class for markov models
public class MarkovModel {
    private int num_states;
    private int orders;
    int[] state_matrix; //matrix(Actually a TENSOR if more than 2d)
    ArrayList<Integer> prev_states;
    int num_data = 0;//how much data has been collected
    public MarkovModel(int num_states, int orders){
        prev_states = new ArrayList<>(); //store the last couple states
        this.num_states = num_states; //number of states
        this.orders = orders+1; //number of orders, hwo far back it looks
        state_matrix = new int[(int)Math.pow(num_states,this.orders)]; //create 1d matrix to store Nd matrix
    }

    //add multiple training data points
    public void addDataPoints(int[] states){
        for (int state : states) {
            addDataPoint(state);
        }
    }

    //add a state to "train"(the lowest state is 0)
    public void addDataPoint(int state){
        if(state >= num_states || state < 0){return;}//not in range
        if(prev_states.size() == orders-1){   //enough data
            int[] coordinate = new int[orders]; //create coordinate
            for (int i = 0; i < orders-1; i++) {
                coordinate[i] = prev_states.get(i);
            }
            coordinate[coordinate.length-1] = state;
            state_matrix[getIndex(coordinate)]++; //update matrix count
        }

        //add to last states
        prev_states.add(state);
        //keep prev states correct size
        if(prev_states.size() == orders){
            prev_states.remove(0);
        }
    }
    //make a prediction based on past data and probability, returns -1 if not right amount of data
    public int makePrediction(int[] states){
        if(states.length != orders-1){return -1;}//not right length
        //get counts
        int[] counts = new int[num_states];   //index corresponds to states
        int total_count = getTotalCount(states, counts); //get total count of occurrences for this combination
        //get random number
        double random_number = Math.random();
        //check probabilities
        double probability_so_far = 0; //until what probability have we checked so far
        for (int i = 0; i < num_states; i++) {
            if(counts[i] == 0){
                continue; //not possible
            }
            double probability = (double)counts[i]/(double)total_count; //get the probability of the state and cast to double
            probability_so_far+=probability; //add to count so far
            if(random_number < probability_so_far){ //check if chosen
                return i; //return the state
            }
        }
        return -1; //prediction could not be made(not enough data)
    }


    //make a prediction based on what is most probable, returns -1 if not right amount of data or all states are not probable
    public int getMostProbableState(int[] states){
        if(states.length != orders-1){return -1;}//not right length
        //get counts
        int[] counts = new int[num_states];   //index corresponds to states
        int total_count = getTotalCount(states, counts);//get counts and total count
        //get the highest probability
        double highest_probability = 0;
        int most_probable_state = -1; //return -1 if all states are 0
        for (int i = 0; i < num_states; i++) {
            double probability = (double)counts[i]/(double)total_count; //get the probability of the state
          if(probability > highest_probability){ //check if greater
              most_probable_state = i;
              highest_probability = probability;
          }
        }
        return most_probable_state;
    }

    //get the total number of occurrences of a combination
    //updates count array with corresponding count values(make sure count array is size of num_states)
    private int getTotalCount(int[] combination, int[] counts) {
        int total_count = 0;
        //for all possible next states
        for (int i = 0; i < num_states; i++) {
            int[] coordinate = new int[orders]; //coordinate in matrix
            for (int j = 0; j < orders - 1; j++) {
                coordinate[j] = combination[j];
            } //copy over known data
            coordinate[orders - 1] = i; //last element is new possible state

            int count = state_matrix[getIndex(coordinate)]; //get occurrence count from matrix
            counts[i] = count;
            total_count += count;
        }
        return total_count;
    }


    //get 1d index for Nd coordinate
    private int getIndex(int[] coordinates){
        //assumes coordinates.length == orders
        int N = orders; //number of dimensions
        int total = 0; //sum

        //for each dimension
        for (int n = 0; n < N; n++) {
            int power = N-n-1; //get power
            total+=coordinates[n]*Math.pow(num_states,power); //coordinate*width^power
        }
        //some useful equations we made
        //(width^n-1) * c[n]...
        //w^2*x + h*y + z(3d mapping)
        return total;
    }
}
