package RockPaperScissors.Markov;

//class for doing the baby predictions
public class MarkovBabyTests {

    //2 state baby states
    enum SimpleBabyState {
        AWAKE, //0
        SLEEPING, //1
    }

    //5 state baby states
    enum ComplexBabyState {
        SLEEPING, //0
        EATING, //1
        CRYING, //2
        PLAYING, //3
        FUSSING //4
    }

    //test the babies
    public static void main(String[] args) {
      //  System.out.println("Simple baby: ");
       // testTwoStateBaby(new int[]{0,0,0,0,1,1,1,0,0,1,1,1,1,1,0,0,0,1,0,0,0}, 4);
      //  System.out.println("Complex baby: ");
     //   testFiveStateBaby(new int[]{0,0,0,0,0,0,0,0,0,0,1,1,3,3,3,4,3,4,2,2,4,2,4,2,1,0,0,2,0}, 100);

        //using markov model assignment
        int[] data = {0,1,2,3,4,1,2,2,3,1,3,0,1,2,3,0,4,3,4,0,0,1,2,3,2,1,0,0,2,2,2,3,1}; //random data
        //print a bunch of tables
        printAssignmentTable(data,ComplexBabyState.SLEEPING, ComplexBabyState.EATING,1,20,1);

    }

    //print a table comparing probability of end state to number of transitions
    public static void printAssignmentTable(int[] data,ComplexBabyState start_state, ComplexBabyState end_state, int min_transitions, int max_transitions, int step){
        final int num_trials = 10000; //number of trials for monte carlo
        int end_state_integer  = end_state.ordinal(); //get the corresponding int
        int start_state_integer  = start_state.ordinal();
        System.out.println("Start: " + start_state); //display info
        System.out.println("End: " + end_state);
        System.out.println("Transitions Probability");//output title
        //for each x value
        for (int num_transitions = min_transitions; num_transitions < max_transitions; num_transitions+=step) {
            //simulate
            double probability = getProbOfEndState(data, start_state_integer,end_state_integer,num_transitions,num_trials);
            System.out.println(num_transitions + " " + probability); //output table values
        }
    }

    //get probability of an end state for a five state baby with monte carlo
    public static double getProbOfEndState(int[] data,int start_state, int end_state, int num_transitions, int max_trials){
        int sum = 0; //number of times end state is the end state specified
        MarkovModel m = new MarkovModel(5,1); //create the model for 5 state baby
        m.addDataPoints(data); //add training data

        for (int i = 0; i < max_trials; i++) { //for each trial
            int last_state = start_state; //start with the start state
            for (int j = 0; j < num_transitions; j++) {
                int prediction  =m.makePrediction(new int[]{last_state}); //run the simulations(transitions)
                last_state = prediction;
            }
            if(last_state == end_state){sum++;} //increment sum if it ends on the end state
        }
        return (double)sum/(double)max_trials; //return probability
    }




    //print out n predictions for simple baby based on past data
    public static void testTwoStateBaby(int[] data, int num_predictions){
        MarkovModel model = new MarkovModel(2,1); //create simple model
        model.addDataPoints(data); //train
        int last_point = data[data.length-1]; //start with the latest data(would be an array for higher orders)
        for (int i = 0; i < num_predictions; i++) {
            int prediction_int = model.makePrediction(new int[]{last_point});//make predictions
            SimpleBabyState prediction = SimpleBabyState.values()[prediction_int];  //convert to enum
            System.out.println("Prediction: " + prediction);  //output
            last_point = prediction_int; //base next prediction after current one
        }
    }

    //print out n predictions for a complex baby based on past data
    public static void testFiveStateBaby(int[] data, int num_predictions){
        MarkovModel model = new MarkovModel(5,1); //create simple model
        model.addDataPoints(data); //train
        int last_point = data[data.length-1]; //start with the latest data
        for (int i = 0; i < num_predictions; i++) {
            int prediction_int = model.makePrediction(new int[]{last_point});//make predictions
            ComplexBabyState prediction = ComplexBabyState.values()[model.makePrediction(new int[]{prediction_int})];
            System.out.println("Prediction: " + prediction);  //output
            last_point = prediction_int;
        }
    }
}
