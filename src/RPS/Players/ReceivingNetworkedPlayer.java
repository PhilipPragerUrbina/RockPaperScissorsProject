package RPS.Players;

import RPS.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.SocketHandler;

//get  moves over the network
public class ReceivingNetworkedPlayer implements Player {
    private Socket socket;
    private DataOutputStream input;
    public ReceivingNetworkedPlayer(String address, int port){
        try {
            socket = new Socket(address,port);
            input = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connected to other player");
    }
    @Override
    public int getMove() {
        return ;
    }

    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
        //nothing
    }
}
