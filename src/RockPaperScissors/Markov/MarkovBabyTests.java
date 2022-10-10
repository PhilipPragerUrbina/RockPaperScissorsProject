package RockPaperScissors.Markov;
//class for doing the baby predictions
public class MarkovBabyTests {

    //2 state baby states
    enum simpleBabyState{
        AWAKE, //0
        SLEEPING, //1
    }

    //5 state baby states
    enum complexBabyStates{
        SLEEPING, //0
        EATING, //1
        CRYING, //2
        PLAYING, //3
        FUSSING //4
    }

    //test the babies
    public static void main(String[] args) {
        System.out.println("Simple baby: ");
        testTwoStateBaby(new int[]{0,0,0,0,1,1,1,0,0,1,1,1,1,1,0,0,0,1,0,0,0}, 4);
        System.out.println("Complex baby: ");
        testFiveStateBaby(new int[]{0,0,0,0,0,0,0,0,0,0,1,1,3,3,3,4,3,4,2,2,4,2,4,2,1,0,0,2,0}, 100);
    }

    //print out n predictions for simple baby based on past data
    public static void testTwoStateBaby(int[] data, int num_predictions){
        MarkovModel model = new MarkovModel(2,1); //create simple model
        model.addDataPoints(data); //train
        int last_point = data[data.length-1]; //start with the latest data(would be an array for higher orders)
        for (int i = 0; i < num_predictions; i++) {
            int prediction_int = model.makePrediction(new int[]{last_point});//make predictions
            simpleBabyState prediction = simpleBabyState.values()[prediction_int];  //convert to enum
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
            complexBabyStates prediction = complexBabyStates.values()[model.makePrediction(new int[]{prediction_int})];
            System.out.println("Prediction: " + prediction);  //output
            last_point = prediction_int;
        }
    }
}
