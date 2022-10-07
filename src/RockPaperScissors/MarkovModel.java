package RockPaperScissors;

public class MarkovModel {
    private int num_states;
    private int orders;
    int[] state_matrix; //matrix
    public MarkovModel(int num_states, int orders){
        this.num_states = num_states; //number of states
        this.orders = orders; //number of orders, hwo far back it looks
        state_matrix = new int[(int)Math.pow(num_states,orders)]; //create 1d matrix to store Nd matrix
    }
    private int prev_state = 0;

    public void addDataPoint(int state){

    }

    //get 1d index for Nd coordinate
    private int getIndex(int[] coordinates){
        //assumes coordinates.length == orders
        int N = orders; //number of dimensions
        int total = 0; //sum

        //for each dimension
        for (int n = 0; n < N; n++) {
            int power = N-n-1; //get power
            total+=coordinates[n]*Math.pow(orders,power); //coordinate*width^power
        }
        //some useful equations we made
        //(orders^n-1) * c[n]...
        //w^2*x + h*y + z(3d mapping)
        return total;
    }
}
