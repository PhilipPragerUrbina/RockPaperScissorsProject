package RockPaperScissors.Players;

import RockPaperScissors.Player;
import RockPaperScissors.RPS;

import java.util.ArrayList;

public class MetaPlayer implements Player {
    private Player[] players;
    private int n;
    private ArrayList<Integer> past_opponent_moves;
    private Player current_player;

    public MetaPlayer(int memory,Player... players){
        n = memory;
        this.players = players;
        current_player = players[0];
    }

    @Override
    public int getMove() {
        return current_player.getMove();
    }

    Player getBestPlayer(){
        RPS rps = new RPS();
        //we should create a copy but nah
        Player best_player = players[0];
        int max_wins = 0;
        for (Player p : players) {
            for (Integer opponent_move : past_opponent_moves){
                int move = p.getMove();
                int win = rps.playRound(move,opponent_move);
                p.saveLastRoundData(move,opponent_move,win);
              //  if(win == )
            }
        }
        return best_player;
    }

    @Override
    public void saveLastRoundData(int yourMove, int opponentMove, int outcome) {
        past_opponent_moves.add(opponentMove);
     //   for (Player p : players) {
      //      p.saveLastRoundData(yourMove,opponentMove,outcome);
      //  }
        current_player = getBestPlayer();
        if(past_opponent_moves.size() >= n){
            past_opponent_moves.remove(0);
        }
    }
}
