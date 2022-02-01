package src.game;

import java.util.ArrayList;

public class Game {
    private boolean isItPlayer1Turn;
    public ArrayList<Move> moveList;

    public Game() {
        this.moveList = new ArrayList<Move>();
        this.isItPlayer1Turn = false;
    }
    
    public boolean getTurn() {
        return this.isItPlayer1Turn;
    }

    public void registerMove(Move move) {
        this.moveList.add(move);
        this.isItPlayer1Turn = !this.isItPlayer1Turn;
    }
}
