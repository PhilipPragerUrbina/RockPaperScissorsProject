package RockPaperScissors;

import java.util.Scanner;

public class MarkovTest {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter text");
        String in = scanner.nextLine();
        System.out.println("Predicted: " +  finishWord(in, 2));
      //  basicTest();
    }

    //predict letters to finish a word
    //make sure to include whole text, so it can learn
    private static String finishWord(String so_far,int order){
        MarkovModel model = new MarkovModel(255,order); //create markov model with ascii states and specified order
        //training
        for (int i = 0; i < so_far.length(); i++) {
            model.addDataPoint(so_far.charAt(i)); //add data so far
        }

        //trim if too long for prediction
        if(so_far.length() > order){
            so_far = so_far.substring(so_far.length()-order); //get last characters
        }
        //add whitespace if too small for prediction
        if(so_far.length() < order){
            //get correct amount of whitespace
            String whitespace = "";
            for (int i = 0; i < order; i++) {
                whitespace += " ";
            }
            so_far = whitespace+so_far;//add whitespace at front
        }

        //turn into character sequence
        int[] characters = new int[so_far.length()]; //should be char but nah
        for (int i = 0; i < so_far.length(); i++) {
            char character = so_far.charAt(i); //get character
            if(character >= 255 || character < 0){
                character = 15; //not in ascii range
            }
            characters[i] = character; //cast to int and store
        }

        //predict until whitespace or until too many have been predicted
        final int MAX_LENGTH = 2;
        String predictions = "";
        for (int i = 0; i < MAX_LENGTH; i++) {
         //   System.out.println((char)characters[0] + " " + (char)characters[1] );

            int predictioni = model.makePrediction(characters);
            char prediction = (char)predictioni;
        //    System.out.println(prediction);
            if(prediction == ' ' || prediction == '\uFFFF'){ //break if whitespace or error
                break;
            }

            predictions+=prediction;

            //add prediction to characters
            for (int j = 1; j < characters.length; j++) {
                characters[j-1] = characters[j]; //shift to left
            }
            characters[characters.length-1] = prediction;


        }
        return predictions;

    }




    private static void basicTest(){
        MarkovModel model = new MarkovModel(3,1);
        model.addDataPoint(0);
        model.addDataPoint(1);
        model.addDataPoint(2);
        model.addDataPoint(0);
        model.addDataPoint(1);
        model.addDataPoint(2);
        model.addDataPoint(0);
        model.addDataPoint(1);
        model.addDataPoint(2);

        int prediction = model.makePrediction(new int[]{0});
        System.out.println( prediction);
        prediction = model.makePrediction(new int[]{prediction});
        System.out.println( prediction);
        prediction = model.makePrediction(new int[]{prediction});
        System.out.println( prediction);
        prediction = model.makePrediction(new int[]{prediction});
        System.out.println( prediction);
        prediction = model.makePrediction(new int[]{prediction});
        System.out.println( prediction);
        prediction = model.makePrediction(new int[]{prediction});
        System.out.println( prediction);
    }
}
