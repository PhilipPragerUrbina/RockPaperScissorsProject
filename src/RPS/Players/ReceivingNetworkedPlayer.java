package RPS.Players;

import RPS.Player;
import RPS.RPS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//get moves from a SendingNetworkPlayer over the network
//The client side
//acts like a puppet for the actual player on another computer

/* Data interchange protocol:
    - Sever sends action(int)           S
    - Client sends result 1(int)        C
    - Server confirmation(void)         S
    - Client sends result 2(int)        C
    - Server confirmation(void)         S
    - Client sends result 3(int)        C
    Repeat.                             S
 */


public class ReceivingNetworkedPlayer implements Player {

    private Socket socket; //the socket to connect to
    private DataOutputStream data_to_send; //outgoing data
    private DataInputStream incoming_data; //input data

    //Create a player that will get moves from an address and port
    public ReceivingNetworkedPlayer(String address, int port){
        try {
            Thread.sleep(50); //wait for server to start up(in case client starts up first)     todo: make scanning loop
            //create socket and steam
            socket = new Socket(address,port);
            data_to_send = new DataOutputStream(socket.getOutputStream());
            incoming_data = new DataInputStream(socket.getInputStream());
            System.out.println("Getting data from: " + socket.getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //get the next move over the network and respond with confirmation
    @Override
    public int getMove() {
        try {
            int move = incoming_data.readInt(); //get move
            //no need for confirmation, will send back round data next
            System.out.println("Move received: " + RPS.intToString(move));
            return move;
        } catch (IOException e) {
            e.printStackTrace();
            return RPS.ROCK; //return rock if not receiving anything
        }
    }

    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
        //send data back to player, so it can make decisions if necessary
        try {//always have a response for every write, and write for every send.
            //This is so the connection is reliable
        data_to_send.writeInt(yourMove);  //write your-move
        incoming_data.read(); //await confirmation
        data_to_send.writeInt(opponentMove); //write opponent move
        incoming_data.read();  //await confirmation
        data_to_send.writeInt(outcome); //write outcome
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //clean up(not necessary if not connecting to anything else)
    public void close(){
        try {
            data_to_send.close();
            incoming_data.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }
}
