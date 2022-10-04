package RockPaperScissors.Players;

import RockPaperScissors.Player;
import RockPaperScissors.RPS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//send moves from Player to a ReceivingNetworkPlayer over the network
//The Server side

/* Data interchange protocol:
    - Sever sends action(int)           S
    - Client sends result 1(int)        C
    - Server confirmation(void)         S
    - Client sends result 2(int)        C
    - Server confirmation(void)         S
    - Client sends result 3(int)        C
    Repeat.                             S
 */


public class SendingNetworkedPlayer implements Player {

    private Socket socket; //the socket to connect to
    private ServerSocket server; //the server to await connection
    private DataOutputStream data_to_send; //outgoing data
    private DataInputStream incoming_data; //input data
    private Player controlling_player;

    //Create a layer that will send moves from a player over the network
    public SendingNetworkedPlayer( Player player,int port){
        try {
            //await connection
            server = new ServerSocket(port);
            System.out.println("Server awaiting connection");
            socket = server.accept();
            System.out.println("Sending data to:" + socket.getInetAddress().getHostName());
            //create streams
            data_to_send = new DataOutputStream(socket.getOutputStream());
            incoming_data = new DataInputStream(socket.getInputStream()); //todo add buffered input stream
            System.out.println("Connected to: " + socket.getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        controlling_player = player;
    }

    //get the next move of the player, and send it over the network
    @Override
    public int getMove() {
        int move = controlling_player.getMove(); //get local player move
        try {
            //send move
            data_to_send.writeInt(move);
            System.out.println("Move sent: "  + RPS.intToString(move));
            return move;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return move; //return local player move
    }

    //receive data and pipe to underlying player
    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
        try {
            //get data
            yourMove = incoming_data.readInt(); //read your move
            data_to_send.write(0); //write confirmation
            opponentMove = incoming_data.readInt(); //read opponent move
            data_to_send.write(0); //write confirmation
            outcome = incoming_data.readInt(); //read outcome
        } catch (IOException e) {
            e.printStackTrace();
        }

        controlling_player.saveLastRoundData(yourMove,opponentMove,outcome); //save data to underlying player(can fall back on local data)

    }

    //clean up(not necessary if not connecting to anything else)
    public void close(){
        try {
            incoming_data.close();
            data_to_send.close();;
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }
}
