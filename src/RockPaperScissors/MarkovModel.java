package RockPaperScissors;

import java.util.ArrayList;

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

    //add a state(starts at 0)
    public void addDataPoint(int state){
        if(prev_states.size() == orders-1){   //enough data
            int[] coordinate = new int[orders];
            for (int i = 0; i < orders-1; i++) {
                coordinate[i] = prev_states.get(i);
            }

            coordinate[coordinate.length-1] = state;
            state_matrix[getIndex(coordinate)]++;

        }

        //add to last states
        prev_states.add(state);
        //keep prev states correct size
        if(prev_states.size() == orders){
            prev_states.remove(0);
        }
    }
    //make a prediction based on past data, returns -1 if not right amount of data
    public int makePrediction(int[] states){



        if(states.length != orders-1){return -1;}//not right length todo add filler option

        //get counts
        int[] counts = new int[num_states];   //index corresponds to states
        int total_count = 0;
        //for all possible next states
        for (int i = 0; i < num_states; i++) {
            int[] coordinate = new int[orders]; //coordinate in matrix
            for (int j = 0; j < orders-1; j++) {coordinate[j] = states[j];} //copy over known data
            coordinate[orders-1]=i; //last element is new possible state
            int count =  state_matrix[getIndex(coordinate)]; //get occurrence count from matrix
            counts[i] = count;
            total_count+=count;
        }
        //get random number
        double random_number = Math.random();
        //check probabilities
        double count_so_far = 0; //until what probability have we checked so far
        for (int i = 0; i < num_states; i++) {
          //  System.out.println((char)i + "  " + counts[i]);
            if(counts[i] == 0){
                continue;
            }
            double count = counts[i]/(double)total_count; //get the probability of the state and cast to double
            count_so_far+=count; //add to count so far
            if(random_number < count_so_far){ //check if chosen
                return i; //return the state
            }
        }
        return -1; //should not happen
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
